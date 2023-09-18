package javagui;

import def.FilmRecord;
import def.RecordManager;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {
    private RecordManager filmsFile;
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

    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
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
        filmsObservableList.add(new FilmRecord());
        filmsTable.getSelectionModel().select(filmsObservableList.size() - 1);
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
        filmsObservableList.remove(selected);
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
        averageFilmPerDayLabel.setText(filmsFile.getAverageFilmPerDay());
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
