package main.java.models.figures;

import main.java.models.BoardField;

public class Pawn extends Figure {
    public Pawn(boolean isWhite) {
        super(isWhite);
        this.figureName = "P";
        this.setFigureId();
    }

    @Override
    public boolean move(BoardField moveTo) {
        if (check_field_and_edge(moveTo) || this.myField.getCol() != moveTo.getCol()) return false;

        if (this.isWhite && moveTo.getRow() < this.myField.getRow() ||
                !this.isWhite && moveTo.getRow() > this.myField.getRow() ||
                Math.abs(moveTo.getRow() - this.myField.getRow()) >= 2) {
            return false;
        }

        this.myField.remove();
        moveTo.put(this);
        return true;
    }
}
