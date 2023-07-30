package def;

import CSV.CSVtextParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class StartClass {

    public Collection<FilmRecord> listOfFilms;
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
                break;
        }
    }

    public static void addFilmRecord() {
        FilmRecord temp = new FilmRecord();
        temp.addFilmRecordFromKeyboard();
    }
}
