package main.java.models.figures;

import main.java.models.BoardField;
import main.java.parser.Move;

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
    public boolean move_for_player(BoardField moveTo) {
        if (!check_move(moveTo)) {
            return false;
        }

        boolean kill = false;
        if (!moveTo.isEmpty()) {
            kill(moveTo);
            kill = true;
        }

        Move move = new Move(this.myField.getRow(), this.myField.getCol(),
                moveTo.getRow(), moveTo.getCol(), "J", kill, false, null);
        this.myField.remove();
        moveTo.put(this);
        this.cancel_highlighting();
        this.myField.getBoard().getGamePanelController().overwrite_list_round(move);
        this.myField.getBoard().getGamePanelController().setInitialAnnotation();
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

