package javafx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class HelloFX extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        URL url = Paths.get("src/resources/fxml/hellofx.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        Button button = new Button("OK");
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