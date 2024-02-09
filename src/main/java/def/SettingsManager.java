package def;

import javagui.Launcher;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

public class SettingsManager {

    private static final String CONFIG_FILE_NAME = "config.ini";
    static boolean defaultDateIsToday = true;
    static boolean autoSave = false;
    static String lastPath = "";

    public static void changeAutoSave() {
        autoSave = !autoSave;
    }

    public static void changeDefaultDate() {
        defaultDateIsToday = !defaultDateIsToday;
    }

    public static boolean getAutoSave() {
        return autoSave;
    }

    public static boolean getDefaultDateIsToday() {
        return defaultDateIsToday;
    }

    public static String getLastPath() {
        return lastPath;
    }

    public static void setLastPath(String path) {
        lastPath = path;
    }

    public static String getValues() {
        return ("defaultDateIsToday " + defaultDateIsToday + "\nautoSave " + autoSave) + "\nlastPath " + lastPath;
    }

    private static boolean isRunningFromIDE() {
        String className = Launcher.class.getName().replace('.', '/');
        String classJar = Launcher.class.getResource("/" + className + ".class").toString();
        return !classJar.startsWith("jar:");
    }

    public static void readSettingsFile() {
        Wini ini = null;
        String path = CONFIG_FILE_NAME;
        if (isRunningFromIDE())
            path = "src/main/resources/txt/" + CONFIG_FILE_NAME;
        File file = new File(path);
        if (!file.exists()) {
            SettingsManager.saveSettingsFile();
            return;
        }
        try {
            ini = new Wini(file);
        } catch (IOException e) {
            e.printStackTrace(System.out);
            saveSettingsFile();
            return;
        }
        defaultDateIsToday = ini.get("CONFIG", "defaultDateIsToday", boolean.class);
        autoSave = ini.get("CONFIG", "autoSave", boolean.class);
        lastPath = ini.get("CONFIG", "lastPath", String.class);
        System.out.println("settings file read successfully");
    }

    public static void saveSettingsFile() {
        Wini ini;
        String path = CONFIG_FILE_NAME;
        if (isRunningFromIDE())
            path = "src/main/resources/txt/" + CONFIG_FILE_NAME;
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Can't create config.ini");
                e.printStackTrace(System.out);
            }
        }

        try {
            ini = new Wini(file);
        } catch (IOException e) {
            e.printStackTrace(System.out);
            return;
        }
        ini.put("CONFIG", "defaultDateIsToday", defaultDateIsToday);
        ini.put("CONFIG", "autoSave", autoSave);
        ini.put("CONFIG", "lastPath", lastPath);
        try {
            ini.store();
        } catch (IOException e) {
            System.err.println("Can't save config.ini");
            e.printStackTrace(System.out);
        }
        System.out.println("settings file saved");
    }
}

