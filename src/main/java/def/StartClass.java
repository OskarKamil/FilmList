package def;

import CSV.CSVtextParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class StartClass {

    public static List<FilmRecord> listOfFilms;

    public static void main(String[] args) {
        System.out.println("Welcome to Watched Film Record Keeper for people with OCD");
        welcomeMethod();


    }

    public static void welcomeMethod() {
        Scanner keyboard = new Scanner(System.in);
        short choice = 0;
        while (choice < 1 || choice > 3) {
            System.out.println("""
                    Would you like to do:
                    [1] See your current film list
                    [2] Add a film to the list
                    [3] Read from CSV file
                    [4] Read from SQLite file
                    """);
            choice = keyboard.nextShort();
        }
        switch (choice) {
            case 1:
                System.out.println("Hello");
                addFilmRecord();

                break;
            case 2:
                break;
            case 3:
                CSVtextParser CSVfile = new CSVtextParser();
                readRecordsFromCSV(CSVfile);
                System.out.println(listOfFilms.get(0));

                //TODO continue here, the method called above will read lines from the file and will populate the arraylist of records of films
                break;
            case 4:
                //read from sql like above from csv
                break;
        }
    }

    public static void addFilmRecord() {
        FilmRecord temp = new FilmRecord();
        temp.addFilmRecordFromKeyboard();
    }

    public static void readRecordsFromCSV(CSVtextParser CSVfile) {
        listOfFilms = new ArrayList<FilmRecord>();
        listOfFilms.add(CSVfile.getNextFilmRecordFromFile());
    }
}
