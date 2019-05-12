package main.java.models.figures;

import main.java.models.BoardField;
import main.java.models.interfaces.Field;

public class Pawn extends Figure {
    public Pawn(boolean isWhite) {
        super(isWhite);
        this.figureName = "P";
        this.setFigureId();
    }

    @Override
    public boolean move(BoardField moveTo) {
        if (check_field_and_edge(moveTo)) return false;

        int distance = 2;
        if ((this.isWhite && this.myField.getRow() == 2) || ((!this.isWhite && this.myField.getRow() == 7))) {
            distance = 3;
        }

        if (moveTo.isEmpty()) {
            if (this.isWhite && moveTo.getRow() < this.myField.getRow() ||
                    !this.isWhite && moveTo.getRow() > this.myField.getRow() ||
                    Math.abs(moveTo.getRow() - this.myField.getRow()) >= distance) {
                return false;
            }
        } else {
            if (this.isWhite()) {
                if ((!this.myField.nextField(Field.Direction.LU).equals(moveTo)) &&
                        (!this.myField.nextField(Field.Direction.RU).equals(moveTo))) {
                    return false;
                }
            } else {
                if ((!this.myField.nextField(Field.Direction.LD).equals(moveTo)) &&
                        (!this.myField.nextField(Field.Direction.RD).equals(moveTo))) {
                    return false;
                }
            }
        }

        if(!moveTo.isEmpty()){
           kill(moveTo);
        }

        this.myField.remove();
        moveTo.put(this);
        this.cancel_highlighting();
        return true;
    }
}
