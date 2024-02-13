package csv;

import models.FilmRecord;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class CSVwriter {
    private String filePath;
    private PrintWriter filmsWriter;
    private String fileColumn;

    public CSVwriter(String newFilePath) {
        try {
            filmsWriter = new PrintWriter(newFilePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("File not found.");
        }
    }

    public void close() {
        filmsWriter.close();
    }

    public void saveListIntoCSV(ObservableList<FilmRecord> list) {
        filmsWriter.println(fileColumn);
        for (FilmRecord filmRecord : list) {
            filmsWriter.print(Objects.toString(filmRecord.getEnglishTitle(), "") + "\t");
            filmsWriter.print(Objects.toString(filmRecord.getOriginalTitle(), "") + "\t");
            filmsWriter.print(Objects.toString(filmRecord.getType(), "") + "\t");
            filmsWriter.print(Objects.toString(filmRecord.getReleaseYear(), "") + "\t");
            filmsWriter.print(Objects.toString(filmRecord.getRating(), "") + "\t");
            filmsWriter.print(Objects.toString(filmRecord.getWatchDate(), "") + "\t");
            filmsWriter.print(Objects.toString(filmRecord.getComments(), "") + "\n");
        }
        filmsWriter.close();
        System.out.println("Saved and closed");
    }

    public void setFileColumn(String fileColumns) {
        this.fileColumn = fileColumns;
    }
}
