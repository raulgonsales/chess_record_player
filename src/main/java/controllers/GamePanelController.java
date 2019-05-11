package main.java.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import main.java.game.Game;
import main.java.game.GameFactory;
import main.java.models.Board;

public class GamePanelController {
    @FXML
    private StackPane game_panel;

    private StartPageController startPageController;

    private Game game;

    GamePanelController(StartPageController startPageController) {
        this.startPageController = startPageController;
    }

    public void initialize() {
        Board board = new Board(8);
        board.setMaxWidth(630);
        board.setMaxHeight(630);
        board.setTranslateY(20);
        this.game_panel.getChildren().addAll(board);
        StackPane.setAlignment(board, Pos.TOP_CENTER);

        this.game = GameFactory.crateChessGame(board);

        System.out.println(this.startPageController.getList_round());
    }
}
