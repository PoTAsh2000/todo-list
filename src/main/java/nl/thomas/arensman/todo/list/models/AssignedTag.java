package nl.thomas.arensman.todo.list.models;

import nl.thomas.arensman.todo.list.builders.AssignedTagBuilder;

import java.time.LocalDateTime;

public class AssignedTag {
    private final int atId;
    private final int atTagId;
    private final int atTaskId;
    private final LocalDateTime atCreationDate;

    public AssignedTag(AssignedTagBuilder assignedTagBuilder) {
        this.atId = assignedTagBuilder.getAtId();
        this.atTagId = assignedTagBuilder.getAtTagId();
        this.atTaskId = assignedTagBuilder.getAtTaskId();
        this.atCreationDate = assignedTagBuilder.getAtCreationDate();
    }

    public static AssignedTagBuilder getAssignedTagBuilder () {
        return new AssignedTagBuilder();
    }

    public int getAtId() {
        return atId;
    }

    public int getAtTagId() {
        return atTagId;
    }

    public int getAtTaskId() {
        return atTaskId;
    }

    public LocalDateTime getAtCreationDate() {
        return atCreationDate;
    }
}
