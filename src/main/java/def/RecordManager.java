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
        if (reader != null) closeReader();
        writer = new CSVwriter();
        writer.setFileColumn(fileColumns);
        writer.saveListIntoCSV(listOfFilms);
        closeWriter();
    }

    private void closeWriter() {
        if (writer != null) writer.close();
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

    public void refreshFurtherIDs(int idOfSelected) {
        for (int i = idOfSelected; i <= listOfFilms.size(); i++) {
            FilmRecord record = listOfFilms.get(i - 1);
            record.setIdInList(i);
        }

    }

    public void deleteRecordFromList(FilmRecord selected) {
        if (listOfFilms.isEmpty() || selected.getIdInList() == 0) return;
        int idOfSelected = selected.getIdInList();
        listOfFilms.remove(selected);
        refreshFurtherIDs(idOfSelected);
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
            } catch (Exception e) {
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

    public String getAverageWatchStatistics() {
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
        Collections.sort(dates);

        int daysBetween = (int) (DAYS.between(dates.get(0), dates.get(dates.size() - 1)) + 1);
        int numberOfFilms = getNumberOfTotalWatchedFilms();
        String statisticsString="";

        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        String averageFilmPerDay = (twoDecimals.format((double) numberOfFilms / daysBetween));
        statisticsString += averageFilmPerDay + " films per day";


        System.out.println("days between two dates are : " + daysBetween);

        return statisticsString;
    }
}
