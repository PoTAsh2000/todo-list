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
import static nl.thomas.arensman.todo.list.utils.database.StatusDatabaseUtils.selectStatusWhereId;
import static nl.thomas.arensman.todo.list.utils.Utils.strIsIntParsable;
import static nl.thomas.arensman.todo.list.utils.HttpUtils.*;

@RestController
public class GetStatusById {

    private static final String GET_STATUS_WHERE_ID = "/status/{statusId}";

    @Autowired
    private DataSource dataSource;

    @GetMapping(GET_STATUS_WHERE_ID)
    public ResponseEntity<String> getGetStatusWhereId(@PathVariable String statusId) throws SQLException, JsonProcessingException {
        try {
            validateStatusId(statusId);

            ResultSet result = selectStatusWhereId(dataSource, Integer.parseInt(statusId));
            validateResultSet(result, statusId);

            Status status = getStatusFromResultSet(result);
            return createResponseEntity(status, HttpStatus.OK, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }
    
    private static void validateStatusId (String statusId) {
        if (!strIsIntParsable(statusId))
            throw new RuntimeException("Provided status_id is invalid. Must be of type Integer");
    }
    
    private static void validateResultSet(ResultSet resultSet, String statusId) throws SQLException {
        if (!resultSet.next())
            throw new SQLException("No result where found with status_id: " + statusId);
    }
}
