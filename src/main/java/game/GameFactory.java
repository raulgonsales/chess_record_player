package main.java.game;

import main.java.models.Board;

public class GameFactory {
    public static Chess crateChessGame(Board board) {
        return new Chess(board);
    }
}
