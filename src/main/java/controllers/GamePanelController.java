package main.java.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import main.java.game.Game;
import main.java.game.GameFactory;
import main.java.models.Board;
import main.java.controllers.StartPageController;
import main.java.parser.Round;

import java.util.ArrayList;


public class GamePanelController {

    private ArrayList<Round> list_round;

    private StartPageController startPageController;

    GamePanelController(StartPageController startPageController) {
        this.startPageController = startPageController;
    }

    @FXML
    public StackPane game_panel;

    private Game game;

    public void initialize() {
        Board board = new Board(8);
        board.setMaxWidth(630);
        board.setMaxHeight(630);
        board.setTranslateY(20);
        this.game_panel.getChildren().addAll(board);
        StackPane.setAlignment(board, Pos.TOP_CENTER);

        this.game = GameFactory.crateChessGame(board);
        this.list_round = this.startPageController.getList_round();


    }

}
