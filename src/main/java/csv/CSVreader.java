package CSV;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class CSVreader {
    private final String filePath = "src/resources/MyFilms.csv";
    private final File file;
    private Scanner filmsFile;

    public CSVreader() {
        file = new File(filePath);
        try {
            filmsFile = new Scanner(file, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        filmsFile.useDelimiter("[\n;]");
    }

    public String nextLine() {
        if (hasNextLine())
            return filmsFile.nextLine();
        else
            return "";
    }

    public boolean hasNextLine(){
        return filmsFile.hasNextLine();
    }

    public String nextValue() {
        String nextWord;
        nextWord = filmsFile.next();
        return nextWord;
    }
}
