package main.java.models.figures;

import main.java.models.BoardField;
import main.java.models.interfaces.Field;
import main.java.parser.Move;

public class Pawn extends Figure {
    public Pawn(boolean isWhite) {
        super(isWhite);
        this.figureName = "P";
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
                moveTo.getRow(), moveTo.getCol(), null, kill, false, null);
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

        int distance = 2;
        if ((this.isWhite && this.myField.getRow() == 2) || ((!this.isWhite && this.myField.getRow() == 7))) {
            distance = 3;
        }

        if (moveTo.isEmpty()) {
            if (this.isWhite && moveTo.getRow() < this.myField.getRow() ||
                    !this.isWhite && moveTo.getRow() > this.myField.getRow() ||
                    Math.abs(moveTo.getRow() - this.myField.getRow()) >= distance || moveTo.getCol() != this.myField.getCol()) {
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

        if (!chceck_color()) {
            return false;
        }
        return true;
    }
}
