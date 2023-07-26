import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVreader {
    String filePath = "src/resources/MyFilms.csv";
    File file;
    Scanner filmsFile;
    Scanner oneLine;

    public CSVreader() {
        file = new File(filePath);
        try {
            filmsFile = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        }
        filmsFile.useDelimiter("[\n;]");
    }

    public String nextLine() {
        String nextLine;
        nextLine = filmsFile.nextLine();
        return nextLine;
    }

    public String nextValue() {
        String nextWord;
        nextWord = filmsFile.next();
        return nextWord;
    }
}
