package nl.thomas.arensman.todo.list.builders;

import nl.thomas.arensman.todo.list.models.Task;

import java.time.LocalDateTime;

public class TaskBuilder {
    private int taskId;
    private String taskName;
    private int taskStatus;
    private int taskPriority;
    private String taskDeadlineDate;
    private String taskCreationDate;

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

    public TaskBuilder setTaskDeadlineDate(String taskDeadlineDate) {
        this.taskDeadlineDate = taskDeadlineDate;
        return this;
    }

    public TaskBuilder setTaskCreationDate(String taskCreationDate) {
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

    public String getTaskDeadlineDate() {
        return taskDeadlineDate;
    }

    public String getTaskCreationDate() {
        return taskCreationDate;
    }

    public Task build () {
        return new Task(this);
    }
}
