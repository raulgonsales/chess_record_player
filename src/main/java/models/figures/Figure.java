package main.java.models.figures;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import main.java.models.Board;
import main.java.models.BoardField;
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
            Board board = this.myField.getBoard();
            for (int i = 1; i < board.getBoardSize() - 1; i++) {
                for (int j = 1; j < board.getBoardSize() - 1; j++) {
                    board.getFields()[i][j].setBorder(Border.EMPTY);
                }
            }
            this.myField.positiveHighlightField();

            if (this instanceof Pawn) {
                this.makePawnWayHighlighting();
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

    private void makePawnWayHighlighting() {
        this.myField.nextField(this.isWhite ? Field.Direction.U : Field.Direction.D).positiveHighlightField();
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
        if (moveTo.equals(this.myField)) {
            return true;
        }

        if (moveTo.getRow() > moveTo.getBoard().getBoardSize() || moveTo.getCol() > moveTo.getBoard().getBoardSize() ||
                moveTo.getRow() < 1 || moveTo.getCol() < 1) {
            return true;
        }

        if ((!moveTo.isEmpty()) && (moveTo.get().isWhite() == this.myField.get().isWhite()
        )) {

            return true;
        }
        return false;
    }
}
