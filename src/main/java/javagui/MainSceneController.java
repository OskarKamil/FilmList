package javagui;

import def.*;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {
    public static ButtonManager buttonManager;
    private ProgramStateManager programStateManager;
    @FXML
    private Button addNewRecordButton;
    @FXML
    private Button cleanAllButton;
    @FXML
    private Button deleteOneRecordButton;
    @FXML
    private Button newFileButton1;
    @FXML
    private Button openFileButton;
    @FXML
    private Button revertChangesButton;
    @FXML
    private Button saveAsButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button aboutButton;
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
        ProgramStateManager.setAnyChange(true);
        ProgramStateManager.setUnsavedChange(true);
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
        ProgramStateManager.setAnyChange(true);
        ProgramStateManager.setUnsavedChange(true);

        //   filmsObservableList.add(new FilmRecord());
    }

    @FXML
    void deleteSelectedRecord() {
        FilmRecord selected;
        selected = filmsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            filmsFile.deleteRecordFromList(selected);
            if (filmsTable.getSelectionModel().getSelectedIndex() != 0)
                filmsTable.getSelectionModel().select(filmsTable.getSelectionModel().getSelectedIndex() + 1);
            ProgramStateManager.setAnyChange(true);
            ProgramStateManager.setUnsavedChange(true);
        }
        filmsTable.refresh();
    }

    @FXML
    void editComments(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setComments(event.getNewValue());
        ProgramStateManager.setAnyChange(true);
    }

    @FXML
    void editOriginalTitle(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setOriginalTitle(event.getNewValue());
        ProgramStateManager.setAnyChange(true);

    }

    @FXML
    void editRating(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setRating(event.getNewValue());
        ProgramStateManager.setAnyChange(true);

    }

    @FXML
    void editReleaseYear(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setReleaseYear(event.getNewValue());
        ProgramStateManager.setAnyChange(true);

    }

    @FXML
    void editTitle(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setEnglishTitle(event.getNewValue());
        ProgramStateManager.setAnyChange(true);

    }

    @FXML
    void editType(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setType(event.getNewValue());
        ProgramStateManager.setAnyChange(true);

    }

    @FXML
    void editWatchDate(TableColumn.CellEditEvent<FilmRecord, String> event) {
        event.getTableView().getItems().get(event.getTablePosition().getRow()).setWatchDate(event.getNewValue());
        ProgramStateManager.setAnyChange(true);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //PROGRAM STATE MANAGER
        programStateManager = new ProgramStateManager(this);

        //BUTTON MANAGER
        ArrayList<Button> alwaysActiveButtons = new ArrayList<>();
        alwaysActiveButtons.add(newFileButton1);
        alwaysActiveButtons.add(openFileButton);
        alwaysActiveButtons.add(aboutButton);
        alwaysActiveButtons.add(saveAsButton);

        ArrayList<Button> unsavedChangeButtons = new ArrayList<>();
        unsavedChangeButtons.add(saveButton);

        ArrayList<Button> openedFileButtons = new ArrayList<>();
        openedFileButtons.add(addNewRecordButton);
        openedFileButtons.add(cleanAllButton);

        ArrayList<Button> selectedCellsButtons = new ArrayList<>();
        selectedCellsButtons.add(deleteOneRecordButton);

        ArrayList<Button> anyChangeButtons = new ArrayList<>();
        anyChangeButtons.add(revertChangesButton);

        buttonManager = new ButtonManager();
        buttonManager.setAlwaysActiveButtons(alwaysActiveButtons);
        buttonManager.setUnsavedChangeButtons(unsavedChangeButtons);
        buttonManager.setOpenedFileButtons(openedFileButtons);
        buttonManager.setSelectedCellsButtons(selectedCellsButtons);
        buttonManager.setAnyChangeButtons(anyChangeButtons);
        //   buttonManager.testButtons(true); // TESTING

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

        //TABLEVIEW SELECTED LISTENER
        ProgramStateManager.setSelectedCells(false);
        filmsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            // A cell is selected
            // No cell is selected
            ProgramStateManager.setSelectedCells(newSelection != null);
        });

        //  programStateManager.setAnyChange(false);
        //programStateManager.setUnsavedChange(false);

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
            ProgramStateManager.setAnyChange(false);
            ProgramStateManager.setUnsavedChange(false);
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
            ProgramStateManager.setUnsavedChange(false);
            ProgramStateManager.setAnyChange(false);
            updateStatistics();
            //updateStageTitle();
        }

    }

    @FXML
    void openFileChooser() {
        File file;
        FileChooser fileChooser = new FileChooser();
        if ("New File".equals(filePath)) {
            System.out.println("new file");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        } //TESTING
        else
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
        ProgramStateManager.setAnyChange(false);
    }

    public void revertChanges(ActionEvent event) {
        openFile(filePath);
    }

    public boolean saveAsChanges() {
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
            return true;
        }
        return false;
    }

    @FXML
    boolean saveChanges() {
        String filePath = filmsFile.getFilePath();
        boolean saved = false;
        if (filePath == null || filePath.isEmpty() || filePath.equals("New File")) {
            saved = saveAsChanges();
            return saved;
        }
        filmsFile.startWriter(filePath);
        ProgramStateManager.setUnsavedChange(false);

        return saved;
    }

    public void setStageAndTitle(Stage window) {
        stage = window;
        // updateStageTitle();
    }

    public boolean shutDown() {
        System.out.println("stop");
        SettingsManager.saveSettingsFile();
        if (SettingsManager.getAutoSave() && ProgramStateManager.isUnsavedChange()) saveChanges();
        else {
            if (ProgramStateManager.isUnsavedChange()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Save changes");
                alert.setHeaderText("Save changes in this file?");

                URL programIcon = getClass().getClassLoader().getResource("img/icon2.png");
                assert programIcon != null;
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(programIcon.toString()));

                ButtonType buttonTypeOne = new ButtonType("Yes");
                ButtonType buttonTypeTwo = new ButtonType("No");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == buttonTypeOne) { // YES
                        boolean saved;
                        saved = saveChanges();
                        return saved;
                    } else if (result.get() == buttonTypeTwo) { // NO
                        return true;
                    } else { // CANCEL
                        System.out.println("option cancel");
                        return false;
                    }
                }
            }
        }
        return !ProgramStateManager.isUnsavedChange();
    }

    private void updateAverageFilmPerDay() {
        averageFilmPerDayLabel.setText(filmsFile.getAverageWatchStatistics());
    }

    private void updateAverageFilmRating() {
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        if (filmsFile.getListOfFilms().isEmpty())
            averageRatingLabel.setText("No data");
        else
            averageRatingLabel.setText((twoDecimals.format(filmsFile.getAverageFilmRating()) + "/4"));
    }

    public void updateNumberOfFilms() {
        filmsTotalLabel.setText(String.valueOf(filmsFile.getNumberOfTotalWatchedFilms()));
    }

    public void updateStageTitle() {
        String stageTitle = "";
        if (ProgramStateManager.isUnsavedChange()) {
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
