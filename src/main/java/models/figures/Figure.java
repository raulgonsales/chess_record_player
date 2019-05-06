package main.java.models.figures;

import main.java.models.BoardField;
import main.java.models.interfaces.Field;

public class Figure implements main.java.models.interfaces.Figure {
    private boolean isWhite;
    private BoardField myField;
    protected String figureName;

    public Figure(boolean isWhite) {
        this.isWhite = isWhite;
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
}
