package main.java.models.figures;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import main.java.models.BoardField;

public class Pawn extends Figure {
    public Pawn(boolean isWhite) {
        super(isWhite);
        this.figureName = "P";
        this.setFigureId();

        setOnDragDetected(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
                /* allow any transfer mode */
                Dragboard db = startDragAndDrop(TransferMode.MOVE);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString(""+myField.getCol()+myField.getRow());
                db.setContent(content);

                event.consume();
            }
        });

        setOnDragDone(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {}
                event.consume();
            }
        });
    }

    @Override
    public boolean move(BoardField moveTo) {
        if (moveTo.equals(this.myField)) {
            return false;
        }

        if (moveTo.getCol() != this.myField.getCol()) {
            return false;
        }

        if (this.isWhite && moveTo.getRow() < this.myField.getRow() ||
                !this.isWhite && moveTo.getRow() > this.myField.getRow() ||
                Math.abs(moveTo.getRow() - this.myField.getRow()) >= 2) {
            return false;
        }

        this.myField.remove();
        moveTo.put(this);
        return true;
    }
}
