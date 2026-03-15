package persistence;

import model.Task;
import java.util.List;

public interface ITaskRepository {
    void addTask(Task t);
    Task getTaskById(int id);
    List<Task> getAllTasks();
    List<Task> getTasksByUser(String username);
    boolean updateTask(Task t);
    boolean deleteTask(int id);
}
