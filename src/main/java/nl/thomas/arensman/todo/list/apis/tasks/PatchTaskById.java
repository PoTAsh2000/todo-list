package nl.thomas.arensman.todo.list.apis.tasks;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.thomas.arensman.todo.list.json.schemas.TaskRequestBody;
import nl.thomas.arensman.todo.list.models.Status;
import nl.thomas.arensman.todo.list.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

import static nl.thomas.arensman.todo.list.utils.Constants.DATABASE_DATETIME_FORMAT;
import static nl.thomas.arensman.todo.list.utils.HttpUtils.*;
import static nl.thomas.arensman.todo.list.utils.Utils.*;
import static nl.thomas.arensman.todo.list.utils.database.StatusDatabaseUtils.getStatusFromResultSet;
import static nl.thomas.arensman.todo.list.utils.database.StatusDatabaseUtils.selectStatusWhereName;
import static nl.thomas.arensman.todo.list.utils.database.TaskDatabaseUtils.*;

@RestController
public class PatchTaskById {

    @Autowired
    private DataSource dataSource;

    private static final String PATCH_TASK_BY_ID = "task/{id}";

    @PatchMapping(PATCH_TASK_BY_ID)
    public ResponseEntity<String> patchTaskById (@PathVariable String id, @RequestBody String body) throws SQLException {
        try {
            validateTaskId(id);

            ResultSet taskResultSet = selectTaskById(dataSource, Integer.parseInt(id));
            validateResultSet(taskResultSet, Integer.parseInt(id));

            TaskRequestBody taskRequestBody = new ObjectMapper().readValue(body, TaskRequestBody.class);
            String validTaskName = validateRequestName(taskRequestBody.getTaskName());
            Status validStatus = validateRequestStatus(taskRequestBody.getTaskStatusName());
            Integer taskPriority = taskRequestBody.getTaskPriority();
            String validDeadlineDateTime = validateRequestDeadline(taskRequestBody.getTaskDeadline());

            updateTaskById(dataSource, validTaskName, validStatus.getStatusId(), taskPriority, validDeadlineDateTime, Integer.parseInt(id));
            return createResponseEntity("Successfully updated task with id: " + id, HttpStatus.OK, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private void validateResultSet(ResultSet taskResultSet, Integer id) throws SQLException {
        if (!taskResultSet.next())
            throw new RuntimeException("There are no tasks found with task_id" + id);
    }

    private void validateTaskId(String id) {
        if (!strIsIntParsable(id))
            throw new RuntimeException("task_id must by of type Integer");
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
