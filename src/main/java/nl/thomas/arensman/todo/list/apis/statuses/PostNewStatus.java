package nl.thomas.arensman.todo.list.apis.statuses;

import nl.thomas.arensman.todo.list.json.schemas.StatusBodyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

import static nl.thomas.arensman.todo.list.utils.database.StatusDatabaseUtils.*;
import static nl.thomas.arensman.todo.list.utils.HttpUtils.*;
import static nl.thomas.arensman.todo.list.utils.Utils.*;

@RestController
public class PostNewStatus {

    private static final String POST_NEW_STATUS_REQUEST = "/status";

    @Autowired
    private DataSource dataSource;

    @PostMapping(POST_NEW_STATUS_REQUEST)
    public ResponseEntity<String> postNewStatus (@RequestBody String requestBody) {
        try {
            final StatusBodyRequest statusBodyRequest = (StatusBodyRequest) unmarshalJsonStringToJavaObjects(requestBody, new StatusBodyRequest());
            validateRequest(dataSource, statusBodyRequest);

            insertStatus(dataSource, statusBodyRequest.getStatusName(), statusBodyRequest.getStatusHexColor());

            return createResponseEntity(String.format("Record '%s' was successfully created", statusBodyRequest.getStatusName()), HttpStatus.CREATED, getDefaultHeaders());
        } catch (Exception e) {
            return createErrorResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR, getDefaultHeaders());
        }
    }

    private static void validateRequest (DataSource dataSource, StatusBodyRequest statusBodyRequest) throws SQLException {
        final String statusName = statusBodyRequest.getStatusName();
        validateStatusName(dataSource, statusName);

        final String hexColor = statusBodyRequest.getStatusHexColor();
        if (!strIsNullOrBlank(hexColor))
            validateHexColor(hexColor);
    }

    private static void validateStatusName (DataSource dataSource, String statusName) throws SQLException {
        final String statusNameConstraints = "Status Name field constraints: String name max length = '16' and can only contain characters of pattern [a-zA-Z]";

        if (statusName.length() > 16)
            throw new RuntimeException("Given status name length is larger than 16. " + statusNameConstraints);

        final String invalidCharactersRemaining = statusName.replaceAll("[a-zA-Z]", "");
        if (!strIsNullOrBlank(invalidCharactersRemaining)) {
            final char[] invalidCharacterArray = invalidCharactersRemaining.toCharArray();
            final String invalidCharacters = String.join(",", charArrayToStringArray(invalidCharacterArray));
            throw new RuntimeException(String.format("Given status name contains illegal characters: [%s]. %s", invalidCharacters, statusNameConstraints));
        }

        final ResultSet resultSet = selectStatusWhereName(dataSource, statusName);
        if (resultSet.next())
            throw new RuntimeException(String.format("Status '%s' already exists in the database", statusName));
    }
}
