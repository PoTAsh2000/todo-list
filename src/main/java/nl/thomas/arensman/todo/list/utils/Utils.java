package nl.thomas.arensman.todo.list.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

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

    public static String marshalObjectToJsonString (Object object) {
        try {
            ObjectWriter jsonMapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return jsonMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("An exception occurred while trying to marshal result Object to JSON String");
        }
    }
}
