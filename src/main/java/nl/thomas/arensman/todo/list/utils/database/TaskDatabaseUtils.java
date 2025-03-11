package nl.thomas.arensman.todo.list.utils.database;

import nl.thomas.arensman.todo.list.models.Task;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static nl.thomas.arensman.todo.list.models.Task.getTaskBuilder;
import static nl.thomas.arensman.todo.list.utils.Utils.preparedStatementSetNullableInt;

public class TaskDatabaseUtils {

    public static Task resultSetToTask (ResultSet resultSet) throws SQLException {
        return getTaskBuilder()
                .setTaskId(resultSet.getInt("task_id"))
                .setTaskName(resultSet.getString("task_name"))
                .setTaskStatus(resultSet.getInt("task_status"))
                .setTaskPriority(resultSet.getInt("task_priority"))
                .setTaskDeadlineDate(resultSet.getString("task_deadline_date"))
                .setTaskDeadlineDate(resultSet.getString("task_creation_date"))
                .build();
    }

    public static void insertNewTask (DataSource dataSource, String taskName, Integer taskStatusId, Integer taskPriority, String taskDeadlineDate) {
        String query = "INSERT INTO `tasks` (`task_name`, `task_status`, `task_priority`, `task_deadline_date`) VALUES (?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, taskName);
            preparedStatement.setInt(2, taskStatusId);
            preparedStatementSetNullableInt(preparedStatement, 3, taskPriority);
            preparedStatement.setString(4, taskDeadlineDate);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
