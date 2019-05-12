package main.java.models;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controllers.GamePanelController;
import main.java.models.interfaces.Field;

public class Board extends StackPane {
    private BoardField[][] fields;
    private int boardSize;
    private GamePanelController gamePanelController;

    enum EdgesLabels {
        a(1),
        b(2),
        c(3),
        d(4),
        e(5),
        f(6),
        g(7),
        h(8);

        int dir_env_index;

        EdgesLabels(int i) {
            this.dir_env_index = i;
        }
    }

    public Board(int boardSize) {
        this.boardSize = boardSize + 2;

        this.fields = new BoardField[this.boardSize][this.boardSize];

        initBoardFields();
    }

    public void setGamePanelController(GamePanelController gamePanelController) {
        this.gamePanelController = gamePanelController;
    }

    public GamePanelController getGamePanelController() {
        return gamePanelController;
    }

    private void initBoardFields() {
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                BoardField field = new BoardField(i, j);

                if(i == 0 || j == 0 || i == (this.boardSize - 1) || j == (this.boardSize - 1)) {
                    field = new BoardFieldEdge(i, j);
                    if (j == 0 && (i >= 1 && i <= 8)) {
                        ((BoardFieldEdge) field).setEdgeText(new Text(EdgesLabels.values()[i - 1].toString()));
                    }

                    if (i == 0 && (j >= 1 && j <= 8)) {
                        ((BoardFieldEdge) field).setEdgeText(new Text(String.valueOf(j)));
                    }

                }
                setAlignment(field, Pos.BOTTOM_LEFT);
                field.setTranslateX(i*70);
                field.setTranslateY(-j*70);
                field.setBoard(this);

                if((j % 2 != 0 && i % 2 != 0) || (j % 2 == 0 && i % 2 == 0) && (i != 0 & j != 0)) {
                    field.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                }

                this.fields[i][j] = field;

                if(j == 9 || i == 9) {
                    continue;
                }
                this.getChildren().add(field);
            }
        }

        for (int i = 1; i < this.boardSize - 1; i++) {
            for (int j = 1; j < this.boardSize - 1; j++) {
                this.fields[i][j].addNextField(Field.Direction.LD, this.fields[i - 1][j - 1]);
                this.fields[i][j].addNextField(Field.Direction.L, this.fields[i - 1][j]);
                this.fields[i][j].addNextField(Field.Direction.LU, this.fields[i - 1][j + 1]);
                this.fields[i][j].addNextField(Field.Direction.U, this.fields[i][j + 1]);
                this.fields[i][j].addNextField(Field.Direction.RU, this.fields[i + 1][j + 1]);
                this.fields[i][j].addNextField(Field.Direction.R, this.fields[i + 1][j]);
                this.fields[i][j].addNextField(Field.Direction.RD, this.fields[i + 1][j - 1]);
                this.fields[i][j].addNextField(Field.Direction.D, this.fields[i][j - 1]);
            }
        }
    }

    public BoardField[][] getFields() {
        return fields;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public BoardField getField(int col, int row) {
        return this.fields[col][row];
    }
}
