package nl.thomas.arensman.todo.list.models;

import nl.thomas.arensman.todo.list.builders.StatusBuilder;

import java.time.LocalDateTime;

public class Status {
    private final int statusId;
    private final String statusName;
    private final String statusHexColor;
    private final LocalDateTime statusCreationDate;

    public Status(StatusBuilder statusBuilder) {
        this.statusId = statusBuilder.getStatusId();
        this.statusName = statusBuilder.getStatusName();
        this.statusHexColor = statusBuilder.getStatusHexColor();
        this.statusCreationDate = statusBuilder.getStatusCreationDate();
    }

    public StatusBuilder getStatusBuilder () {
        return new StatusBuilder();
    }

    public int getStatusId() {
        return statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public String getStatusHexColor() {
        return statusHexColor;
    }

    public LocalDateTime getStatusCreationDate() {
        return statusCreationDate;
    }
}
