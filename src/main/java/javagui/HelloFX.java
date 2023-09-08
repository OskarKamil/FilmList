package javagui;

import csv.CSVtextParser;
import def.FilmRecord;
import def.RecordManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class HelloFX extends Application {
    public static final String VERSION = " Version 0.002 ";
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/hellofx.fxml")));
        Label versionLabel = (Label) root.lookup("#versionLabel");
        versionLabel.setText(VERSION);
        stage.getIcons().add(new Image("file:src/main/resources/img/icon2.png"));
        Label filmsLabel = (Label) root.lookup("#filmsLabel");
        /* ################ */


        /* ################ */
        Scene scene = new Scene(root, 1100, 600);
        stage.setScene(scene);
        stage.setTitle("Watched Films Tracker");
        stage.show();
    }

    public static ArrayList<FilmRecord> loadCSVandReturnFilmsArray() {
        CSVtextParser CSVfile = new CSVtextParser();
        RecordManager.loadRecordsFromCSVtoArray(CSVfile);
        ArrayList<FilmRecord> films = RecordManager.getArrayListOfFilms();
        return films;
    }
}