package nl.thomas.arensman.todo.list.apis.tags;

import nl.thomas.arensman.todo.list.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import static nl.thomas.arensman.todo.list.models.Tag.getTagBuilder;
import static nl.thomas.arensman.todo.list.utils.HttpUtils.*;
import static nl.thomas.arensman.todo.list.utils.Utils.strIsIntParsable;
import static nl.thomas.arensman.todo.list.utils.database.TagsDatabaseUtils.selectTagWhereId;
import static nl.thomas.arensman.todo.list.utils.database.TagsDatabaseUtils.tagResultSetToTag;

@RestController
public class GetTagById {

    @Autowired
    private DataSource dataSource;

    private static final String GET_TAGS_BY_ID = "/tag/{tagId}";

    @GetMapping(GET_TAGS_BY_ID)
    public ResponseEntity<String> getStatusById(@PathVariable String tagId) {
        try {
            validateTagId(tagId);

            ResultSet resultSet = selectTagWhereId(dataSource, Integer.parseInt(tagId));
            validateTagResultSet(resultSet, tagId);

            Tag tag = tagResultSetToTag(resultSet);
            return createResponseEntity(tag, HttpStatus.OK, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private void validateTagResultSet(ResultSet resultSet, String tagId) throws SQLException {
        if (!resultSet.next())
            throw new RuntimeException("No result where found with tag_id: " + tagId);
    }

    private static void validateTagId (String tagId) {
        if (!strIsIntParsable(tagId))
            throw new RuntimeException("Provided tag_id is invalid. Must be of type Integer");
    }
}
