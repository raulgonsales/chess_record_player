package main.java.models.interfaces;

import javafx.scene.input.TransferMode;
import main.java.models.BoardField;

public interface Figure {
    boolean isWhite();
    void setMyField(BoardField field);
    Field getMyField();
    String getState();

    boolean move(BoardField boardField);
}
