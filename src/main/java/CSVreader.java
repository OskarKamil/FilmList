package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVreader {
    String filePath;
    File file;
    Scanner scanner;

    public CSVreader() {
        filePath = "src/resources/MyFilms.csv";
        file = new File(filePath);
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        }
        scanner.useDelimiter("[\n;]");
    }

    public String nextValue() {
        String nextWord;
        nextWord = scanner.next();
        return nextWord;
    }
}
