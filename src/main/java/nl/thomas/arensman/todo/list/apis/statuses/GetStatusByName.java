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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static nl.thomas.arensman.todo.list.utils.HttpUtils.*;

@RestController
public class GetStatusByName {

    private static final String SELECT_STATUS_WHERE_NAME_STATEMENT = "SELECT * FROM `statuses` WHERE `status_name` = ?";
    private static final String GET_STATUS_WHERE_NAME = "/status/name/{statusName}";

    @Autowired
    private DataSource dataSource;

    @GetMapping(GET_STATUS_WHERE_NAME)
    public ResponseEntity<String> getGetStatusWhereName(@PathVariable String statusName) throws SQLException, JsonProcessingException {
        try {
            ResultSet result = selectStatusWhereId(dataSource, statusName);
            validateResultSet(result, statusName);

            Status status = getStatusFromResultSet(result);
            return createResponseEntity(status, HttpStatus.ACCEPTED, getDefaultHeaders());
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

    public static ResultSet selectStatusWhereId (DataSource dataSource, String statusName) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STATUS_WHERE_NAME_STATEMENT);
            preparedStatement.setString(1, statusName);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("An exception occurred while trying to select status by status_name: " + statusName);
        }
    }

    private static void validateResultSet(ResultSet resultSet, String statusName) throws SQLException {
        if (!resultSet.next())
            throw new SQLException("No result where found with status_name: " + statusName);
    }

}
