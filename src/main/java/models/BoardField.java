package main.java.models;

import javafx.scene.layout.*;
import main.java.models.interfaces.Field;
import main.java.models.interfaces.Figure;

public class BoardField extends StackPane implements Field{
    private int col;
    private int row;
    private Figure figure = null;

    BoardField(int col, int row) {
        this.col = col;
        this.row = row;
        setMaxWidth(70);
        setMaxHeight(70);

        this.setOnMouseClicked(event -> {
            System.out.println(this.col);
            System.out.println(this.row);
            System.out.println(this);
        });
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean isEmpty() {
        return this.figure == null;
    }

    @Override
    public boolean put(Figure figure) {
        figure.setMyField(this);
        this.figure = figure;
        return true;
    }

    @Override
    public boolean remove(Figure figure) {
        if(this.figure == null || !figure.equals(this.figure)) {
            return false;
        } else {
            this.figure = null;
            return true;
        }
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
