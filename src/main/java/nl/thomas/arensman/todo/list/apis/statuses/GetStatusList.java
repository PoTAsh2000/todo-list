package nl.thomas.arensman.todo.list.apis.statuses;

import nl.thomas.arensman.todo.list.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static nl.thomas.arensman.todo.list.utils.HttpUtils.*;

@RestController
public class GetStatusList {

    private static final String SELECT_ALL_STATUSES_STATEMENT = "SELECT * FROM `statuses`";
    private static final String GET_STATUSES_LIST_ENDPOINT = "/status/list";

    @Autowired
    private DataSource dataSource;

    @GetMapping(GET_STATUSES_LIST_ENDPOINT)
    public ResponseEntity<String> getStatusesList() {
        try {
            ResultSet resultSet = selectAllStatuses(dataSource);

            List<Status> statusList = new ArrayList<>();
            appendResultsToStatusList(resultSet, statusList);

            return createResponseEntity(statusList, HttpStatus.ACCEPTED, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private static void appendResultsToStatusList(ResultSet resultSet, List<Status> statusList) throws SQLException {
        while (resultSet.next()) {
            Status status = Status.getStatusBuilder()
                    .setStatusId(resultSet.getInt("status_id"))
                    .setStatusName(resultSet.getString("status_name"))
                    .setStatusHexColor(resultSet.getString("status_hex_color"))
                    .setStatusCreationDate(resultSet.getString("status_creation_date"))
                    .build();
            statusList.add(status);
        }
    }

    private static ResultSet selectAllStatuses(DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STATUSES_STATEMENT);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new SQLException("An exception occurred while trying to select data from statuses table");
        }
    }
}
