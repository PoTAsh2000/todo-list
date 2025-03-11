package nl.thomas.arensman.todo.list.apis.tags;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.thomas.arensman.todo.list.json.schemas.TagBodyRequest;
import nl.thomas.arensman.todo.list.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

import static nl.thomas.arensman.todo.list.utils.HttpUtils.*;
import static nl.thomas.arensman.todo.list.utils.Utils.*;
import static nl.thomas.arensman.todo.list.utils.database.TagsDatabaseUtils.*;

@RestController
public class PatchTagById {

    @Autowired
    private DataSource dataSource;

    private static final String PATCH_TAG_BY_ID_ENDPOINT = "/tag/{tagId}";

    @PatchMapping(PATCH_TAG_BY_ID_ENDPOINT)
    public ResponseEntity<String> patchTagById (@PathVariable String tagId, @RequestBody String requestBody) {
        try {
            TagBodyRequest tagBodyRequest = new ObjectMapper().readValue(requestBody, TagBodyRequest.class);

            validateTagId(tagId);
            validateTagExists(tagId);
            validateTagName(tagBodyRequest.getTagName());
            validateHexColor(tagBodyRequest.getTagHexColor());

            ResultSet existingTag = selectTagWhereId(dataSource, Integer.parseInt(tagId));
            Tag tag = tagResultSetToTag(existingTag);

            updateTagWhereId(dataSource, tagBodyRequest, Integer.parseInt(tagId));

            return createResponseEntity(String.format("Tag with tag_id: '%s' and tag_name: '%s' was updated successfully", tagId, tag.getTagName()), HttpStatus.OK, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private void validateTagId(String tagId) {
        if (!strIsIntParsable(tagId))
            throw new RuntimeException("tag_id must be of type integer");
    }

    private void validateTagExists (String tagId) throws SQLException {
        ResultSet resultSet = selectTagWhereId(dataSource, Integer.parseInt(tagId));
        if (!resultSet.next())
            throw new RuntimeException("there is no existing tag with tag_id: " + tagId);
    }

    private void validateTagName (String tagName) throws SQLException {
        final String tagNameConstraints = "Tag Name field constraints: String name max length = '16' and can only contain characters of pattern [a-zA-Z]";

        if (tagName.length() > 16)
            throw new RuntimeException("Given tag name length is larger than 16. " + tagNameConstraints);

        final String invalidCharactersRemaining = tagName.replaceAll("[a-zA-Z]", "");
        if (!strIsNullOrBlank(invalidCharactersRemaining)) {
            final char[] invalidCharacterArray = invalidCharactersRemaining.toCharArray();
            final String invalidCharacters = String.join(",", charArrayToStringArray(invalidCharacterArray));
            throw new RuntimeException(String.format("Given tag name contains illegal characters: [%s]. %s", invalidCharacters, tagNameConstraints));
        }

        final ResultSet resultSet = selectTagWhereName(dataSource, tagName);
        if (resultSet.next())
            throw new RuntimeException(String.format("Tag '%s' already exists in the database", tagName));
    }
}
