package def;

public class SettingsManager {

    static int defaultDateIsToday;

    public static void changeDefaultDate() {
        if (defaultDateIsToday == 0)
            defaultDateIsToday = 1;
        else
            defaultDateIsToday = 0;
    }

    public static void readSettingsFile(){

    }

    public static void saveSettingsFile(){

    }
}
