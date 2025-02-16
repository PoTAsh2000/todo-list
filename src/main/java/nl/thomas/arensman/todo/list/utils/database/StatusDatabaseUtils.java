package nl.thomas.arensman.todo.list.utils.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class StatusDatabaseUtils {

    public static ResultSet selectStatusWhereId (DataSource dataSource, int statusId) {
        final String query = "SELECT * FROM `statuses` WHERE `status_id` = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, statusId);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("An exception occurred while trying to select status by ID");
        }
    }

    public static ResultSet selectStatusWhereName (DataSource dataSource, String statusName) {
        final String query = "SELECT * FROM `statuses` WHERE `status_name` = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, statusName);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("An exception occurred while trying to select status by status_name: " + statusName);
        }
    }

    public static ResultSet selectAllStatuses(DataSource dataSource) throws SQLException {
        final String query = "SELECT * FROM `statuses`";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new SQLException("An exception occurred while trying to select data from statuses table");
        }
    }

    public static void insertStatus(DataSource dataSource, String statusName, String hexColor) {
        final String query = "INSERT INTO `statuses` (`status_name`, `status_hex_color`) VALUES (?, ?)";
        final String statusNameValue = statusName.toUpperCase();
        final String statusHexColorValue = hexColor.toLowerCase();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, statusNameValue);
            preparedStatement.setString(2, statusHexColorValue);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateStatusWhereId(DataSource dataSource, String statusName, String hexColor, String statusId) {
        final int statusUpdateId = Integer.parseInt(statusId);
        final String statusNameValue = statusName.toUpperCase();
        final String statusHexColorValue = hexColor.toLowerCase();
        final String query = "UPDATE `statuses` SET `status_name` = ?, `status_hex_color` = ? WHERE (`status_id` = ?);";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, statusNameValue);
            preparedStatement.setString(2, statusHexColorValue);
            preparedStatement.setInt(3, statusUpdateId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("An exception occurred while trying to update status with status_id: " + statusId);
        }
    }

    public static void deleteStatusWhereId (DataSource dataSource, int statusId) {
        final String query = "DELETE FROM `statuses` WHERE (`status_id` = ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, statusId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
