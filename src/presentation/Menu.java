package presentation;

import logic.AdminService;
import logic.TaskService;
import logic.UserService;
import model.Task;
import util.Logger;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private TaskService taskService;
    private UserService userService;
    private AdminService adminService;
    private TaskPrinter printer;
    private Scanner scanner;

    public Menu(TaskService ts, UserService us, AdminService as) {
        this.taskService  = ts;
        this.userService  = us;
        this.adminService = as;
        this.printer  = new TaskPrinter();
        this.scanner  = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to TaskFlow");
        System.out.println("-------------------");
        boolean running = true;
        while (running) {
            printMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    showAllTasks();
                    break;
                case "2":
                    showHighPriority();
                    break;
                case "3":
                    showTasksByUser();
                    break;
                case "4":
                    addNewTask();
                    break;
                case "5":
                    markComplete();
                    break;
                case "6":
                    taskService.printSummary();
                    break;
                case "7":
                    adminService.printUserReport();
                    break;
                case "0":
                    System.out.println("Exiting TaskFlow. Goodbye.");
                    running = false;
                    break;
                default:
                    System.out.println("Unknown option.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n[1] List all tasks");
        System.out.println("[2] High priority tasks");
        System.out.println("[3] Tasks by user");
        System.out.println("[4] Add task");
        System.out.println("[5] Mark task complete");
        System.out.println("[6] Summary report");
        System.out.println("[7] User report (admin)");
        System.out.println("[0] Exit");
        System.out.print("> ");
    }

    private void showAllTasks() {
        List<Task> tasks = taskService.getVisibleTasks();
        printer.printTable(tasks);
    }

    private void showHighPriority() {
        List<Task> tasks = taskService.getHighPriorityTasks();
        printer.printTable(tasks);
    }

    private void showTasksByUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        List<Task> tasks = taskService.getTasksForUser(username);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found for user: " + username);
        } else {
            printer.printTable(tasks);
        }
    }

    private void addNewTask() {
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Description: ");
        String desc = scanner.nextLine().trim();
        System.out.print("Priority (LOW/MEDIUM/HIGH): ");
        String priority = scanner.nextLine().trim().toUpperCase();
        System.out.print("Assign to (username): ");
        String user = scanner.nextLine().trim();

        Task t = taskService.addTask(title, desc, priority, user);
        if (t != null) {
            System.out.println("Task added: " + t);
        } else {
            System.out.println("Failed to add task.");
        }
    }

    private void markComplete() {
        System.out.print("Enter task ID to mark complete: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            boolean ok = taskService.completeTask(id);
            System.out.println(ok ? "Task marked complete." : "Task not found or update failed.");
        } catch (NumberFormatException e) {
            Logger.warn("Invalid task ID entered.");
            System.out.println("Invalid ID.");
        }
    }
}
