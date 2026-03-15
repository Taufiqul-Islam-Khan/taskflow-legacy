package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {

    private static AppConfig instance;
    private Properties props;

    // hardcoded fallback path — students may need to fix this for their environment
    private static final String DEFAULT_CONFIG_PATH = "config/config.properties";

    private AppConfig() {
        props = new Properties();
        try {
            FileInputStream fis = new FileInputStream(DEFAULT_CONFIG_PATH);
            props.load(fis);
            fis.close();
        } catch (IOException e) {
            System.out.println("Warning: Could not load config file at " + DEFAULT_CONFIG_PATH);
            System.out.println("Using default values.");
            loadDefaults();
        }
    }

    private void loadDefaults() {
        props.setProperty("app.name", "TaskFlow");
        props.setProperty("app.version", "1.0");
        props.setProperty("storage.mode", "memory");
        props.setProperty("display.max_tasks", "20");
        props.setProperty("display.show_completed", "true");
        props.setProperty("debug.enabled", "false");
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public String get(String key) {
        return props.getProperty(key, "");
    }

    public int getInt(String key, int defaultVal) {
        try {
            return Integer.parseInt(props.getProperty(key));
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public boolean getBool(String key) {
        return "true".equalsIgnoreCase(props.getProperty(key, "false"));
    }
}
