package main.java.models.figures;

public class Knight extends Figure {
    public Knight(boolean isWhite) {
        super(isWhite);
        this.figureName = "J";
        this.setFigureId();
    }
}
