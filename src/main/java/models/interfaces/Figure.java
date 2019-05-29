package main.java.models.interfaces;

import main.java.models.BoardField;

/**
 * Figure interface.
 *
 * @author Bohdan Inhliziian (xinhli00)
 * @author Konetzný Jakub (xinhli00)
 */
public interface Figure {
    boolean isWhite();

    void setMyField(BoardField field);

    Field getMyField();

    String getState();

    boolean check_move(BoardField moveTo);

    boolean move(BoardField boardField);

    boolean move_for_player(BoardField boardField);
}
