package nl.thomas.arensman.todo.list.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;

import static nl.thomas.arensman.todo.list.utils.Utils.marshalObjectToJsonString;

public class HttpUtils {

    public static MultiValueMap<String, String> getDefaultHeaders () {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put(HttpHeaders.ACCEPT, List.of("application/json"));
        headers.put(HttpHeaders.CONTENT_TYPE, List.of("application/json"));
        return headers;
    }

    public static ResponseEntity<String> createResponseEntity (Object response, HttpStatus status, MultiValueMap<String, String> headers) {
        return new ResponseEntity<>(marshalObjectToJsonString(response), headers, status);
    }

    public static ResponseEntity<String> createErrorResponseEntity (Exception exception, HttpStatus status, MultiValueMap<String, String> headers) {
        HashMap<String, String> errorResponseObject = new HashMap<>() {{
            put("Exception", exception.getMessage());
        }};
        return new ResponseEntity<>(marshalObjectToJsonString(errorResponseObject), headers, status);
    }

}
