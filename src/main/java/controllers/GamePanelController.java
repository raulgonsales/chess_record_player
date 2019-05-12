package main.java.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import main.java.game.Game;
import main.java.game.GameFactory;
import main.java.models.Board;
import main.java.controllers.StartPageController;
import main.java.parser.Round;

import java.util.ArrayList;


public class GamePanelController {

    private ArrayList<Round> list_round;

    private StartPageController startPageController;

    private VBox annotationContainer;

    GamePanelController(StartPageController startPageController) {
        this.startPageController = startPageController;
    }

    @FXML
    public StackPane game_panel;

    private Game game;

    public void initialize() {
        Board board = new Board(8);
        board.setMaxWidth(630);
        board.setMaxHeight(630);
        board.setTranslateY(20);
        this.game_panel.getChildren().addAll(board);
        StackPane.setAlignment(board, Pos.TOP_CENTER);

        this.game = GameFactory.crateChessGame(board);
        this.list_round = this.startPageController.getList_round();

        this.createAnnotationPanel();
        this.setInitialAnnotation();
    }

    /**
     * Sets annotation to annotation panel from initial file with all saved annotations
     */
    public void setInitialAnnotation() {
        for (int i = 0; i < list_round.size(); i++) {
            if (list_round.get(i).getBlack() != null && list_round.get(i).getWhite() != null) {
                this.annotationContainer.getChildren().add(this.createMoveAnnotation("" + (i + 1) + ". " +
                        list_round.get(i).getWhite().toString() + " " + list_round.get(i).getBlack().toString()));
            } else if (list_round.get(i).getWhite() != null) {
                this.annotationContainer.getChildren().add(this.createMoveAnnotation("" + (i + 1) + ". " +
                        list_round.get(i).getWhite().toString()));
            }
        }
    }

    /**
     * Creates one text annotation for particular movement
     *
     * @param moveText
     * @return
     */
    public Text createMoveAnnotation(String moveText) {
        Text annotation = new Text(moveText);
        annotation.setOnMouseClicked(event -> System.out.println(annotation.getText()));
        return annotation;
    }

    /**
     * Creates panel for every movement annotation
     */
    public void createAnnotationPanel() {
        ScrollPane annotationPane = new ScrollPane();
        annotationPane.setMaxHeight(200);
        annotationPane.setMaxWidth(800);
        annotationPane.setTranslateY(300);
        StackPane.setAlignment(annotationPane, Pos.CENTER);
        this.game_panel.getChildren().add(annotationPane);

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        annotationPane.setContent(root);

        this.annotationContainer = root;
    }
}
