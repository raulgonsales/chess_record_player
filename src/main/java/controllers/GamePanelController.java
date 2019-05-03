package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import main.java.game.Game;
import main.java.game.GameFactory;
import main.java.models.Board;

public class GamePanelController {
    @FXML
    public AnchorPane game_panel;

    private Game game;

    public void initialize() {
        Board board = new Board(8);
        this.game_panel.getChildren().addAll(board);
        this.game = GameFactory.crateChessGame(board);
    }
}
