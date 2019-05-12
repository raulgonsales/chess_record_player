package main.java.models.figures;

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
        if (!check_move(moveTo)) {
            return false;
        }

        if (!moveTo.isEmpty()) {
            kill(moveTo);
        }

        this.myField.getBoard().setWhites_round(!this.myField.getBoard().getWhites_round());
        this.myField.remove();
        moveTo.put(this);
        this.cancel_highlighting();
        return true;
    }

    @Override
    public boolean check_move(BoardField moveTo) {
        if (check_field_and_edge(moveTo)) return false;

        if ((Math.abs(this.myField.getCol() - this.myField.getRow()) != Math.abs(moveTo.getCol() - moveTo.getRow())) && (this.myField.getCol() + this.myField.getRow() != Math.abs(moveTo.getCol() + moveTo.getRow()))) {
            return false;
        }

        if (moveTo.getRow() > this.myField.getRow() && moveTo.getCol() > this.myField.getCol()) {
            if (check_way(moveTo, Field.Direction.RU)) {
                return false;
            }
        } else if (moveTo.getRow() < this.myField.getRow() && moveTo.getCol() < this.myField.getCol()) {
            if (check_way(moveTo, Field.Direction.LD)) {
                return false;
            }
        } else if (moveTo.getRow() > this.myField.getRow() && moveTo.getCol() < this.myField.getCol()) {
            if (check_way(moveTo, Field.Direction.LU)) {
                return false;
            }
        } else {
            if (check_way(moveTo, Field.Direction.RD)) {
                return false;
            }
        }

        if (!chceck_color()) {
            return false;
        }
        return true;
    }

}
