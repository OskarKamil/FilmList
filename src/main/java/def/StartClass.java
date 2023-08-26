package def;

import CSV.CSVtextParser;

import java.util.Scanner;

public class StartClass {



    public static void main(String[] args) {
        System.out.println("Welcome to Watched Film Record Keeper for people with OCD");
        welcomeMethod();


    }

    public static void welcomeMethod() {
        Scanner keyboard = new Scanner(System.in);
        short choice = -1;
        while (choice < 0 || choice > 5) {
            System.out.println("""
                    Would you like to do:
                    [0] Exit the program
                    [1] See your current film list
                    [2] Add a film to the list
                    [3] Read from CSV file
                    [4] Read from SQLite filw
                    """);
            choice = keyboard.nextShort();
        }
        switch (choice) {
            case 0:
                System.exit(0);
                break;
            case 1:
                System.out.println("Hello"); //doesn't do what should, just random code for now
                break;
            case 2:
                RecordManager.addFilmRecordFromKeyboard(); //just placeholder, probably delete later
                break;
            case 3:
                CSVtextParser CSVfile = new CSVtextParser();
                RecordManager.loadRecordsFromCSVtoArray(CSVfile);
                RecordManager.displayArraylistContent();
                break;
            case 4:
                //read from sql like above from csv
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + choice);
        }
    }
}
