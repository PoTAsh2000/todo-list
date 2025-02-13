package nl.thomas.arensman.todo.list.builders;

import java.time.LocalDateTime;

public class StatusBuilder {
    private int statusId;
    private String statusName;
    private String statusHexColor;
    private LocalDateTime statusCreationDate;

    public StatusBuilder setStatusId(int statusId) {
        this.statusId = statusId;
        return this;
    }

    public StatusBuilder setStatusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

    public StatusBuilder setStatusHexColor(String statusHexColor) {
        this.statusHexColor = statusHexColor;
        return this;
    }

    public StatusBuilder setStatusCreationDate(LocalDateTime statusCreationDate) {
        this.statusCreationDate = statusCreationDate;
        return this;
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
