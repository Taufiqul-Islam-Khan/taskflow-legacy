package logic;

import model.Task;
import model.User;
import persistence.ITaskRepository;
import persistence.RepositoryFactory;
import util.AppConfig;
import util.Logger;

import java.util.ArrayList;
import java.util.List;

// Admin-level operations — some logic duplicated from TaskService
public class AdminService {

    private ITaskRepository repo;
    private UserService userService;
    private int maxDisplay;

    public AdminService(UserService userService) {
        this.repo = RepositoryFactory.createTaskRepository();
        this.userService = userService;
        this.maxDisplay = AppConfig.getInstance().getInt("display.max_tasks", 20);
    }

    // NOTE: this is nearly identical to TaskService.getVisibleTasks() — refactor opportunity
    public List<Task> getAllVisibleTasks() {
        List<Task> all = repo.getAllTasks();
        List<Task> result = new ArrayList<>();
        int count = 0;
        for (Task t : all) {
            if (count >= maxDisplay) break;
            result.add(t);
            count++;
        }
        return result;
    }

    public void printUserReport() {
        List<User> allUsers = userService.getAllUsers();
        System.out.println("\n=== User Report ===");
        for (User u : allUsers) {
            List<Task> tasks = repo.getTasksByUser(u.getUsername());
            int pending = 0;
            for (Task t : tasks) {
                if (!t.isCompleted()) pending++;
            }
            System.out.println("  " + u.getUsername() + ": " + tasks.size() + " task(s), " + pending + " pending");
        }
    }

    public boolean reassignTask(int taskId, String newUser) {
        Task t = repo.getTaskById(taskId);
        if (t == null) {
            Logger.warn("Task not found: " + taskId);
            return false;
        }
        if (userService.findUser(newUser) == null) {
            Logger.warn("Cannot reassign to unknown user: " + newUser);
            return false;
        }
        t.SetAssignedTo(newUser);   // uses the inconsistently-named setter from Task
        return repo.updateTask(t);
    }
}
