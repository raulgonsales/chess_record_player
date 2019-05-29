package main.java.parser;

/**
 * Round of the movements.
 *
 * @author Bohdan Inhliziian (xinhli00)
 * @author Konetzný Jakub (xinhli00)
 */
public class Round {

    private Move white;
    private Move black;

    /**
     * constructor without moves
     */
    Round() {
        this.black = null;
        this.white = null;
    }

    /**
     * constructor with boht moves
     *
     * @param white move of white player
     * @param black move of black player
     */
    public Round(Move white, Move black) {
        this.black = black;
        this.white = white;
    }

    /**
     * @return move of white player
     */
    public Move getWhite() {
        return white;
    }

    /**
     * @return move of black player
     */
    public Move getBlack() {
        return black;
    }
}
