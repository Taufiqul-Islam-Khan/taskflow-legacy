package logic;

import model.Task;
import persistence.ITaskRepository;
import persistence.RepositoryFactory;
import util.AppConfig;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Core service class for task management.
 * Handles business logic for creating, updating, filtering tasks.
 *
 * Note: this class is doing too much. Should be split in future refactor.
 */
public class TaskService {

    private ITaskRepository repo;
    private int nextId;
    private int maxDisplay;
    private boolean showCompleted;

    public TaskService() {
        this.repo = RepositoryFactory.createTaskRepository();
        this.nextId = 100; // arbitrary starting ID for new tasks
        this.maxDisplay = AppConfig.getInstance().getInt("display.max_tasks", 20);
        this.showCompleted = AppConfig.getInstance().getBool("display.show_completed");
        Logger.info("TaskService initialized. maxDisplay=" + maxDisplay + ", showCompleted=" + showCompleted);
    }

    // Also used by AdminService — duplicated logic below (code smell)
    public List<Task> getVisibleTasks() {
        List<Task> all = repo.getAllTasks();
        List<Task> result = new ArrayList<>();
        int count = 0;
        for (Task t : all) {
            if (!showCompleted && t.isCompleted()) continue;
            if (count >= maxDisplay) break;
            result.add(t);
            count++;
        }
        return result;
    }

    public Task addTask(String title, String description, String priority, String assignedTo) {
        if (title == null || title.trim().isEmpty()) {
            Logger.warn("Attempted to add task with empty title.");
            return null;
        }
        // validate priority
        if (!priority.equals(Task.PRIORITY_LOW) &&
            !priority.equals(Task.PRIORITY_MEDIUM) &&
            !priority.equals(Task.PRIORITY_HIGH)) {
            Logger.warn("Invalid priority '" + priority + "', defaulting to MEDIUM");
            priority = Task.PRIORITY_MEDIUM;
        }

        Task t = new Task(nextId++, title, description, priority, assignedTo);
        repo.addTask(t);
        Logger.info("Added task: " + t);
        return t;
    }

    public boolean completeTask(int id) {
        Task t = repo.getTaskById(id);
        if (t == null) {
            Logger.warn("Task not found: " + id);
            return false;
        }
        t.setCompleted(true);
        return repo.updateTask(t);
    }

    public boolean deleteTask(int id) {
        return repo.deleteTask(id);
    }

    public List<Task> getTasksForUser(String username) {
        return repo.getTasksByUser(username);
    }

    // duplicated from getVisibleTasks — subtle bug: ignores showCompleted flag here
    public List<Task> getHighPriorityTasks() {
        List<Task> all = repo.getAllTasks();
        List<Task> result = new ArrayList<>();
        int count = 0;
        for (Task t : all) {
            if (Task.PRIORITY_HIGH.equals(t.getPriority())) {
                if (count >= maxDisplay) break;
                result.add(t);
                count++;
            }
        }
        return result;
    }

    public List<Task> filterByPriority(String priority) {
        List<Task> all = repo.getAllTasks();
        List<Task> result = new ArrayList<>();
        for (Task t : all) {
            // BUG: case-sensitive compare may miss lowercase input
            if (priority.equals(t.getPriority())) {
                result.add(t);
            }
        }
        return result;
    }

    public Task findById(int id) {
        return repo.getTaskById(id);
    }

    // utility: count tasks per user — only counts pending tasks
    public int countPendingForUser(String username) {
        List<Task> tasks = repo.getTasksByUser(username);
        int count = 0;
        for (Task t : tasks) {
            if (!t.isCompleted()) count++;
        }
        return count;
    }

    // prints a summary report to console
    public void printSummary() {
        List<Task> all = repo.getAllTasks();
        int total = all.size();
        int done = 0;
        for (Task t : all) {
            if (t.isCompleted()) done++;
        }
        System.out.println("=== Task Summary ===");
        System.out.println("Total:     " + total);
        System.out.println("Completed: " + done);
        System.out.println("Pending:   " + (total - done));
    }
}
