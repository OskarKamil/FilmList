package CSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class CSVreader {
    private final String filePath = "src/resources/MyFilms.csv";
    private final File file;
    private Scanner filmsFile;

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
