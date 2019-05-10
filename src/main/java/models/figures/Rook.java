package main.java.models.figures;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import main.java.models.BoardField;
import main.java.models.interfaces.Field;

public class Rook extends Figure {
    public Rook(boolean isWhite) {
        super(isWhite);
        this.figureName = "V";
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

        this.myField.remove();
        moveTo.put(this);
        return true;
    }


}
