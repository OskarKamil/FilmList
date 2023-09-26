package javagui;

import def.FilmRecord;
import def.RecordManager;
import def.SettingsManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {
    private boolean changeInFile = false;
    private String filePath;
    private RecordManager filmsFile;
    @FXML
    private TableColumn<FilmRecord, Integer> idColumn;
    @FXML
    private CheckBox autoSaveBox;
    @FXML
    private CheckBox defaultDateBox;
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
    private Stage stage;

    @FXML
    void addNewRecord() {
        FilmRecord newRecord = new FilmRecord(filmsObservableList.size() + 1);
        filmsObservableList.add(newRecord);
        filmsTable.getSelectionModel().select(newRecord);

        if (SettingsManager.getDefaultDateIsToday()) {
            LocalDate today = LocalDate.now();//For reference
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedString = today.format(formatter);
            newRecord.setWatchDate(formattedString);
        }
        setChangeInFile(true);

    }

    @FXML
    void checkBoxAutoSave(ActionEvent event) {
        CheckBox checkBox = (CheckBox) event.getSource();
        SettingsManager.changeAutoSave();
        checkBox.setSelected(SettingsManager.getAutoSave());
    }

    @FXML
    void checkBoxDefaultDate(ActionEvent event) {
        CheckBox checkBox = (CheckBox) event.getSource();
        SettingsManager.changeDefaultDate();
        checkBox.setSelected(SettingsManager.getDefaultDateIsToday());
    }

    @FXML
    void clearAll() {
        System.out.println("cleared all");
        filmsFile.getListOfFilms().clear();
        setChangeInFile(true);

        //   filmsObservableList.add(new FilmRecord());
    }

    @FXML
    void deleteSelectedRecord() {
        FilmRecord selected;
        selected = filmsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            filmsFile.deleteRecordFromList(selected);
            setChangeInFile(true);
            if (filmsTable.getSelectionModel().getSelectedIndex() != 0)
                filmsTable.getSelectionModel().select(filmsTable.getSelectionModel().getSelectedIndex() + 1);
        }
        filmsTable.refresh();
    }

    @FXML
    void editComments(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setComments(event.getNewValue());
        setChangeInFile(true);
    }

    @FXML
    void editOriginalTitle(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setOriginalTitle(event.getNewValue());
        setChangeInFile(true);

    }

    @FXML
    void editRating(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setRating(event.getNewValue());
        setChangeInFile(true);

    }

    @FXML
    void editReleaseYear(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setReleaseYear(event.getNewValue());
        setChangeInFile(true);

    }

    @FXML
    void editTitle(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setEnglishTitle(event.getNewValue());
        setChangeInFile(true);

    }

    @FXML
    void editType(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setType(event.getNewValue());
        setChangeInFile(true);

    }

    @FXML
    void editWatchDate(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setWatchDate(event.getNewValue());
        setChangeInFile(true);

    }

    public boolean getChangeInFile() {
        return changeInFile;
    }

    private void setChangeInFile(boolean changed) {
        changeInFile = changed;
        updateStageTitle();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //SETTINGS
        SettingsManager.readSettingsFile();
        autoSaveBox.setSelected(SettingsManager.getAutoSave());
        defaultDateBox.setSelected(SettingsManager.getDefaultDateIsToday());

        //TABLEVIEW DISPLAY VALUES
        titleColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("englishTitle"));
        originalTitleColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("originalTitle"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("type"));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("releaseYear"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("rating"));
        watchDateColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("watchDate"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, String>("comments"));
        idColumn.setCellValueFactory(new PropertyValueFactory<FilmRecord, Integer>("idInList"));
        openFile(SettingsManager.getLastPath());
//        filmsFile = new RecordManager();
//        filmsFile.startReader(SettingsManager.getLastPath());
//        filePath = SettingsManager.getLastPath();
//        filmsObservableList = filmsFile.getListOfFilms();
//        filmsTable.setItems(filmsObservableList);

        //TABLEVIEW EDIT OPTIONS
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        originalTitleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        releaseYearColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ratingColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        watchDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        commentsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        filmsTable.refresh();

        //STATISTICS
        updateStatistics();
    }

    public void newFile() {
        if (shutDown()) {
            filmsFile = new RecordManager();
            filmsFile.startReader("New File");
            filePath = SettingsManager.getLastPath();
            filmsObservableList = filmsFile.getListOfFilms();
            filmsTable.setItems(filmsObservableList);
            filePath = "New File";
            setChangeInFile(false);
            System.out.println("new file created and names: " + filePath);
            SettingsManager.setLastPath(filePath);
            updateStatistics();
            updateStageTitle();
        }
    }

    @FXML
    void openAboutScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/aboutStage.fxml"));
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

    void openFile(String newFilePath) {
        System.out.println("trying to open new file");
        if (Objects.equals(newFilePath, "")) {
            System.out.println("trying to create new file");
            newFile();
        } else {
            filePath = newFilePath;
            filmsFile = new RecordManager();
            filmsFile.startReader(filePath);
            filmsObservableList = filmsFile.getListOfFilms();
            filmsTable.setItems(filmsObservableList);
            SettingsManager.setLastPath(filePath);
            updateStatistics();
            updateStageTitle();
        }

    }

    @FXML
    void openFileChooser() {
        File file;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(filePath).getParentFile());
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt), (*.csv)", "*.txt", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Open file");
        file = fileChooser.showOpenDialog(stage);
        if (file != null) openFile(file.getPath());
    }

    public void reloadFile() {
        System.out.println("Loading new file");
        this.initialize(null, null);
        setChangeInFile(false);
    }

    public void revertChanges(ActionEvent event) {
        openFile(filePath);
    }

    public void saveAsChanges() {
        File file;
        FileChooser fileChooser = new FileChooser();
        if (filmsFile.getFilePath() != null || !Objects.equals(filmsFile.getFilePath(), ""))
            fileChooser.setInitialDirectory(new File(filePath).getParentFile());
        fileChooser.setTitle("Save file as");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt), (*.csv)", "*.txt", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            filmsFile.startWriter(file.getPath());
            openFile(file.getPath());
        }
    }

    @FXML
    void saveChanges() {
        String filePath = filmsFile.getFilePath();
        if (filePath == null || filePath.isEmpty() || filePath.equals("New File")) {
            saveAsChanges();
            return;
        }
        filmsFile.startWriter(filePath);
        setChangeInFile(false);
    }

    public void setStageAndTitle(Stage window) {
        stage = window;
        // updateStageTitle();
    }

    public boolean shutDown() {
        System.out.println("stop");
        SettingsManager.saveSettingsFile();
        if (SettingsManager.getAutoSave()) saveChanges();
        else {
            if (getChangeInFile()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Save changes");
                alert.setHeaderText("Save changes in this file?");
                // alert.setContentText("Save changes in this file?");

                ButtonType buttonTypeOne = new ButtonType("Yes");
                ButtonType buttonTypeTwo = new ButtonType("No");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

                Optional<ButtonType> result = alert.showAndWait();
                // alert.showAndWait();
                if (result.get() == buttonTypeOne) { // YES
                    saveChanges();
                    return true;
                } else if (result.get() == buttonTypeTwo) { // NO
                    return true;
                } else { // CANCEL
                    System.out.println("option cancel");
                    return false;
                }
            }

        }
        return true;
    }

    private void updateAverageFilmPerDay() {
        averageFilmPerDayLabel.setText(filmsFile.getAverageWatchStatistics());
    }

    private void updateAverageFilmRating() {
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        averageRatingLabel.setText((twoDecimals.format(filmsFile.getAverageFilmRating()) + "/4"));
    }

    public void updateNumberOfFilms() {
        filmsTotalLabel.setText(String.valueOf(filmsFile.getNumberOfTotalWatchedFilms()));
    }

    private void updateStageTitle() {
        String stageTitle = "";
        if (getChangeInFile()) {
            stageTitle = "*";
        }
        System.out.println("updating stage title to:" + filePath);
        stageTitle += filePath + " - " + AboutSceneController.PROGRAM_NAME;

        HelloFX.setStageTitle(stageTitle);
        // stage.setTitle(stageTitle);
    }

    private void updateStatistics() {
        updateNumberOfFilms();
        updateAverageFilmRating();
        updateAverageFilmPerDay();
    }

}
