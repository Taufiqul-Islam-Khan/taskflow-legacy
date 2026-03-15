package presentation;

import model.Task;
import java.util.List;

public class TaskPrinter {

    private static final String SEPARATOR = "+------+------------------------------+----------+----------+-------------------+";
    private static final String HEADER    = "| ID   | Title                        | Priority | Status   | Assigned To       |";

    public void printTable(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("(no tasks to display)");
            return;
        }
        System.out.println(SEPARATOR);
        System.out.println(HEADER);
        System.out.println(SEPARATOR);
        for (Task t : tasks) {
            System.out.printf("| %-4d | %-28s | %-8s | %-8s | %-17s |%n",
                t.getId(),
                truncate(t.getTitle(), 28),
                t.getPriority(),
                t.isCompleted() ? "DONE" : "PENDING",
                truncate(t.getAssignedTo() == null ? "unassigned" : t.getAssignedTo(), 17)
            );
        }
        System.out.println(SEPARATOR);
        System.out.println("  " + tasks.size() + " task(s) shown.");
    }

    public void printTask(Task t) {
        if (t == null) {
            System.out.println("Task not found.");
            return;
        }
        System.out.println("---- Task Detail ----");
        System.out.println("ID:          " + t.getId());
        System.out.println("Title:       " + t.getTitle());
        System.out.println("Description: " + t.getDescription());
        System.out.println("Priority:    " + t.getPriority());
        System.out.println("Status:      " + (t.isCompleted() ? "DONE" : "PENDING"));
        System.out.println("Assigned To: " + t.getAssignedTo());
        System.out.println("Created:     " + t.getCreatedAt());
    }

    private String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() > max ? s.substring(0, max - 1) + "…" : s;
    }
}
