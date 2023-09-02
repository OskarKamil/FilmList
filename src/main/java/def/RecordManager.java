package def;

import csv.CSVtextParser;

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
        }
    }

    public static ArrayList<FilmRecord> getArrayListOfFilms()  {
        if(listOfFilms == null)
            throw new NullPointerException("List of films empty. Load list of films first");
        else
            return (ArrayList<FilmRecord>) listOfFilms;
    }
}
