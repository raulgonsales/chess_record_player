package main.java.controllers;

import javafx.animation.PauseTransition;
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
import javafx.util.Duration;
import main.java.game.Game;
import main.java.game.GameFactory;
import main.java.models.Board;
import main.java.parser.Round;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;


public class GamePanelController {
    private int currentMoveIndex = 0;

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
        highlightAnnotation(Color.RED);
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

        try{
            FileWriter fw=new FileWriter(file);
            annotationContainer.getChildren().forEach(element -> {
                if(element instanceof Text) {
                    try {
                        fw.write(((Text) element).getText()+"\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            fw.flush();
            fw.close();
        }catch(Exception e){System.out.println(e);}
    }

    /**
     * Handle click to the button prev
     *
     * @throws IOException
     */
    public void prev() throws IOException {
        highlightAnnotation(Color.BLACK);
        this.currentMoveIndex -= 1;
        this.next.setVisible(true);
        if (this.currentMoveIndex == 0) {
            this.prev.setVisible(false);
        }
        highlightAnnotation(Color.RED);
    }

    /**
     * Handle click to the button next
     *
     * @throws IOException
     */
    public void next() throws IOException {
        highlightAnnotation(Color.BLACK);
        this.currentMoveIndex += 1;
        this.prev.setVisible(true);
        if (this.currentMoveIndex == this.list_round.size() - 1) {
            this.next.setVisible(false);
        }
        highlightAnnotation(Color.RED);
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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 2000);
    }
    
    public void highlightAnnotation(Color color) {
        Text annotation = (Text) this.annotationContainer.getChildren().get(this.currentMoveIndex);
        annotation.setFill(color);
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

    public void end_game(boolean is_White) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game end");
        System.out.println("tady");
        if (is_White) {
            alert.setHeaderText("White win");
        } else {
            alert.setHeaderText("Black win");
        }
        alert.showAndWait();
        this.game_panel.setMouseTransparent(true);
    }
}
