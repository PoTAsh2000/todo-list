package nl.thomas.arensman.todo.list.apis.tags;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.thomas.arensman.todo.list.json.schemas.TagBodyRequest;
import nl.thomas.arensman.todo.list.utils.database.TagsDatabaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

import static nl.thomas.arensman.todo.list.utils.HttpUtils.*;
import static nl.thomas.arensman.todo.list.utils.Utils.*;
import static nl.thomas.arensman.todo.list.utils.database.TagsDatabaseUtils.selectTagWhereName;

@RestController
public class PostNewTag {

    public static final String POST_NEW_TAG = "/tag";

    @Autowired
    private DataSource dataSource;

    @PostMapping(POST_NEW_TAG)
    public ResponseEntity<String> postNewTag (@RequestBody String requestBody) {
        try {
            TagBodyRequest tagBodyRequest = new ObjectMapper().readValue(requestBody, TagBodyRequest.class);

            validateHexColor(tagBodyRequest.getTagHexColor());
            validateTagName(dataSource, tagBodyRequest.getTagName());

            TagsDatabaseUtils.postNewTag(dataSource, tagBodyRequest);
            return createResponseEntity(String.format("Record '%s' was successfully created", tagBodyRequest.getTagName()), HttpStatus.CREATED, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private static void validateTagName (DataSource dataSource, String tagName) throws SQLException {
        final String statusNameConstraints = "Tag Name field constraints: String name max length = '16' and can only contain characters of pattern [a-zA-Z]";

        if (tagName.length() > 16)
            throw new RuntimeException("Given tag name length is larger than 16. " + statusNameConstraints);

        final String invalidCharactersRemaining = tagName.replaceAll("[a-zA-Z]", "");
        if (!strIsNullOrBlank(invalidCharactersRemaining)) {
            final char[] invalidCharacterArray = invalidCharactersRemaining.toCharArray();
            final String invalidCharacters = String.join(",", charArrayToStringArray(invalidCharacterArray));
            throw new RuntimeException(String.format("Given tag name contains illegal characters: [%s]. %s", invalidCharacters, statusNameConstraints));
        }

        final ResultSet resultSet = selectTagWhereName(dataSource, tagName);
        if (resultSet.next())
            throw new RuntimeException(String.format("Tag '%s' already exists in the database", tagName));
    }
}
