package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class CSVreader {
    private Scanner filmsFile;

    public CSVreader() {
        try {
            URL filmsCSVfile = getClass().getClassLoader().getResource("txt/MyFilms.csv");
            assert filmsCSVfile != null;
            filmsFile = new Scanner(new File(filmsCSVfile.toURI()), StandardCharsets.UTF_8);
        } catch (URISyntaxException | IOException e) {
            System.err.println("File not found.");
        }
        filmsFile.useDelimiter("[\n;]");
    }

    public String nextLine() {
        if (hasNextLine()) return filmsFile.nextLine();
        else return "";
    }

    public boolean hasNextLine() {
        return filmsFile.hasNextLine();
    }

    public String nextValue() {
        String nextWord;
        nextWord = filmsFile.next();
        return nextWord;
    }
}
