package def;

import CSV.CSVtextParser;

import java.util.ArrayList;
import java.util.List;

public class RecordManager {

    public static List<FilmRecord> listOfFilms;

    public static void addFilmRecordFromKeyboard() {
        FilmRecord temp = new FilmRecord();
        temp.addFilmRecordFromKeyboard();
    }

    public static void loadRecordsFromCSVtoArray(CSVtextParser CSVfile) {
        listOfFilms = new ArrayList<FilmRecord>(3000);
        listOfFilms = CSVfile.getAllFilmsRecordsFromFile();
    }

    public static void displayArraylistContent() {
        int i = 0;
        for (FilmRecord record : listOfFilms) {
            System.out.println(i++ + " " + record.toString());
            if (i == 2148)
                System.out.println("LASTLASTLAST");


        }
    }
}
