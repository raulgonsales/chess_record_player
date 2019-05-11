package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import main.java.parser.Parser;
import main.java.parser.Round;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import javax.swing.plaf.FileChooserUI;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StartPageController {

    @FXML
    public Button end_game;

    @FXML
    public Button replay_new_game;

    private Tab thisTab;

    private ArrayList<Round> list_round;

    private MainController mainController;


    StartPageController(MainController mainController, Tab thisTab) {
        this.mainController = mainController;
        this.thisTab = thisTab;
    }

    public void endGame(ActionEvent event) {
        this.mainController.closeTab(this.thisTab);
    }

    public void startGame() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);

        Parser parser = new Parser(file.getPath());
        this.list_round = parser.convert();

        if (this.list_round != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/views/game_panel.fxml"));
            fxmlLoader.setController(new GamePanelController(this));
            this.thisTab.setContent(fxmlLoader.load());
        } else {
            error(parser.getErr_code());
        }
    }

    private void error(int err) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input file ERROR");
        alert.setHeaderText(null);
        if (err == 1) {
            alert.setContentText("File does not exist or you don't have permissions to read file");
        } else if (err == 3) {
            alert.setContentText("Syntax error of file");
        } else {
            alert.setContentText("Unknown ERROR");
        }

        alert.showAndWait();
    }

    public ArrayList<Round> getList_round() {
        return list_round;
    }
}
