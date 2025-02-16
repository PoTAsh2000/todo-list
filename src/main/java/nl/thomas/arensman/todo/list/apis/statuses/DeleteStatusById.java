package nl.thomas.arensman.todo.list.apis.statuses;

import nl.thomas.arensman.todo.list.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
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
import static nl.thomas.arensman.todo.list.utils.database.StatusDatabaseUtils.deleteStatusWhereId;
import static nl.thomas.arensman.todo.list.utils.database.StatusDatabaseUtils.selectStatusWhereId;

@RestController
public class DeleteStatusById {

    private static final String DELETE_STATUS_BY_ID_REQUEST = "/status/{statusId}";

    @Autowired
    private DataSource dataSource;

    @DeleteMapping(DELETE_STATUS_BY_ID_REQUEST)
    public ResponseEntity<String> deleteStatusById (@PathVariable String statusId) {
        try {
            validateStatusId(statusId);
            final ResultSet resultSet = selectStatusWhereId(dataSource, Integer.parseInt(statusId));

            if (!resultSetHasResults(resultSet))
                return createResponseEntity(String.format("No records found with status_id '%s'. No deletes where executed.", statusId), HttpStatus.OK, getDefaultHeaders());

            final Status status = getStatusFromResultSet(resultSet);
            deleteStatusWhereId(dataSource, Integer.parseInt(statusId));

            return createResponseEntity(String.format("Successfully deleted status: '%s', with status_id: '%s'", status.getStatusName(), statusId), HttpStatus.OK, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private static Status getStatusFromResultSet(ResultSet result) throws SQLException {
        return Status.getStatusBuilder()
                .setStatusId(result.getInt("status_id"))
                .setStatusName(result.getString("status_name"))
                .setStatusHexColor(result.getString("status_hex_color"))
                .setStatusCreationDate(result.getString("status_creation_date"))
                .build();
    }

    private static boolean resultSetHasResults(ResultSet resultSet) {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            return false;
        }
    }

    private static void validateStatusId (String statusId) {
        if (!strIsIntParsable(statusId))
            throw new RuntimeException("Provided status_id is invalid. Must be of type Integer");
    }
}
