package main.java.game;

import main.java.models.Board;

public class Chess implements Game {
    private Board board;

    Chess(Board board) {
        this.board = board;
    }
}
