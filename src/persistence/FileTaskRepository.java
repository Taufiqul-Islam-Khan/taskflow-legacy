package persistence;

import model.Task;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

// TODO: implement file-based persistence
// This class is a stub. Storage to file is not yet complete.
public class FileTaskRepository implements ITaskRepository {

    private String filePath;
    private List<Task> cache;

    public FileTaskRepository(String filePath) {
        this.filePath = filePath;
        this.cache = new ArrayList<>();
        Logger.warn("FileTaskRepository is not fully implemented. Data will not persist.");
        // TODO: load from file if exists
    }

    @Override
    public void addTask(Task t) {
        cache.add(t);
        // TODO: write to file
    }

    @Override
    public Task getTaskById(int id) {
        for (Task t : cache) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(cache);
    }

    @Override
    public List<Task> getTasksByUser(String username) {
        List<Task> result = new ArrayList<>();
        for (Task t : cache) {
            if (t.getAssignedTo() != null && t.getAssignedTo().equalsIgnoreCase(username)) {
                result.add(t);
            }
        }
        return result;
    }

    @Override
    public boolean updateTask(Task updated) {
        // TODO: persist change to disk
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getId() == updated.getId()) {
                cache.set(i, updated);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteTask(int id) {
        return cache.removeIf(t -> t.getId() == id);
    }
}
