package nl.thomas.arensman.todo.list.apis.tasks;

import nl.thomas.arensman.todo.list.models.Task;
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
import static nl.thomas.arensman.todo.list.utils.Utils.strIsIntParsable;
import static nl.thomas.arensman.todo.list.utils.database.TaskDatabaseUtils.resultSetToTask;
import static nl.thomas.arensman.todo.list.utils.database.TaskDatabaseUtils.selectTaskById;

@RestController
public class SelectTaskById {

    @Autowired
    private DataSource dataSource;

    public static final String GET_TASK_BY_ID_ENDPOINT = "/task/{taskId}";

    @GetMapping(GET_TASK_BY_ID_ENDPOINT)
    public ResponseEntity<String> getTaskById (@PathVariable String taskId) {
        try {
            validateTaskId(taskId);

            ResultSet taskResultSet = selectTaskById(dataSource, Integer.parseInt(taskId));
            validateResultSet(taskResultSet, taskId);

            Task task = resultSetToTask(taskResultSet);
            return createResponseEntity(task, HttpStatus.OK, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private void validateTaskId(String taskId) {
        if (!strIsIntParsable(taskId))
            throw new RuntimeException("task_id must be be type integer");
    }

    private static void validateResultSet(ResultSet resultSet, String taskId) throws SQLException {
        if (!resultSet.next())
            throw new SQLException("No result where found with task_id: " + taskId);
    }
}
