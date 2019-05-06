package main.java.models.figures;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import main.java.models.BoardField;
import main.java.models.interfaces.Field;

public class Figure extends Pane implements main.java.models.interfaces.Figure {
    private boolean isWhite;
    private BoardField myField;
    protected String figureName = "";

    public Figure(boolean isWhite) {
        this.isWhite = isWhite;
        setMaxWidth(55);
        setMaxHeight(55);

        getStylesheets().addAll(this.getClass().getResource("../../../resources/style/figures.css").toExternalForm());
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
                (isWhite ? "W" : "B") + "]" +
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
}
