package nl.thomas.arensman.todo.list.apis.tags;

import nl.thomas.arensman.todo.list.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

import static nl.thomas.arensman.todo.list.utils.HttpUtils.*;
import static nl.thomas.arensman.todo.list.utils.Utils.strIsIntParsable;
import static nl.thomas.arensman.todo.list.utils.database.TagsDatabaseUtils.*;

@RestController
public class DeleteTagById {

    @Autowired
    private DataSource dataSource;

    public static final String DELETE_TAG_WHERE_ID_ENDPOINT = "/tag/{tagId}";

    @DeleteMapping(DELETE_TAG_WHERE_ID_ENDPOINT)
    public ResponseEntity<String> deleteTagById(@PathVariable String tagId) {
        try {
            validateTagId(tagId);
            validateTagExists(tagId);

            ResultSet existingTag = selectTagWhereId(dataSource, Integer.parseInt(tagId));
            Tag tag = tagResultSetToTag(existingTag);

            deleteTagWhereId(dataSource, Integer.parseInt(tagId));

            return createResponseEntity(
                    String.format("Successfully delete with tag_id: '%s' and tag_name: '%s'", tagId, tag.getTagName()),
                    HttpStatus.OK,
                    getDefaultHeaders()
            );
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private void validateTagId(String tagId) {
        if (!strIsIntParsable(tagId))
            throw new RuntimeException("tag_id is invalid. Must be of type: Integer");
    }

    private void validateTagExists(String tagId) throws SQLException {
        ResultSet resultSet = selectTagWhereId(dataSource, Integer.parseInt(tagId));
        if (!resultSet.next())
            throw new RuntimeException("There are no tags with tag_id: " + tagId);
    }
}
