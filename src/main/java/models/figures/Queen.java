package main.java.models.figures;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import main.java.models.BoardField;
import main.java.models.interfaces.Field;

public class Queen extends Figure {
    public Queen(boolean isWhite) {
        super(isWhite);
        this.figureName = "D";
        this.setFigureId();
    }

    @Override
    public boolean move(BoardField moveTo) {
        if (check_field_and_edge(moveTo)) return false;

        if (moveTo.getRow() > this.myField.getRow() && moveTo.getCol() > this.myField.getCol()) {
            if (check_way(moveTo, Field.Direction.RU)) return false;
        } else if (moveTo.getRow() < this.myField.getRow() && moveTo.getCol() < this.myField.getCol()) {
            if (check_way(moveTo, Field.Direction.LD)) return false;
        } else if (moveTo.getRow() > this.myField.getRow() && moveTo.getCol() < this.myField.getCol()) {
            if (check_way(moveTo, Field.Direction.LU)) return false;
        } else if (moveTo.getRow() < this.myField.getRow() && moveTo.getCol() > this.myField.getCol()) {
            if (check_way(moveTo, Field.Direction.RD)) return false;
        } else if (this.myField.getCol() == moveTo.getCol()) {
            if (this.myField.getRow() > moveTo.getRow()) {
                if (check_way(moveTo, Field.Direction.D)) return false;
            } else {
                if (check_way(moveTo, Field.Direction.U)) return false;
            }

        } else if (this.myField.getRow() == moveTo.getRow()) {
            if (this.myField.getCol() > moveTo.getCol()) {
                if (check_way(moveTo, Field.Direction.L)) return false;
            } else {
                if (check_way(moveTo, Field.Direction.R)) return false;
            }

        } else return false;

        this.myField.remove();
        moveTo.put(this);
        return true;
    }
}
