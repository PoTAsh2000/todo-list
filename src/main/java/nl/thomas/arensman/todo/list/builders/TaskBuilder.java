package nl.thomas.arensman.todo.list.builders;

import java.time.LocalDateTime;

public class TaskBuilder {
    private int taskId;
    private String taskName;
    private int taskStatus;
    private int taskPriority;
    private LocalDateTime taskDeadlineDate;
    private LocalDateTime taskCreationDate;

    public TaskBuilder setTaskId(int taskId) {
        this.taskId = taskId;
        return this;
    }

    public TaskBuilder setTaskName(String taskName) {
        this.taskName = taskName;
        return this;
    }

    public TaskBuilder setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }

    public TaskBuilder setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
        return this;
    }

    public TaskBuilder setTaskDeadlineDate(LocalDateTime taskDeadlineDate) {
        this.taskDeadlineDate = taskDeadlineDate;
        return this;
    }

    public TaskBuilder setTaskCreationDate(LocalDateTime taskCreationDate) {
        this.taskCreationDate = taskCreationDate;
        return this;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public LocalDateTime getTaskDeadlineDate() {
        return taskDeadlineDate;
    }

    public LocalDateTime getTaskCreationDate() {
        return taskCreationDate;
    }
}
