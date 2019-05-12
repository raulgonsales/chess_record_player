package main.java.models.figures;

import main.java.models.BoardField;
import main.java.models.interfaces.Field;
import main.java.parser.Move;

public class King extends Figure {
    public King(boolean isWhite) {
        super(isWhite);
        this.figureName = "K";
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
                moveTo.getRow(), moveTo.getCol(), "K", kill, false, null);
        this.myField.getBoard().setWhites_round(!this.myField.getBoard().getWhites_round());
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

        if (Math.abs(moveTo.getCol() - this.myField.getCol()) > 1) {
            return false;
        }

        if (Math.abs(moveTo.getRow() - this.myField.getRow()) > 1) {
            return false;
        }

        if (!chceck_color()) {
            return false;
        }
        return true;
    }
}
