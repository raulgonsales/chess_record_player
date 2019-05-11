package main.java.models.figures;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import main.java.models.BoardField;
import main.java.models.interfaces.Field;

public class Bishop extends Figure {
    public Bishop(boolean isWhite) {
        super(isWhite);
        this.figureName = "S";
        this.setFigureId();
    }

    @Override
    public boolean move(BoardField moveTo) {
        if (check_field_and_edge(moveTo)) return false;

        if ((Math.abs(this.myField.getCol() - this.myField.getRow()) != Math.abs(moveTo.getCol() - moveTo.getRow())) && (this.myField.getCol() + this.myField.getRow() != Math.abs(moveTo.getCol() + moveTo.getRow()))) {
            return false;
        }

        if (moveTo.getRow() > this.myField.getRow() && moveTo.getCol() > this.myField.getCol()) {
            if (check_way(moveTo, Field.Direction.RU)) return false;
        } else if (moveTo.getRow() < this.myField.getRow() && moveTo.getCol() < this.myField.getCol()) {
            if (check_way(moveTo, Field.Direction.LD)) return false;
        } else if (moveTo.getRow() > this.myField.getRow() && moveTo.getCol() < this.myField.getCol()) {
            if (check_way(moveTo, Field.Direction.LU)) return false;
        } else {
            if (check_way(moveTo, Field.Direction.RD)) return false;
        }


        this.myField.remove();
        moveTo.put(this);
        return true;
    }


}
