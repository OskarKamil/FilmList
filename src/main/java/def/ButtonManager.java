package def;

import javafx.scene.control.Button;

import java.util.ArrayList;


public class ButtonManager {
    private ArrayList<Button> alwaysActiveButtons = new ArrayList<>();
    private ArrayList<Button> openedFileButtons = new ArrayList<>();
    private ArrayList<Button> selectedCellsButtons = new ArrayList<>();
    private ArrayList<Button> unsavedChangeButtons = new ArrayList<>();
    private ArrayList<Button> anyChangeButtons = new ArrayList<>();

    public ButtonManager() {
    }

    public void disableButton(Button button) {
        button.setDisable(true);
    }

    public void disableButtons(ArrayList<Button> buttons) {
        for (Button button : buttons) {
            button.setDisable(true);
        }
    }

    public void enableButton(Button button) {
        button.setDisable(false);
    }

    public void enableButtons(ArrayList<Button> buttons) {
        for (Button button : buttons) {
            button.setDisable(false);
        }
    }

    public ArrayList<Button> getAlwaysActiveButtons() {
        return alwaysActiveButtons;
    }

    public void setAlwaysActiveButtons(ArrayList<Button> alwaysActiveButtons) {
        this.alwaysActiveButtons = alwaysActiveButtons;
    }

    public ArrayList<Button> getAnyChangeButtons() {
        return anyChangeButtons;
    }

    public void setAnyChangeButtons(ArrayList<Button> anyChangeButtons) {
        this.anyChangeButtons = anyChangeButtons;
    }

    public ArrayList<Button> getOpenedFileButtons() {
        return openedFileButtons;
    }

    public void setOpenedFileButtons(ArrayList<Button> openedFileButtons) {
        this.openedFileButtons = openedFileButtons;
    }

    public ArrayList<Button> getSelectedCellsButtons() {
        return selectedCellsButtons;
    }

    public void setSelectedCellsButtons(ArrayList<Button> selectedCellsButtons) {
        this.selectedCellsButtons = selectedCellsButtons;
    }

    public ArrayList<Button> getUnsavedChangeButtons() {
        return unsavedChangeButtons;
    }

    public void setUnsavedChangeButtons(ArrayList<Button> unsavedChangeButtons) {
        this.unsavedChangeButtons = unsavedChangeButtons;
    }

    public void testButtons(boolean b) {
        ArrayList<Button> allButtons = new ArrayList<>();
        allButtons.addAll(alwaysActiveButtons);
        allButtons.addAll(openedFileButtons);
        allButtons.addAll(selectedCellsButtons);
        allButtons.addAll(unsavedChangeButtons);
        allButtons.addAll(anyChangeButtons);
        for (Button button : allButtons) {
            button.setText("Good");
        }
    }
}
