package main.java.game;

import main.java.models.Board;

/**
 * Factory to create games.
 *
 * @author Bohdan Inhliziian (xinhli00)
 * @author Konetzný Jakub (xinhli00)
 */
public class GameFactory {
    public static Chess crateChessGame(Board board) {
        return new Chess(board);
    }
}
