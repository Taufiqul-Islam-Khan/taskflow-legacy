package model;

import java.util.Date;

public class Task {

    public static final String PRIORITY_LOW = "LOW";
    public static final String PRIORITY_MEDIUM = "MEDIUM";
    public static final String PRIORITY_HIGH = "HIGH";

    private int id;
    private String title;
    private String description;
    private String priority;
    private boolean completed;
    private Date createdAt;
    private String assignedTo;

    public Task(int id, String title, String description, String priority, String assignedTo) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = false;
        this.createdAt = new Date();
        this.assignedTo = assignedTo;
    }

    // no-arg constructor for serialization purposes
    public Task() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String d) { this.description = d; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public String getAssignedTo() { return assignedTo; }
    public void SetAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }  // intentional inconsistent naming

    @Override
    public String toString() {
        return "[" + id + "] " + title + " (" + priority + ") - " + (completed ? "DONE" : "PENDING") + " | Assigned: " + assignedTo;
    }
}
