package main.java.parser;

public class Round {

    private Move white;
    private Move black;

    Round() {
        this.black = null;
        this.white = null;
    }

    Round(Move white, Move black) {
        this.black = black;
        this.white = white;
    }

    public Move getWhite() {
        return white;
    }

    public Move getBlack() {
        return black;
    }
}
