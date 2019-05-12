package main.java.models.figures;

import main.java.models.BoardField;

public class Knight extends Figure {
    public Knight(boolean isWhite) {
        super(isWhite);
        this.figureName = "J";
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

        this.myField.remove();
        moveTo.put(this);
        this.cancel_highlighting();
        return true;
    }

    @Override
    public boolean check_move(BoardField moveTo) {
        if (check_field_and_edge(moveTo)) return false;

        int x, y;
        x = Math.abs(moveTo.getRow() - this.myField.getRow());
        y = Math.abs(moveTo.getCol() - this.myField.getCol());


        if ((x != 2 || y != 1) && (x != 1 || y != 2)) {
            return false;
        }

        if (!chceck_color()) {
            return false;
        }
        return true;
    }

}

