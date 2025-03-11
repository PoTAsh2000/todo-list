package nl.thomas.arensman.todo.list.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static LocalDateTime stringToLocalDataTime (String dateTimeIn, String dateTimeFormatIn) {
        return LocalDateTime.parse(dateTimeIn, DateTimeFormatter.ofPattern(dateTimeFormatIn));
    }

    public static boolean strIsIntParsable (String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean strIsNullOrBlank (String string) {
        return string == null || string.isBlank();
    }

    public static String[] charArrayToStringArray (char[] charArray) {
        String[] stringArray = new String[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            stringArray[i] = String.valueOf(charArray[i]);
        return stringArray;
    }

    public static boolean strMatchesDateTimePattern (String str, String pattern) {
        try {
            LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void preparedStatementSetNullableInt(PreparedStatement preparedStatement, int index, Integer intValue) throws SQLException {
        preparedStatement.setObject(index, null);
        if (intValue != null)
            preparedStatement.setInt(index, intValue);
    }

    public static String marshalObjectToJsonString (Object object) {
        try {
            final ObjectWriter jsonMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return jsonMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("An exception occurred while trying to marshal result Object to JSON String");
        }
    }

    public static Object unmarshalJsonStringToJavaObjects(String json, Object targetObject) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, targetObject.getClass());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to unmarshal input JSON String to class of: " + targetObject.getClass());
        }
    }

    public static void validateHexColor(String hexColor) {
        final String hexColorConstraints = "Hex Color field constraints: Hex color must start with an '#' and the code must be 7 characters long, including the '#'. The code only allows characters of pattern [a-zA-Z0-9]";

        if (strIsNullOrBlank(hexColor))
            return;

        if (!hexColor.startsWith("#"))
            throw new RuntimeException("Hex color does not start with a '#'. " + hexColorConstraints);

        if (hexColor.length() != 7)
            throw new RuntimeException("Hex color length does not add up to 7. " + hexColorConstraints);

        final String hexColorCode = hexColor.substring(1); // Get the hex color code excluding the #
        final String invalidCharactersRemaining = hexColorCode.replaceAll("[a-zA-Z0-9]", ""); // remove valid characters from String, if any characters remain the hex code must be invalid
        if (!strIsNullOrBlank(invalidCharactersRemaining)) {
            final char[] invalidCharacterArray = invalidCharactersRemaining.toCharArray();
            final String invalidCharacters = String.join(",", charArrayToStringArray(invalidCharacterArray));
            throw new RuntimeException(String.format("Hex color code contains invalid characters: [%s]. %s", invalidCharacters, hexColorConstraints));
        }
    }
}
