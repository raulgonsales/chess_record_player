package main.java.models;

import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.java.models.interfaces.Field;
import main.java.models.interfaces.Figure;

/**
 * Specific field.
 *
 * @author Bohdan Inhliziian (xinhli00)
 * @author Konetzný Jakub (xinhli00)
 */
public class BoardField extends StackPane implements Field{
    private int col;
    private int row;
    private Figure figure = null;
    private Board board;
    private Field[] envFields;

    BoardField(int col, int row) {
        this.col = col;
        this.row = row;
        this.envFields = new BoardField[8];
        setMaxWidth(70);
        setMaxHeight(70);

        setOnDragOver(event -> {
            /* accept it only if it is  not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != BoardField.this &&
                    event.getDragboard().hasString()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });

        setOnDragDropped(event -> {
            /* data dropped */
            /* if there is a string data on dragboard, read it and use it */
            Dragboard db = event.getDragboard();

            boolean success = false;

            if (db.hasString()) {
                int col1 = Integer.parseInt(String.valueOf(db.getString().charAt(0)));
                int row1 = Integer.parseInt(String.valueOf(db.getString().charAt(1)));
                board.getField(col1, row1).get().move_for_player(BoardField.this);
                success = true;
            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);

            event.consume();
        });
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public void addNextField(Direction dirs, Field field) {
        this.envFields[dirs.getValue()] = field;
    }

    @Override
    public Field nextField(Direction dirs) {
        return this.envFields[dirs.getValue()];
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void positiveHighlightField() {
        this.setBorder(new Border(new BorderStroke(Color.GREEN,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
    }

    @Override
    public boolean isEmpty() {
        return this.figure == null;
    }

    @Override
    public boolean put(Figure figure) {
        figure.setMyField(this);
        this.getChildren().add((Node) figure);
        this.figure = figure;
        return true;
    }

    @Override
    public boolean remove() {
        this.getChildren().removeAll((Node) figure);
        if(this.figure == null) {
            return false;
        } else {
            this.figure = null;
            return true;
        }
    }

    @Override
    public Figure get() {
        return this.isEmpty() ? null : this.figure;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !(obj instanceof BoardField)) {
            return false;
        }

        BoardField comparedObj = (BoardField) obj;
        return this.getCol() == comparedObj.getCol() && this.row == comparedObj.getRow();
    }
}
