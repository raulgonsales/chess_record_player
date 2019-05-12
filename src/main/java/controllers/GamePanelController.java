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
import main.java.game.Chess;
import main.java.game.Game;
import main.java.game.GameFactory;
import main.java.models.Board;
import main.java.models.BoardField;
import main.java.models.figures.Figure;
import main.java.parser.Move;
import main.java.parser.Round;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class GamePanelController {
    private int currentMoveIndex = -1;

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

    private Chess game;

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
                if (element instanceof Text) {
                    try {
                        fw.write(((Text) element).getText() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            fw.flush();
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Handle click to the button prev
     *
     * @throws IOException
     */
    public void prev() throws IOException {
        this.next.setVisible(true);

        highlightAnnotation(Color.BLACK, this.currentMoveIndex);

        if (this.currentMoveIndex == 0) {
            this.currentMoveIndex = -1;
            this.prev.setVisible(false);
        } else {
            this.currentMoveIndex -= 1;
            highlightAnnotation(Color.RED, this.currentMoveIndex);
        }

        if (this.hasStarted) {
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

        if (this.currentMoveIndex == -1) {
            this.currentMoveIndex = 0;
        } else {
            highlightAnnotation(Color.BLACK, this.currentMoveIndex);
            this.currentMoveIndex += 1;
        }

        highlightAnnotation(Color.RED, this.currentMoveIndex);

        if (this.currentMoveIndex == this.list_round.size() - 1) {
            this.next.setVisible(false);

            if (this.hasStarted) {
                this.timer.cancel();
                this.pause.setVisible(false);
                this.play.setVisible(false);
            }
        }
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

    public void highlightAnnotation(Color color, int index) {
        Text annotation = (Text) this.annotationContainer.getChildren().get(index);
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
        if (!is_White) {
            alert.setHeaderText("White win");
        } else {
            alert.setHeaderText("Black win");
        }
        alert.showAndWait();
        this.game_panel.setMouseTransparent(true);
    }

    public Figure find_figure(Move move) {
        Figure tmp;
        BoardField moveTo;

        moveTo = game.getBoard().getField(move.getTo_col(), move.getTo_row());

        if (move.getFrom_col() != 0 && move.getFrom_row() != 0) {

            tmp = (Figure) game.getBoard().getField(move.getFrom_col(), move.getFrom_row()).get();
            if (tmp.getFigureName() == "P") {
                if (move.getStone() != null) {
                    return null;
                }
            } else {
                if (move.getStone() != tmp.getFigureName()) {
                    return null;
                }
            }
            if (tmp.check_move(moveTo) == true) return tmp;

        } else if (move.getFrom_col() != 0) {
            for (int i = 1; i <= game.getBoard().getBoardSize(); i++) {
                tmp = (Figure) game.getBoard().getField(move.getFrom_col(), i).get();
                if (tmp == null) {
                    continue;
                }
                if (tmp.check_move(moveTo) == true) {
                    if (tmp.getFigureName() == "P") {
                        if (move.getStone() != null) {
                            return null;
                        }
                    } else {
                        if (move.getStone() != tmp.getFigureName()) {
                            return null;
                        }
                    }

                    return tmp;
                }


            }

            return null;

        } else if (move.getFrom_row() != 0) {
            for (int i = 1; i <= game.getBoard().getBoardSize(); i++) {
                tmp = (Figure) game.getBoard().getField(i, move.getFrom_row()).get();
                if (tmp == null) {
                    continue;
                }
                if (tmp.check_move(moveTo) == true) {
                    if (tmp.getFigureName() == "P") {
                        if (move.getStone() != null) {
                            return null;
                        }
                    } else {
                        if (move.getStone() != tmp.getFigureName()) {
                            return null;
                        }
                    }

                    return tmp;
                }


            }
            return null;


        } else {
            for (int i = 1; i <= game.getBoard().getBoardSize(); i++) {
                for (int j = 1; j <= game.getBoard().getBoardSize(); j++) {

                    tmp = (Figure) game.getBoard().getField(i, j).get();
                    if (tmp == null) {
                        continue;
                    }
                    if (tmp.check_move(moveTo) == true) {
                        if (tmp.getFigureName() == "P") {
                            if (move.getStone() != null) {
                                return null;
                            }
                        } else {
                            if (move.getStone() != tmp.getFigureName()) {
                                return null;
                            }
                        }

                        return tmp;
                    }


                }
            }
            return null;
        }

        return null;
    }
}
