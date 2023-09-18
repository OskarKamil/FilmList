package def;

import csv.CSVreader;
import csv.CSVwriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class RecordManager {

    private ObservableList<FilmRecord> listOfFilms;
    private String fileColumns;
    private CSVreader reader;
    private CSVwriter writer;

    public static void addFilmRecordFromKeyboard() {
        FilmRecord temp = new FilmRecord();
        temp.addFilmRecordFromKeyboard();
    }

    public void loadRecordsFromCSVtoArray(CSVreader CSVfile) {
        listOfFilms = FXCollections.observableArrayList(CSVfile.getAllFilmsRecordsFromFile());
    }

    private ArrayList<FilmRecord> loadCSVandReturnFilmsArray() {
        CSVreader CSVfile = new CSVreader();
        loadRecordsFromCSVtoArray(CSVfile);
        return getArrayListOfFilms();
    }

    public void startWriter() {
        if (reader != null)
            closeReader();
        writer = new CSVwriter();
        writer.setFileColumn(fileColumns);
        writer.saveListIntoCSV(listOfFilms);
        closeWriter();
    }

    private void closeWriter() {
        if (writer != null)
            writer.close();
    }

    public void displayArraylistContent() {
        int i = 0;
        for (FilmRecord record : listOfFilms) {
            System.out.println(i++ + " " + record.toString());
        }
    }

    public ArrayList<FilmRecord> getArrayListOfFilms() {
        return (ArrayList<FilmRecord>) listOfFilms.stream().toList();
    }

    public String getFileColumns() {
        return fileColumns;
    }

    public void startReader() {
        reader = new CSVreader();
        fileColumns = reader.getFileColumns();
        loadRecordsFromCSVtoArray(reader);
    }

    public void closeReader() {
        reader.closeFile();
    }

    public double getAverageFilmRating() {
        double averageRating = 0;
        int correction = 0;
        for (FilmRecord film : listOfFilms) {
            try {
                averageRating += Double.parseDouble(film.getRating());
            } catch (NullPointerException e) {
                correction++;

            }

        }
        averageRating /= listOfFilms.size() - correction;

        return averageRating;
    }

    public int getNumberOfTotalWatchedFilms() {
        System.out.println("Films number is: " + listOfFilms.size());
        return listOfFilms.size();
    }

    public ObservableList<FilmRecord> getListOfFilms() {
        return listOfFilms;
    }

    public String getAverageFilmPerDay() {
        return "coming soon";
    }
}
