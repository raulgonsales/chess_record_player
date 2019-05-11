package main.java.models.figures;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import main.java.models.BoardField;
import main.java.models.interfaces.Field;

public class King extends Figure {
    public King(boolean isWhite) {
        super(isWhite);
        this.figureName = "K";
        this.setFigureId();
    }

    @Override
    public boolean move(BoardField moveTo) {
        if (check_field_and_edge(moveTo)) return false;

        if (Math.abs(moveTo.getCol() - this.myField.getCol()) > 1) {
            return false;
        }

        if (Math.abs(moveTo.getRow() - this.myField.getRow()) > 1) {
            return false;
        }


        this.myField.remove();
        moveTo.put(this);
        return true;
    }
}
