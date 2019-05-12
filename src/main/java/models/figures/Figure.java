package main.java.models.figures;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import main.java.models.Board;
import main.java.models.BoardField;
import main.java.models.BoardFieldEdge;
import main.java.models.interfaces.Field;

public class Figure extends Pane implements main.java.models.interfaces.Figure {
    protected boolean isWhite;
    protected BoardField myField;
    protected String figureName = "";

    public Figure(boolean isWhite) {
        this.isWhite = isWhite;
        setMaxWidth(55);
        setMaxHeight(55);

        getStylesheets().addAll(this.getClass().getResource("../../../resources/style/figures.css").toExternalForm());

        this.setOnMouseClicked(event -> {
            cancel_highlighting();
            this.myField.positiveHighlightField();

            if (this instanceof Pawn) {
                this.makePawnWayHighlighting();
            }

            if (this instanceof King) {
                this.makeKingWayHighlighting();
            }

            if (this instanceof Bishop) {
                this.makeBishopWayHighlighting();
            }

            if (this instanceof Knight) {
                this.makeKnightWayHighlighting();
            }

            if (this instanceof Queen) {
                this.makeQueenWayHighlighting();
            }


            if (this instanceof Rook) {
                this.makeRookWayHighlighting();
            }
        });

        setOnDragDetected(event -> {
            /* allow any transfer mode */
            Dragboard db = startDragAndDrop(TransferMode.MOVE);

            /* put a string on dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString("" + myField.getCol() + myField.getRow());
            db.setContent(content);

            event.consume();
        });
    }

    protected void cancel_highlighting() {
        Board board = this.myField.getBoard();
        for (int i = 1; i < board.getBoardSize() - 1; i++) {
            for (int j = 1; j < board.getBoardSize() - 1; j++) {
                board.getFields()[i][j].setBorder(Border.EMPTY);
            }
        }
    }

    private void makePawnWayHighlighting() {

        Field tmp = this.myField;
        if ((this.isWhite && this.myField.getRow() == 2) || ((!this.isWhite && this.myField.getRow() == 7))) {
            if (this.isWhite() && this.myField.nextField(Field.Direction.U).nextField(Field.Direction.U).isEmpty() &&
                    this.myField.nextField(Field.Direction.U).isEmpty()) {
                this.myField.nextField(Field.Direction.U).nextField(Field.Direction.U).positiveHighlightField();
            } else if (!this.isWhite()) {
                if (this.myField.nextField(Field.Direction.D).nextField(Field.Direction.D).isEmpty() &&
                        this.myField.nextField(Field.Direction.D).isEmpty())
                    this.myField.nextField(Field.Direction.D).nextField(Field.Direction.D).positiveHighlightField();
            }

        }
        if (this.isWhite()) {
            if (this.myField.nextField(Field.Direction.U).isEmpty()) {
                this.myField.nextField(Field.Direction.U).positiveHighlightField();
            }
            if (!this.myField.nextField(Field.Direction.LU).isEmpty()) {
                this.myField.nextField(Field.Direction.LU).positiveHighlightField();
            }
            if (!this.myField.nextField(Field.Direction.RU).isEmpty()) {
                this.myField.nextField(Field.Direction.RU).positiveHighlightField();
            }
        } else if (!this.isWhite()) {
            if (this.myField.nextField(Field.Direction.D).isEmpty()) {
                this.myField.nextField(Field.Direction.D).positiveHighlightField();
            }
            if (!this.myField.nextField(Field.Direction.LD).isEmpty()) {
                this.myField.nextField(Field.Direction.LD).positiveHighlightField();
            }
            if (!this.myField.nextField(Field.Direction.RD).isEmpty()) {
                this.myField.nextField(Field.Direction.RD).positiveHighlightField();
            }
        }
    }

    private void makeKingWayHighlighting() {

        for (int j = 0; j < 8; j++) {
            Field tmp = this.myField.nextField(Field.Direction.values()[j]);

            if ((tmp.isEmpty() || tmp.get().isWhite() != this.isWhite) && (!(check_field_and_edge((BoardField) tmp)))) {
                tmp.positiveHighlightField();
            }

        }


    }

    private void makeBishopWayHighlighting() {
        Field tmp = this.myField;
        bishop_dir_highlight(tmp, Field.Direction.RU);
        bishop_dir_highlight(tmp, Field.Direction.RD);
        bishop_dir_highlight(tmp, Field.Direction.LU);
        bishop_dir_highlight(tmp, Field.Direction.LD);

    }

    private void bishop_dir_highlight(Field tmp, Field.Direction ru) {
        while (!(tmp instanceof BoardFieldEdge)) {
            if (!tmp.nextField(ru).isEmpty()) {
                if (tmp.nextField(ru).get().isWhite() != this.isWhite()) {
                    tmp = tmp.nextField(ru);
                    tmp.positiveHighlightField();
                    return;
                }
                break;
            }
            tmp = tmp.nextField(ru);
            if ((tmp.isEmpty() || tmp.get().isWhite() != this.isWhite) && (!(check_field_and_edge((BoardField) tmp)))) {
                tmp.positiveHighlightField();
            }
        }
    }

    private void makeKnightWayHighlighting() {
        knight_dir_highligth(Field.Direction.D, Field.Direction.L);
        knight_dir_highligth(Field.Direction.D, Field.Direction.R);
        knight_dir_highligth(Field.Direction.U, Field.Direction.L);
        knight_dir_highligth(Field.Direction.U, Field.Direction.R);
        knight_dir_highligth(Field.Direction.L, Field.Direction.D);
        knight_dir_highligth(Field.Direction.L, Field.Direction.U);
        knight_dir_highligth(Field.Direction.R, Field.Direction.D);
        knight_dir_highligth(Field.Direction.R, Field.Direction.U);
    }

    private void knight_dir_highligth(Field.Direction f, Field.Direction s) {
        Field tmp;
        tmp = this.myField;
        tmp = tmp.nextField(f);
        if (knight_highligth(f, s, tmp, tmp.nextField(f), tmp.nextField(s))) return;

        tmp = this.myField;
        tmp = tmp.nextField(s);
        if (knight_highligth(f, f, tmp, tmp.nextField(s), tmp.nextField(s))) return;
    }

    private boolean knight_highligth(Field.Direction f, Field.Direction s, Field tmp, Field field, Field field2) {
        if (tmp instanceof BoardFieldEdge) return true;
        tmp = tmp.nextField(f);
        if (tmp instanceof BoardFieldEdge) return true;
        tmp = tmp.nextField(s);
        if ((tmp.isEmpty() || tmp.get().isWhite() != this.isWhite) && (!(check_field_and_edge((BoardField) tmp)))) {
            tmp.positiveHighlightField();
        }

        tmp = this.myField;
        tmp = field;
        if (tmp instanceof BoardFieldEdge) return true;
        tmp = field2;
        if (tmp instanceof BoardFieldEdge) return true;
        tmp = tmp.nextField(s);
        if ((tmp.isEmpty() || tmp.get().isWhite() != this.isWhite) && (!(check_field_and_edge((BoardField) tmp)))) {
            tmp.positiveHighlightField();
        }
        return false;
    }

    private void makeRookWayHighlighting() {
        Field tmp = this.myField;
        rook_dir_highlight(tmp, Field.Direction.U);
        rook_dir_highlight(tmp, Field.Direction.D);
        rook_dir_highlight(tmp, Field.Direction.L);
        rook_dir_highlight(tmp, Field.Direction.R);
    }

    private void rook_dir_highlight(Field tmp, Field.Direction ru) {
        while (!(tmp instanceof BoardFieldEdge)) {
            if (!tmp.nextField(ru).isEmpty()) {
                if (tmp.nextField(ru).get().isWhite() != this.isWhite()) {
                    tmp = tmp.nextField(ru);
                    tmp.positiveHighlightField();
                    return;
                }
                break;
            }
            tmp = tmp.nextField(ru);
            if ((tmp.isEmpty() || tmp.get().isWhite() != this.isWhite) && (!(check_field_and_edge((BoardField) tmp)))) {
                tmp.positiveHighlightField();
            }
        }
    }

    private void makeQueenWayHighlighting() {
        makeBishopWayHighlighting();
        makeRookWayHighlighting();

    }

    @Override
    public boolean move(BoardField boardField) {
        return false;
    }

    @Override
    public boolean isWhite() {
        return this.isWhite;
    }

    @Override
    public void setMyField(BoardField field) {
        this.myField = field;
    }

    @Override
    public Field getMyField() {
        return this.myField;
    }

    @Override
    public String getState() {
        return this.figureName + "[" +
                (this.isWhite ? "W" : "B") + "]" +
                this.getMyField().getCol() +
                ":" + this.getMyField().getRow();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof Figure)) {
            return false;
        }

        Figure comparedObj = (Figure) obj;
        return this.isWhite() == comparedObj.isWhite();
    }

    protected void setFigureId() {
        this.setId(this.figureName + "_" + (this.isWhite ? "white" : "black"));
    }

    boolean check_way(BoardField moveTo, Field.Direction dir) {
        Field tmp;
        tmp = this.myField;
        tmp = tmp.nextField(dir);
        while (!tmp.equals(moveTo)) {

            if (!tmp.isEmpty()) {
                return true;
            }
            tmp = tmp.nextField(dir);
        }
        return false;
    }

    protected boolean check_field_and_edge(BoardField moveTo) {
        return moveTo.equals(this.myField)
                || (moveTo instanceof BoardFieldEdge)
                || !moveTo.isEmpty() && this.equals(moveTo.get());

    }
}
