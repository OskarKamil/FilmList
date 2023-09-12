package javagui;

import csv.CSVtextParser;
import def.FilmRecord;
import def.RecordManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class HelloFX extends Application {
    private static ObservableList<FilmRecord> films;

    public static ObservableList<FilmRecord> getFilms() {
        return films;
    }

    public static double getAverageFilmRating() {
        double averageRating = 0;
        for (FilmRecord film : films) {
            averageRating += Double.parseDouble(film.getRating());
        }
        averageRating /= films.size();

        return averageRating;
    }

    public static int getNumberOfTotalWatchedFilms() {
        return films.size();
    }

    public static void main(String[] args) {
        launch();
    }

    private static ArrayList<FilmRecord> loadCSVandReturnFilmsArray() {
        CSVtextParser CSVfile = new CSVtextParser();
        RecordManager.loadRecordsFromCSVtoArray(CSVfile);
        return RecordManager.getArrayListOfFilms();
    }

    public static void loadCSVandSaveToLocalObject() {
        films = FXCollections.observableArrayList(loadCSVandReturnFilmsArray());
    }

    public static String getAverageFilmPerDay() {
        return "*** coming soon ***";
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/hellofx.fxml")));
        Label versionLabel = (Label) root.lookup("#versionLabel");
        versionLabel.setText(AboutSceneController.VERSION);
        URL programIcon = getClass().getClassLoader().getResource("img/icon2.png");
        assert programIcon != null;
        stage.getIcons().add(new Image(programIcon.toString()));
        /* ################ */



        /* ################ */
        Scene scene = new Scene(root, 1400, 800);
        stage.setScene(scene);
        stage.setTitle(AboutSceneController.PROGRAM_NAME);
        stage.show();
    }
}