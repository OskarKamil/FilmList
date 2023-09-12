package javagui;

import def.FilmRecord;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {
    @FXML
    private TableView<FilmRecord> filmsTable;
    @FXML
    private TableColumn<FilmRecord, String> titleColumn;
    @FXML
    private TableColumn<FilmRecord, String> originalTitleColumn;
    @FXML
    private TableColumn<FilmRecord, String> typeColumn;
    @FXML
    private TableColumn<FilmRecord, String> releaseYearColumn;
    @FXML
    private TableColumn<FilmRecord, String> ratingColumn;
    @FXML
    private TableColumn<FilmRecord, String> watchDateColumn;
    @FXML
    private TableColumn<FilmRecord, String> commentsColumn;
    @FXML
    private Label averageFilmPerDayLabel;
    @FXML
    private Label averageRatingLabel;
    @FXML
    private Label filmsTotalLabel;

    @FXML
    void openAboutScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/aboutstage.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("About " + AboutSceneController.PROGRAM_NAME);
        URL programIcon = getClass().getClassLoader().getResource("img/icon2.png");
        assert programIcon != null;
        stage.getIcons().add(new Image(programIcon.toString()));
        stage.show();
    }

    public void updateNumberOfFilms() {
        filmsTotalLabel.setText(String.valueOf(HelloFX.getNumberOfTotalWatchedFilms()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HelloFX.loadCSVandSaveToLocalObject();
        ObservableList<FilmRecord> films = HelloFX.getFilms();
        System.out.println("films copy pasted in controller");
        System.out.println(films);
        titleColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("englishTitle"));
        originalTitleColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("originalTitle"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("type"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("releaseYear"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("rating"));
        watchDateColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("watchDate"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("comments"));
        filmsTable.setItems(HelloFX.getFilms());

        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        originalTitleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        releaseYearColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ratingColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        watchDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        commentsColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        updateNumberOfFilms();
        updateAverageFilmRating();
        updateAverageFilmPerDay();

    }

    private void updateAverageFilmPerDay() {
        averageFilmPerDayLabel.setText(HelloFX.getAverageFilmPerDay());
    }

    private void updateAverageFilmRating() {
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        averageRatingLabel.setText((twoDecimals.format(HelloFX.getAverageFilmRating()) + "/4"));
    }

    @FXML
    void editComments(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setComments(event.getNewValue());

    }


    @FXML
    void editOriginalTitle(TableColumn.CellEditEvent<FilmRecord, String> event) {

    }

    @FXML
    void editRating(TableColumn.CellEditEvent<FilmRecord, String> event) {

    }

    @FXML
    void editReleaseYear(TableColumn.CellEditEvent<FilmRecord, String> event) {

    }

    @FXML
    void editTitle(TableColumn.CellEditEvent<FilmRecord, String> event) {

    }

    @FXML
    void editType(TableColumn.CellEditEvent<FilmRecord, String> event) {

    }

    @FXML
    void editWatchDate(TableColumn.CellEditEvent<FilmRecord, String> event) {

    }


}
