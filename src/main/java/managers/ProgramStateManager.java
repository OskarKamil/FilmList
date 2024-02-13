package managers;


import controllers.MainSceneController;

import static controllers.MainSceneController.buttonManager;

public class ProgramStateManager {
    private static MainSceneController mainSceneController;
    private static boolean unsavedChange;
    private static boolean anyChange;
    private static boolean selectedCells;
    private static boolean openedFile;

    public ProgramStateManager(MainSceneController mainSceneController) {
        ProgramStateManager.mainSceneController = mainSceneController;
        unsavedChange = false;
        anyChange = false;
    }

    public static boolean isAnyChange() {
        return anyChange;
    }

    public static void setAnyChange(boolean anyChange) {
        if (!anyChange)
            buttonManager.disableButtons(buttonManager.getAnyChangeButtons());
        else {
            buttonManager.enableButtons(buttonManager.getAnyChangeButtons());
            unsavedChange = true;
        }

        ProgramStateManager.anyChange = anyChange;
    }

    public static boolean isOpenedFile() {
        return openedFile;
    }

    public static void setOpenedFile(boolean openedFile) {
        ProgramStateManager.openedFile = openedFile;
        mainSceneController.updateStageTitle();

        if (openedFile)
            buttonManager.enableButtons(buttonManager.getOpenedFileButtons());
        else
            buttonManager.disableButtons(buttonManager.getOpenedFileButtons());


    }

    public static boolean isSelectedCells() {
        return selectedCells;
    }

    public static void setSelectedCells(boolean selectedCells) {
        ProgramStateManager.selectedCells = selectedCells;
        if (selectedCells)
            buttonManager.enableButtons(buttonManager.getSelectedCellsButtons());
        else
            buttonManager.disableButtons(buttonManager.getSelectedCellsButtons());
    }

    public static boolean isUnsavedChange() {
        if (unsavedChange)
            buttonManager.enableButtons(buttonManager.getUnsavedChangeButtons());
        else
            buttonManager.disableButtons(buttonManager.getUnsavedChangeButtons());

        return unsavedChange;
    }

    public static void setUnsavedChange(boolean unsavedChange) {
        ProgramStateManager.unsavedChange = unsavedChange;
        mainSceneController.updateStageTitle();
    }

}

