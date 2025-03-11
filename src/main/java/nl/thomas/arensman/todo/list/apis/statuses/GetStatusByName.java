package nl.thomas.arensman.todo.list.apis.statuses;

import com.fasterxml.jackson.core.JsonProcessingException;
import nl.thomas.arensman.todo.list.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import static nl.thomas.arensman.todo.list.utils.database.StatusDatabaseUtils.getStatusFromResultSet;
import static nl.thomas.arensman.todo.list.utils.database.StatusDatabaseUtils.selectStatusWhereName;
import static nl.thomas.arensman.todo.list.utils.HttpUtils.*;

@RestController
public class GetStatusByName {

    private static final String GET_STATUS_WHERE_NAME = "/status/name/{statusName}";

    @Autowired
    private DataSource dataSource;

    @GetMapping(GET_STATUS_WHERE_NAME)
    public ResponseEntity<String> getGetStatusWhereName(@PathVariable String statusName) throws SQLException, JsonProcessingException {
        try {
            ResultSet result = selectStatusWhereName(dataSource, statusName);
            validateResultSet(result, statusName);

            Status status = getStatusFromResultSet(result);
            return createResponseEntity(status, HttpStatus.OK, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private static void validateResultSet(ResultSet resultSet, String statusName) throws SQLException {
        if (!resultSet.next())
            throw new SQLException("No result where found with status_name: " + statusName);
    }

}
