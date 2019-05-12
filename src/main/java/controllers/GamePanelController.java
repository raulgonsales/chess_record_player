package main.java.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import main.java.game.Game;
import main.java.game.GameFactory;
import main.java.models.Board;
import main.java.parser.Round;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class GamePanelController {
    private int currentMoveIndex = -1;
    private int currentMoveBlockIndex = -1;

    @FXML
    private Button play;
    @FXML
    private Button pause;
    @FXML
    private Button prev;
    @FXML
    private Button next;
    @FXML
    private Button save_game;

    private Timer timer;
    private boolean hasStarted = false;

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
        board.setGamePanelController(this);
        board.setWhites_round(true);
        board.setMaxWidth(630);
        board.setMaxHeight(630);
        board.setTranslateY(20);
        this.game_panel.getChildren().addAll(board);
        StackPane.setAlignment(board, Pos.TOP_CENTER);

        this.game = GameFactory.crateChessGame(board);
        this.list_round = this.startPageController.getList_round();

        this.createAnnotationPanel();
        this.setInitialAnnotation();

        this.pause.setVisible(false);
        this.prev.setVisible(false);
        StackPane.setAlignment(this.save_game, Pos.TOP_RIGHT);
    }

    /**
     * Handle click to the button save
     *
     * @throws IOException
     */
    public void save() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);

        try {
            FileWriter fw = new FileWriter(file);
            annotationContainer.getChildren().forEach(element -> {
                HBox movesBlock = (HBox) ((HBox) element).getChildren().get(1);
                Text iteration = (Text) ((HBox) element).getChildren().get(0);
                this.writeToFile(fw, iteration);

                movesBlock.getChildren().forEach(text -> this.writeToFile(fw, (Text) text));
                this.writeToFile(fw, new Text("\n"));
            });
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void writeToFile(FileWriter fw, Text data) {
        try {
            fw.write(((Text) data).getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle click to the button prev
     *
     * @throws IOException
     */
    public void prev() throws IOException {
        this.next.setVisible(true);

        if (this.currentMoveBlockIndex == 0 && this.currentMoveIndex == 0) {
            highlightAnnotation(Color.BLACK, this.currentMoveBlockIndex, this.currentMoveIndex);
            this.currentMoveBlockIndex = -1;
            this.currentMoveIndex = -1;
            this.prev.setVisible(false);
            return;
        } else {
            highlightAnnotation(Color.BLACK, this.currentMoveBlockIndex, this.currentMoveIndex);

            if (this.currentMoveIndex == 0) {
                this.currentMoveBlockIndex -= 1;
                this.currentMoveIndex = 1;
            } else if (currentMoveIndex == 1) {
                currentMoveIndex -= 1;
            }
        }

        highlightAnnotation(Color.RED, this.currentMoveBlockIndex, this.currentMoveIndex);

        if(this.hasStarted) {
            this.timer.cancel();
            this.pause.setVisible(false);
            this.play.setVisible(true);
        }
    }

    /**
     * Handle click to the button next
     *
     * @throws IOException
     */
    public void next() throws IOException {
        this.prev.setVisible(true);

        if(this.currentMoveBlockIndex == -1) {
            this.currentMoveBlockIndex = 0;
            this.currentMoveIndex = 0;
        } else if(this.currentMoveBlockIndex == this.list_round.size() - 1 && this.currentMoveIndex == 0) {
            highlightAnnotation(Color.BLACK, this.currentMoveBlockIndex, this.currentMoveIndex);
            this.currentMoveIndex += 1;
            this.next.setVisible(false);

            if (this.hasStarted) {
                this.timer.cancel();
                this.pause.setVisible(false);
                this.play.setVisible(false);
            }
        } else {
            highlightAnnotation(Color.BLACK, this.currentMoveBlockIndex, this.currentMoveIndex);

            if (currentMoveIndex == 1) {
                currentMoveBlockIndex += 1;
                currentMoveIndex = 0;
            } else if (currentMoveIndex == 0) {
                currentMoveIndex += 1;
            }
        }

        highlightAnnotation(Color.RED, this.currentMoveBlockIndex, this.currentMoveIndex);
    }

    /**
     * Handle click to the button play
     *
     * @throws IOException
     */
    public void play() throws IOException {
        this.play.setVisible(false);
        this.pause.setVisible(true);

        this.timer = new Timer();
        timer.schedule(
                new TimerTask() {

                    @Override
                    public void run() {
                        try {
                            next();
                            hasStarted = true;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 2000);
    }

    /**
     * Handle click to the button pause
     *
     * @throws IOException
     */
    public void pause() throws IOException {
        this.pause.setVisible(false);
        this.play.setVisible(true);
        this.timer.cancel();
    }
    
    public void highlightAnnotation(Color color, int blockIndex, int moveIndex) {
        HBox moveBlock = (HBox) this.annotationContainer.getChildren().get(blockIndex);
        HBox moveAnnotationBlock = (HBox) moveBlock.getChildren().get(1);
        Text moveAnnotation = (Text) moveAnnotationBlock.getChildren().get(moveIndex);
        moveAnnotation.setFill(color);
    }

    /**
     * Sets annotation to annotation panel from initial file with all saved annotations
     */
    public void setInitialAnnotation() {
        for (int i = 0; i < list_round.size(); i++) {
            HBox annotationBlock = new HBox();
            annotationBlock.getChildren().add(new Text((i+1) + ". "));

            HBox annotationMovesBlock = new HBox();
            Text textWhite = createMoveAnnotation(this.list_round.get(i).getWhite().toString());
            if (list_round.get(i).getBlack() != null && list_round.get(i).getWhite() != null) {
                Text textBlack = createMoveAnnotation(" " + this.list_round.get(i).getBlack().toString());
                annotationMovesBlock.getChildren().addAll(textWhite, textBlack);
            } else if (list_round.get(i).getWhite() != null) {
                annotationMovesBlock.getChildren().addAll(textWhite);
            }

            annotationBlock.getChildren().add(annotationMovesBlock);
            this.annotationContainer.getChildren().add(annotationBlock);
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

    public void end_game(boolean is_White) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game end");
        System.out.println("tady");
        if (!is_White) {
            alert.setHeaderText("White win");
        } else {
            alert.setHeaderText("Black win");
        }
        alert.showAndWait();
        this.game_panel.setMouseTransparent(true);
    }
}
