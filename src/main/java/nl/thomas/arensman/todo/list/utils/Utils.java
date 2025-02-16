package nl.thomas.arensman.todo.list.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

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
}
