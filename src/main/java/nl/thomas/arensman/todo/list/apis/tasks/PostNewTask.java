package nl.thomas.arensman.todo.list.apis.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.thomas.arensman.todo.list.json.schemas.TaskRequestBody;
import nl.thomas.arensman.todo.list.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static nl.thomas.arensman.todo.list.utils.Constants.DATABASE_DATETIME_FORMAT;
import static nl.thomas.arensman.todo.list.utils.HttpUtils.*;
import static nl.thomas.arensman.todo.list.utils.Utils.strIsNullOrBlank;
import static nl.thomas.arensman.todo.list.utils.Utils.strMatchesDateTimePattern;
import static nl.thomas.arensman.todo.list.utils.database.StatusDatabaseUtils.getStatusFromResultSet;
import static nl.thomas.arensman.todo.list.utils.database.StatusDatabaseUtils.selectStatusWhereName;
import static nl.thomas.arensman.todo.list.utils.database.TaskDatabaseUtils.insertNewTask;

@RestController
public class PostNewTask {

    @Autowired
    private DataSource dataSource;

    private static final String POST_NEW_TASK_ENDPOINT = "/task";

    @PostMapping(POST_NEW_TASK_ENDPOINT)
    public ResponseEntity<String> postNewTask (@RequestBody String requestBody) {
        try {
            TaskRequestBody taskRequestBody = new ObjectMapper().readValue(requestBody, TaskRequestBody.class);
            String validTaskName = validateRequestName(taskRequestBody.getTaskName());
            Status validStatus = validateRequestStatus(taskRequestBody.getTaskStatusName());
            Integer taskPriority = taskRequestBody.getTaskPriority();
            String validDeadlineDateTime = validateRequestDeadline(taskRequestBody.getTaskDeadline());

            insertNewTask(dataSource, validTaskName, validStatus.getStatusId(), taskPriority, validDeadlineDateTime);

            return createResponseEntity("Successfully created new task", HttpStatus.OK, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private String validateRequestDeadline(String taskDeadline) {
        if (strIsNullOrBlank(taskDeadline))
            return null;

        if (!strMatchesDateTimePattern(taskDeadline, DATABASE_DATETIME_FORMAT))
            throw new RuntimeException("Given task_deadline_date must be of pattern: " + DATABASE_DATETIME_FORMAT);

        return taskDeadline;
    }

    private Status validateRequestStatus(String taskStatusName) throws SQLException {
        ResultSet resultSet = selectStatusWhereName(dataSource, taskStatusName);
        if (!resultSet.next())
            throw new RuntimeException("There are no existing statuses with status_name: " + taskStatusName);
        return getStatusFromResultSet(resultSet);
    }

    private String validateRequestName(String taskName) {
        int maxNameLength = 64;

        if (strIsNullOrBlank(taskName))
            throw new RuntimeException("task_name must have a value");
        if (taskName.length() > maxNameLength)
            throw new RuntimeException(String.format("task_name can not be longer than %s characters", maxNameLength));

        return taskName;
    }


}
