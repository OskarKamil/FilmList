package def;

import csv.CSVreader;
import csv.CSVwriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

import static java.time.temporal.ChronoUnit.DAYS;

public class RecordManager {

    private ObservableList<FilmRecord> listOfFilms;
    private String fileColumns;
    private CSVreader reader;
    private CSVwriter writer;
    private String filePath;

    public void closeReader() {
        reader.closeFile();
    }

    private void closeWriter() {
        if (writer != null) writer.close();
    }

    public void deleteRecordFromList(FilmRecord selected) {
        if (listOfFilms.isEmpty() || selected.getIdInList() == 0) return;
        int idOfSelected = selected.getIdInList();
        listOfFilms.remove(selected);
        refreshFurtherIDs(idOfSelected);
    }

    public double getAverageFilmRating() {
        double averageRating = 0;
        int correction = 0;
        for (FilmRecord film : listOfFilms) {
            try {
                averageRating += Double.parseDouble(film.getRating());
            } catch (Exception e) {
                correction++;

            }

        }
        averageRating /= listOfFilms.size() - correction;

        return averageRating;
    }

    public String getAverageWatchStatistics() {
        if (listOfFilms.isEmpty())
            return "0";
        ArrayList<LocalDate> dates = new ArrayList<>(listOfFilms.size());
        int correction = 0;

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (FilmRecord film : listOfFilms) {
            try {
                LocalDate date = LocalDate.parse(film.getWatchDate(), format);
                dates.add(date);
            } catch (Exception e) {
                correction++;
                //doesn't do anything yet, but may indicate not valid film so can correct number of films and days
            }

        }
        if (dates.isEmpty()) // No dates found
            return "0";
        Collections.sort(dates);

        int daysBetween = (int) (DAYS.between(dates.get(0), dates.get(dates.size() - 1)) + 1);
        int numberOfFilms = getNumberOfTotalWatchedFilms();
        String statisticsString = "";

        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        String averageFilmPerDay = (twoDecimals.format((double) numberOfFilms / daysBetween));
        statisticsString += averageFilmPerDay + " films per day";

        return statisticsString;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ObservableList<FilmRecord> getListOfFilms() {
        return listOfFilms;
    }

    public int getNumberOfTotalWatchedFilms() {
        return listOfFilms.size();
    }

    public void loadRecordsFromCSVtoArray(CSVreader CSVfile) {
        listOfFilms = FXCollections.observableArrayList(CSVfile.getAllFilmsRecordsFromFile());
    }

    public void refreshFurtherIDs(int idOfSelected) {
        for (int i = 0; i < listOfFilms.size(); i++) {
            FilmRecord record = listOfFilms.get(i);
            if (record.getIdInList() >= idOfSelected) record.setIdInList(record.getIdInList() - 1);
        }

    }

    public void startReader(String newFilePath) {
        reader = new CSVreader(newFilePath);
        filePath = newFilePath;
        fileColumns = reader.getFileColumns();
        loadRecordsFromCSVtoArray(reader);
    }

    public void startWriter(String newFilePath) {
        if (reader != null) closeReader();
        writer = new CSVwriter(newFilePath);
        writer.setFileColumn(fileColumns);
        writer.saveListIntoCSV(listOfFilms);
        closeWriter();
    }
}
