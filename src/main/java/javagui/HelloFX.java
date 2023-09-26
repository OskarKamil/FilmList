package javagui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloFX extends Application {
    private static Stage stage;

    public static void main(String[] args) {
        launch();
    }

    public static void setStageTitle(String newTitle) {
        stage.setTitle(newTitle);
    }

    @Override
    public void start(Stage stage) throws IOException {
        HelloFX.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainWindow.fxml"));
        Parent root = loader.load();
        Label versionLabel = (Label) root.lookup("#versionLabel");
        versionLabel.setText(AboutSceneController.VERSION);
        URL programIcon = getClass().getClassLoader().getResource("img/icon2.png");
        assert programIcon != null;
        stage.getIcons().add(new Image(programIcon.toString()));
        /* ################ */



        /* ################ */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        // stage.setTitle(AboutSceneController.PROGRAM_NAME);
        MainSceneController controller = loader.getController();
        // controller.setStageAndTitle(stage);
        stage.setOnCloseRequest(event -> {
            event.consume();
            if (controller.shutDown())
                Platform.exit();
        });
        stage.show();
    }
}