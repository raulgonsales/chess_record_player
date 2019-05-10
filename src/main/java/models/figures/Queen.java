package main.java.models.figures;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import main.java.models.BoardField;

public class Queen extends Figure {
    public Queen(boolean isWhite) {
        super(isWhite);
        this.figureName = "D";
        this.setFigureId();

        setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                /* allow any transfer mode */
                Dragboard db = startDragAndDrop(TransferMode.MOVE);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString("" + myField.getCol() + myField.getRow());
                db.setContent(content);

                event.consume();
            }
        });

        setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                }
                event.consume();
            }
        });
    }

    @Override
    public boolean move(BoardField moveTo) {
        if (moveTo.equals(this.myField)) {
            return false;
        }

        if (moveTo.getRow() > moveTo.getBoard().getBoardSize() || moveTo.getCol() > moveTo.getBoard().getBoardSize() ||
                moveTo.getRow() < 1 || moveTo.getCol() < 1) {
            return false;
        }

        if ((!moveTo.isEmpty()) && (moveTo.get().isWhite() == this.myField.get().isWhite()
        )) {

            return false;
        }


        this.myField.remove();
        moveTo.put(this);
        return true;
    }
}
