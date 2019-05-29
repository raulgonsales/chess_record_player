package main.java.game;

import main.java.models.Board;
import main.java.models.figures.*;

/**
 * Creates game instance and puts initial figures to board.
 *
 * @author Bohdan Inhliziian (xinhli00)
 * @author Konetzný Jakub (xinhli00)
 */
public class Chess {
    private Board board;

    Chess(Board board) {
        this.board = board;
        initFigures();
    }

    public Board getBoard() {
        return board;
    }

    private void initFigures() {
        for (int i = 1; i < board.getBoardSize() - 1; i++) {
            this.board.getField(i, 2).put(new Pawn(true));
            this.board.getField(i, 7).put(new Pawn(false));

            switch (i) {
                case 1:
                case 8:
                    this.board.getField(i, 1).put(new Rook(true));
                    this.board.getField(i, 8).put(new Rook(false));
                    break;
                case 2:
                case 7:
                    this.board.getField(i, 1).put(new Knight(true));
                    this.board.getField(i, 8).put(new Knight(false));
                    break;
                case 3:
                case 6:
                    this.board.getField(i, 1).put(new Bishop(true));
                    this.board.getField(i, 8).put(new Bishop(false));
                    break;
                case 4:
                    this.board.getField(i, 1).put(new Queen(true));
                    this.board.getField(i, 8).put(new Queen(false));
                    break;
                case 5:
                    this.board.getField(i, 1).put(new King(true));
                    this.board.getField(i, 8).put(new King(false));
                    break;
            }
        }
    }
}
