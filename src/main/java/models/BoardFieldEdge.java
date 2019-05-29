package main.java.models;

import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Edges of the board.
 *
 * @author Bohdan Inhliziian (xinhli00)
 * @author Konetzný Jakub (xinhli00)
 */
public class BoardFieldEdge extends BoardField {
    private Text text;

    BoardFieldEdge(int col, int row) {
        super(col, row);
    }

    public void setEdgeText(Text text) {
        this.text = text;
        this.text.setStyle("-fx-font: 20 arial;");
        this.text.setTextAlignment(TextAlignment.CENTER);
        this.getChildren().add(text);
    }
}
