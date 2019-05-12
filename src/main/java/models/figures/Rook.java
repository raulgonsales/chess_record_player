package main.java.models.figures;

import main.java.models.BoardField;
import main.java.models.interfaces.Field;
import main.java.parser.Move;

import java.io.IOException;

public class Rook extends Figure {
    public Rook(boolean isWhite) {
        super(isWhite);
        this.figureName = "V";
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
    public boolean move_for_player(BoardField moveTo){
        if (!check_move(moveTo)) {
            return false;
        }

        boolean kill = false;
        if (!moveTo.isEmpty()) {
            kill(moveTo);
            kill = true;
        }

        Move move = new Move(this.myField.getRow(), this.myField.getCol(),
                moveTo.getRow(), moveTo.getCol(), "V", kill, false, null);
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

        if (this.myField.getCol() == moveTo.getCol()) {
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


        if (!chceck_color()) {
            return false;
        }
        return true;
    }
}
