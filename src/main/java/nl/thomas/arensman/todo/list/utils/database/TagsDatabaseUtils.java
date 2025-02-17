package nl.thomas.arensman.todo.list.utils.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TagsDatabaseUtils {

    public static ResultSet selectTagList (DataSource dataSource) throws SQLException {
        final String query = "SELECT * FROM `tags`";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new SQLException("An exception occurred while trying to select data from tags table");
        }
    }

    public static ResultSet selectTagWhereId (DataSource dataSource, int tagId) {
        final String query = "SELECT * FROM `tags` WHERE `tag_id` = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, tagId);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("An exception occurred while trying to select tag by ID");
        }
    }

    public static ResultSet selectTagWhereName (DataSource dataSource, String tagName) {
        final String query = "SELECT * FROM `tags` WHERE `tag_name` = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tagName);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("An exception occurred while trying to select tag by tag_name: " + tagName);
        }
    }

}
