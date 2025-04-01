package nl.thomas.arensman.todo.list.utils.database;

import nl.thomas.arensman.todo.list.models.Task;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        final String query = "INSERT INTO `tasks` (`task_name`, `task_status`, `task_priority`, `task_deadline_date`) VALUES (?, ?, ?, ?)";
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

    public static ResultSet selectTaskById(DataSource dataSource, int taskId) {
        final String query = "SELECT * FROM `tasks` WHERE `task_id` = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, taskId);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet selectTaskByName(DataSource dataSource, String name) {
        final String query = "SELECT * FROM `tasks` WHERE `task_name` = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteTaskById (DataSource dataSource, int taskId) {
        final String query = "DELETE FROM `tasks` WHERE `task_id` = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, taskId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateTaskById (DataSource dataSource, String name, Integer status_id, Integer priority, String deadline, Integer id) {
        final String query = "UPDATE `tasks` SET `task_name` = ?, `task_status` = ?, `task_priority` = ?, `task_deadline_date` = ? WHERE task_id = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, status_id);
            preparedStatementSetNullableInt(preparedStatement, 3, priority);
            preparedStatement.setString(4, deadline);
            preparedStatement.setInt(5, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
