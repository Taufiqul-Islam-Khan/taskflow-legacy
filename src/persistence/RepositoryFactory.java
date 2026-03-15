package persistence;

import util.AppConfig;
import util.Logger;

public class RepositoryFactory {

    public static ITaskRepository createTaskRepository() {
        String mode = AppConfig.getInstance().get("storage.mode");
        Logger.debug("Storage mode from config: " + mode);

        if ("file".equalsIgnoreCase(mode)) {
            String path = AppConfig.getInstance().get("storage.filepath");
            if (path == null || path.isEmpty()) {
                Logger.warn("storage.filepath not set, falling back to memory.");
                return new InMemoryTaskRepository();
            }
            return new FileTaskRepository(path);
        }

        return new InMemoryTaskRepository();
    }
}
