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
import static nl.thomas.arensman.todo.list.utils.Utils.strIsNullOrBlank;
import static nl.thomas.arensman.todo.list.utils.database.TaskDatabaseUtils.resultSetToTask;
import static nl.thomas.arensman.todo.list.utils.database.TaskDatabaseUtils.selectTaskByName;

@RestController
public class SelectTaskByName {

    @Autowired
    private DataSource dataSource;

    private static final String SELECT_BY_NAME_ENDPOINT = "/task/name/{taskName}";

    @GetMapping(SELECT_BY_NAME_ENDPOINT)
    public ResponseEntity<String> getTaskByName(@PathVariable String taskName) {
        try {
            validateName(taskName);

            ResultSet taskResultSet = selectTaskByName(dataSource, taskName);
            validateResultSet(taskResultSet);

            Task task = resultSetToTask(taskResultSet);
            return createResponseEntity(task, HttpStatus.OK, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private void validateResultSet(ResultSet taskResultSet) throws SQLException {
        if (!taskResultSet.next())
            throw new RuntimeException("There is no task with this name");
    }

    private void validateName(String taskName) {
        if (strIsNullOrBlank(taskName))
            throw new RuntimeException("task_name must have a value");

        int maxNameLength = 64;
        if (taskName.length() > maxNameLength)
            throw new RuntimeException(String.format("task_name can not be longer than %s characters", maxNameLength));
    }

}
