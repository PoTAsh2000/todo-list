package nl.thomas.arensman.todo.list.utils.database;

import nl.thomas.arensman.todo.list.json.schemas.TagBodyRequest;
import nl.thomas.arensman.todo.list.models.Tag;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static nl.thomas.arensman.todo.list.models.Tag.getTagBuilder;
import static nl.thomas.arensman.todo.list.utils.Utils.strIsNullOrBlank;

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

    public static Tag tagResultSetToTag (ResultSet resultSet) throws SQLException {
        resultSet.next();
        return getTagBuilder()
                .setTagId(resultSet.getInt("tag_id"))
                .setTagName(resultSet.getString("tag_name"))
                .setTagHexColor(resultSet.getString("tag_hex_color"))
                .setTagCreationDate(resultSet.getString("tag_creation_date"))
                .build();
    }

    public static void postNewTag (DataSource dataSource, TagBodyRequest tagBodyRequest) {
        final String query = "INSERT INTO `tags` (`tag_name`, `tag_hex_color`) VALUES (?, ?)";
        final String tagName = tagBodyRequest.getTagName().toUpperCase();
        final String tagHexColor = tagBodyRequest.getTagHexColor().toLowerCase();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tagName);
            preparedStatement.setString(2, tagHexColor);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("An exception occurred while trying to insert new tag: " + tagName);
        }
    }

    public static void updateTagWhereId(DataSource dataSource, TagBodyRequest tagBodyRequest, int tagId) {
        final String query = "UPDATE `tags` SET `tag_name` = ?, `tag_hex_color` = ? WHERE `tag_id` = ?";
        final String tagName = tagBodyRequest.getTagName().toUpperCase();
        final String tagHexColor = tagBodyRequest.getTagHexColor().toLowerCase();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tagName);
            preparedStatement.setString(2, tagHexColor);
            preparedStatement.setInt(3, tagId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteTagWhereId (DataSource dataSource, int tagId) {
        final String query = "DELETE FROM `tags` WHERE (`tag_id` = ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, tagId);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
