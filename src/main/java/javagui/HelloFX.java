package javagui;

import csv.CSVtextParser;
import def.RecordManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Objects;

public class HelloFX extends Application {

    public static final String VERSION = " Version 0.001 ";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        URL url = Paths.get("src/resources/fxml/hellofx.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Label versionLabel = (Label)root.lookup("#versionLabel");
        versionLabel.setText(VERSION);
        BorderPane bottomPaneVersion = (BorderPane)root.lookup("#bottomPaneVersion");
        bottomPaneVersion.setBackground(Background.fill(Color.rgb(200,200,200)));
        Label filmsLabel = (Label)root.lookup("#filmsLabel");
        filmsLabel.setText("Just checking");

        CSVtextParser CSVfile = new CSVtextParser();
        RecordManager.loadRecordsFromCSVtoArray(CSVfile);
        filmsLabel.setText(RecordManager.getArrayListOfFilms().get(RecordManager.getArrayListOfFilms().size()-1).toNiceString2());


//        Button button = new Button("OK");
//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Button pressed. Hello World.");
//            }
//        });
//
//        //StackPane root = new StackPane();
//        root.getChildrenUnmodifiable().add(button);
        Scene scene = new Scene(root,600,400);
        stage.setScene(scene);
        stage.setTitle("Watched Films Tracker");
        stage.show();

    }

}