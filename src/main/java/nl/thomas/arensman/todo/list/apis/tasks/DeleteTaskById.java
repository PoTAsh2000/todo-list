package nl.thomas.arensman.todo.list.apis.tasks;

import nl.thomas.arensman.todo.list.models.Task;
import nl.thomas.arensman.todo.list.utils.database.TaskDatabaseUtils;
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
import static nl.thomas.arensman.todo.list.utils.database.TaskDatabaseUtils.resultSetToTask;
import static nl.thomas.arensman.todo.list.utils.database.TaskDatabaseUtils.selectTaskById;

@RestController
public class DeleteTaskById {

    @Autowired
    private DataSource dataSource;

    private static final String DELETE_TASK_BY_ID_ENDPOINT = "/task/{taskId}";

    @DeleteMapping(DELETE_TASK_BY_ID_ENDPOINT)
    public ResponseEntity<String> deleteTaskById(@PathVariable String taskId) {
        try {
            validateTaskId(taskId);

            ResultSet taskResultSet = selectTaskById(dataSource, Integer.parseInt(taskId));
            validateResultSet(taskResultSet, taskId);

            Task task = resultSetToTask(taskResultSet);

            TaskDatabaseUtils.deleteTaskById(dataSource, Integer.parseInt(taskId));

            return createResponseEntity("Successfully delete task: " + task.getTaskName(), HttpStatus.OK, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private void validateTaskId(String taskId) {
        if (!strIsIntParsable(taskId))
            throw new RuntimeException("task_id must be be type integer");
    }

    private static void validateResultSet(ResultSet resultSet, String statusId) throws SQLException {
        if (!resultSet.next())
            throw new SQLException("No result where found with status_id: " + statusId);
    }
}
