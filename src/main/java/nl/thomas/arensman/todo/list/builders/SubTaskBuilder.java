package nl.thomas.arensman.todo.list.builders;

import java.time.LocalDateTime;

public class SubTaskBuilder {
    private int subId;
    private int subTaskId;
    private String subName;
    private int subStatus;
    private LocalDateTime subDealineDate;
    private LocalDateTime subCreationDate;

    public SubTaskBuilder setSubId(int subId) {
        this.subId = subId;
        return this;
    }

    public SubTaskBuilder setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
        return this;
    }

    public SubTaskBuilder setSubName(String subName) {
        this.subName = subName;
        return this;
    }

    public SubTaskBuilder setSubStatus(int subStatus) {
        this.subStatus = subStatus;
        return this;
    }

    public SubTaskBuilder setSubDealineDate(LocalDateTime subDealineDate) {
        this.subDealineDate = subDealineDate;
        return this;
    }

    public SubTaskBuilder setSubCreationDate(LocalDateTime subCreationDate) {
        this.subCreationDate = subCreationDate;
        return this;
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
