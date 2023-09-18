package javagui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HelloFX extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/mainWindow.fxml")));
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