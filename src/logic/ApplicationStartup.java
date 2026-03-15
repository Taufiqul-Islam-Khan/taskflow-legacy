package logic;

import presentation.Menu;
import util.AppConfig;
import util.Logger;

/**
 * Application entry point.
 *
 * Run this class to start TaskFlow.
 * (Not in presentation because it wires together services before handing off to the UI.)
 */
public class ApplicationStartup {

    public static void main(String[] args) {
        // init config + logger
        AppConfig config = AppConfig.getInstance();
        Logger.init();

        Logger.info("Starting " + config.get("app.name") + " v" + config.get("app.version"));

        // wire up services
        logic.UserService userService   = new logic.UserService();
        logic.TaskService taskService   = new logic.TaskService();
        logic.AdminService adminService = new logic.AdminService(userService);

        // hand off to UI
        Menu menu = new Menu(taskService, userService, adminService);
        menu.run();

        Logger.info("Application exited cleanly.");
    }
}
