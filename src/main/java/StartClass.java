import java.util.Scanner;

public class StartClass {
    public static void main(String[] args) {
        System.out.println("Welcome to Watched Film Record Keeper for people with OCD");

        CSVreader reader = new CSVreader();
        String word;
        word = reader.nextLine();
        System.out.println(word);
        word = reader.nextLine();
        System.out.println(word);
        word = reader.nextLine();
        System.out.println(word);
        word = reader.nextLine();
        System.out.println(word);


    }

    public static void welcomeMethod() {
        Scanner keyboard = new Scanner(System.in);
        short choice = 0;
        while (choice != 1 && choice != 2) {
            System.out.println("Would you like to do:\n" + "[1] See your current film list\n" + "[2] Add a film to the list");
            choice = keyboard.nextShort();
        }
        switch (choice) {
            case 1:
                System.out.println("Hello");
                addFilmRecord();

                break;
            case 2:
                break;
        }
    }

    public static void addFilmRecord() {
        FilmRecord temp = new FilmRecord();
        temp.addFilmRecordFromKeyboard();
    }
}
