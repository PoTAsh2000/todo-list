package nl.thomas.arensman.todo.list.models;

import nl.thomas.arensman.todo.list.builders.TaskBuilder;

import java.time.LocalDateTime;

public class Task {
    private final int taskId;
    private final String taskName;
    private final int taskStatus;
    private final int taskPriority;
    private final String taskDeadlineDate;
    private final String taskCreationDate;

    public Task(TaskBuilder taskBuilder) {
        this.taskId = taskBuilder.getTaskId();
        this.taskName = taskBuilder.getTaskName();
        this.taskStatus = taskBuilder.getTaskStatus();
        this.taskPriority = taskBuilder.getTaskPriority();
        this.taskDeadlineDate = taskBuilder.getTaskDeadlineDate();
        this.taskCreationDate = taskBuilder.getTaskCreationDate();
    }

    public static TaskBuilder getTaskBuilder () {
        return new TaskBuilder();
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
}
