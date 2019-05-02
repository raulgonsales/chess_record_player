package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;

public class StartPageController {

    @FXML
    public Button end_game;

    @FXML
    public Button replay_new_game;

    private Tab thisTab;

    private MainController mainController;

    StartPageController(MainController mainController, Tab thisTab) {
        this.mainController = mainController;
        this.thisTab = thisTab;
    }

    public void endGame(ActionEvent event) {
        this.mainController.closeTab(this.thisTab);
    }
}
