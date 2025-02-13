package nl.thomas.arensman.todo.list.builders;

import nl.thomas.arensman.todo.list.models.AssignedTag;

import java.time.LocalDateTime;

public class AssignedTagBuilder {
    private int atId;
    private int atTagId;
    private int atTaskId;
    private LocalDateTime atCreationDate;

    public AssignedTagBuilder setAtId(int atId) {
        this.atId = atId;
        return this;
    }

    public AssignedTagBuilder setAtTagId(int atTagId) {
        this.atTagId = atTagId;
        return this;
    }

    public AssignedTagBuilder setAtTaskId(int atTaskId) {
        this.atTaskId = atTaskId;
        return this;
    }

    public AssignedTagBuilder setAtCreationDate(LocalDateTime atCreationDate) {
        this.atCreationDate = atCreationDate;
        return this;
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

    public AssignedTag build() {
        return new AssignedTag(this);
    }
}
