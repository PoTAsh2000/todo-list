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

import static nl.thomas.arensman.todo.list.utils.HttpUtils.*;
import static nl.thomas.arensman.todo.list.utils.database.TagsDatabaseUtils.selectTagWhereName;
import static nl.thomas.arensman.todo.list.utils.database.TagsDatabaseUtils.tagResultSetToTag;

@RestController
public class GetTagByName {

    private static final String GET_TAG_BY_NAME = "tag/name/{name}";

    @Autowired
    private DataSource dataSource;

    @GetMapping(GET_TAG_BY_NAME)
    public ResponseEntity<String> getTagByName(@PathVariable String name) {
        try {
            name = name.toUpperCase();

            ResultSet resultSet = selectTagWhereName(dataSource, name);
            validateResultSet(resultSet, name);

            Tag tag = tagResultSetToTag(resultSet);
            return createResponseEntity(tag, HttpStatus.OK, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private void validateResultSet(ResultSet resultSet, String name) throws SQLException {
        if (!resultSet.next())
            throw new RuntimeException("No results found where tag_name: " + name);
    }

}
