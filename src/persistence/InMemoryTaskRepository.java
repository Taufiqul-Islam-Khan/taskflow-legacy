package persistence;

import model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryTaskRepository implements ITaskRepository {

    private List<Task> store;

    public InMemoryTaskRepository() {
        this.store = new ArrayList<>();
        seedData();
    }

    // Pre-populate with sample data so there's something to display
    private void seedData() {
        store.add(new Task(1, "Fix login bug",       "Users cannot log in with special chars in password", Task.PRIORITY_HIGH,   "alice"));
        store.add(new Task(2, "Write unit tests",    "Cover the logic layer with JUnit tests",             Task.PRIORITY_MEDIUM, "bob"));
        store.add(new Task(3, "Update README",       "README is missing setup steps",                      Task.PRIORITY_LOW,    "alice"));
        store.add(new Task(4, "Refactor TaskService","Method is too long, split into helpers",              Task.PRIORITY_MEDIUM, "carol"));
        store.add(new Task(5, "Deploy to staging",   "Push latest build to staging server",                Task.PRIORITY_HIGH,   "bob"));

        // mark one task done
        store.get(2).setCompleted(true);
    }

    @Override
    public void addTask(Task t) {
        store.add(t);
    }

    @Override
    public Task getTaskById(int id) {
        for (Task t : store) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(store);
    }

    @Override
    public List<Task> getTasksByUser(String username) {
        List<Task> result = new ArrayList<>();
        for (Task t : store) {
            if (t.getAssignedTo() != null && t.getAssignedTo().equalsIgnoreCase(username)) {
                result.add(t);
            }
        }
        return result;
    }

    @Override
    public boolean updateTask(Task updated) {
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getId() == updated.getId()) {
                store.set(i, updated);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteTask(int id) {
        return store.removeIf(t -> t.getId() == id);
    }
}
