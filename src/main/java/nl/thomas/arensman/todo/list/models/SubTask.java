package nl.thomas.arensman.todo.list.models;

import nl.thomas.arensman.todo.list.builders.SubTaskBuilder;

import java.time.LocalDateTime;

public class SubTask {
    private final int subId;
    private final int subTaskId;
    private final String subName;
    private final int subStatus;
    private final LocalDateTime subDealineDate;
    private final LocalDateTime subCreationDate;

    public SubTask(SubTaskBuilder subTaskBuilder) {
        this.subId = subTaskBuilder.getSubId();
        this.subTaskId = subTaskBuilder.getSubTaskId();
        this.subName = subTaskBuilder.getSubName();
        this.subStatus = subTaskBuilder.getSubStatus();
        this.subDealineDate = subTaskBuilder.getSubDealineDate();
        this.subCreationDate = subTaskBuilder.getSubCreationDate();
    }

    public static SubTaskBuilder getSubTaskBuilder () {
        return new SubTaskBuilder();
    }

    public int getSubId() {
        return subId;
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public String getSubName() {
        return subName;
    }

    public int getSubStatus() {
        return subStatus;
    }

    public LocalDateTime getSubDealineDate() {
        return subDealineDate;
    }

    public LocalDateTime getSubCreationDate() {
        return subCreationDate;
    }
}
