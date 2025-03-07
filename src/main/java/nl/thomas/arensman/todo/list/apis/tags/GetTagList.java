package nl.thomas.arensman.todo.list.apis.tags;

import nl.thomas.arensman.todo.list.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.thomas.arensman.todo.list.models.Tag.getTagBuilder;
import static nl.thomas.arensman.todo.list.utils.HttpUtils.*;
import static nl.thomas.arensman.todo.list.utils.database.TagsDatabaseUtils.selectTagList;
import static nl.thomas.arensman.todo.list.utils.database.TagsDatabaseUtils.tagResultSetToTag;

@RestController
public class GetTagList {

    @Autowired
    private DataSource dataSource;

    private static final String GET_TAGS_LIST_ENDPOINT = "/tag/list";

    @GetMapping(GET_TAGS_LIST_ENDPOINT)
    public ResponseEntity<String> getTagsListEndpoint () throws SQLException {
        try {
            final ResultSet resultSet = selectTagList(dataSource);

            List<Tag> tags = new ArrayList<>();
            while (resultSet.next())
                tags.add(tagResultSetToTag(resultSet));

            return createResponseEntity(tags, HttpStatus.OK, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }
}
