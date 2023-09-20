package javagui;

import def.FilmRecord;
import def.RecordManager;
import def.SettingsManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {
    private RecordManager filmsFile;

    @FXML
    private TableColumn<FilmRecord, Integer> idColumn;
    private ObservableList<FilmRecord> filmsObservableList;
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
    void checkBoxDefaultDate() {
        SettingsManager.changeDefaultDate();
    }

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
        System.out.println("update number of films");
        filmsTotalLabel.setText(String.valueOf(filmsFile.getNumberOfTotalWatchedFilms()));
    }

    @FXML
    void saveChanges() {
        filmsFile.startWriter();
        this.initialize(null, null);
    }

    public void reloadFile() {
        System.out.println("Loading new file");
        this.initialize(null, null);
    }

    @FXML
    void openCSV() {
        reloadFile();
    }


    @FXML
    void addNewRecord() {
        FilmRecord newRecord = new FilmRecord(filmsObservableList.size() + 1);
        filmsObservableList.add(newRecord);
        filmsTable.getSelectionModel().select(newRecord);

        LocalDate today = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedString = today.format(formatter);
        newRecord.setWatchDate(formattedString);
        // filmsTable.getSelectionModel().select(
    }

    @FXML
    void clearAll() {
        System.out.println("cleared all");
        filmsFile.getListOfFilms().clear();
        //   filmsObservableList.add(new FilmRecord());
    }

    @FXML
    void deleteSelectedRecord() {
        FilmRecord selected;
        selected = filmsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            filmsFile.deleteRecordFromList(selected);
            filmsTable.getSelectionModel().select(filmsTable.getSelectionModel().getSelectedIndex() + 1);
        }
        // filmsTable.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filmsFile = new RecordManager();
        filmsFile.startReader();
        filmsObservableList = filmsFile.getListOfFilms();
        titleColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("englishTitle"));
        originalTitleColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("originalTitle"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("type"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("releaseYear"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("rating"));
        watchDateColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("watchDate"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("comments"));
        idColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, Integer>("idInList"));
        filmsTable.setItems(filmsObservableList);

        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        originalTitleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        releaseYearColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ratingColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        watchDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        commentsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        filmsTable.refresh();

        updateNumberOfFilms();
        updateAverageFilmRating();
        updateAverageFilmPerDay();

    }

    private void updateAverageFilmPerDay() {
        averageFilmPerDayLabel.setText(filmsFile.getAverageWatchStatistics());
    }

    private void updateAverageFilmRating() {
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        averageRatingLabel.setText((twoDecimals.format(filmsFile.getAverageFilmRating()) + "/4"));
    }

    @FXML
    void editComments(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setComments(event.getNewValue());

    }


    @FXML
    void editOriginalTitle(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setOriginalTitle(event.getNewValue());
    }

    @FXML
    void editRating(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setRating(event.getNewValue());
    }

    @FXML
    void editReleaseYear(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setReleaseYear(event.getNewValue());
    }

    @FXML
    void editTitle(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setEnglishTitle(event.getNewValue());
    }

    @FXML
    void editType(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setType(event.getNewValue());
    }

    @FXML
    void editWatchDate(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setWatchDate(event.getNewValue());
    }


}
