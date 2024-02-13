package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutSceneController implements Initializable {

    public static final String VERSION = " Version 0.007 ";
    public static final String PROGRAM_NAME = "Watched films tracker";
    @FXML
    private Label copyrightLabel;

    @FXML
    private Label emptyLabel;

    @FXML
    private Label githubLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Button okButton;

    @FXML
    private Label versionLabel;
    @FXML
    private Hyperlink githubLink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameLabel.setText(PROGRAM_NAME);
        versionLabel.setText(VERSION);

    }

    @FXML
    void okButton(ActionEvent event) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void openGitHubButton(ActionEvent event) {

        try {
            Desktop.getDesktop().browse(new URL("https://github.com/OskarKamil/FilmList").toURI());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


}
