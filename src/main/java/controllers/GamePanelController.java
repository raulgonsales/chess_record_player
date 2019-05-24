package main.java.controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
    @FXML
    private ChoiceBox choose_interval;

    private int timer_interval = 1000;

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
        this.choose_interval.setItems(FXCollections.observableArrayList(
                "Interval", "0.5s", "1s", "2s", "5s", "10s"
        ));
        this.choose_interval.getSelectionModel().selectFirst();
        this.choose_interval.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                switch (newValue.intValue()) {
                    case 0:
                        timer_interval = 1000;
                        break;
                    case 1:
                        timer_interval = 500;
                        break;
                    case 2:
                        timer_interval = 1000;
                        break;
                    case 3:
                        timer_interval = 2000;
                        break;
                    case 4:
                        timer_interval = 5000;
                        break;
                    case 5:
                        timer_interval = 10000;
                        break;
                    default:
                        timer_interval = 1000;
                        break;
                }
            }
        });


        this.createAnnotationPanel();
        this.setInitialAnnotation();

        this.pause.setVisible(false);
        this.prev.setVisible(false);
        StackPane.setAlignment(this.save_game, Pos.TOP_RIGHT);


    }


    /**
     * Handle click to the button save
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
        this.play.setVisible(true);

        if (this.currentMoveBlockIndex == 0 && this.currentMoveIndex == 0) {
            highlightAnnotation(Color.BLACK, this.currentMoveBlockIndex, this.currentMoveIndex);
            this.currentMoveBlockIndex = -1;
            this.currentMoveIndex = -1;
            this.prev.setVisible(false);
        } else {
            highlightAnnotation(Color.BLACK, this.currentMoveBlockIndex, this.currentMoveIndex);

            if (this.currentMoveIndex == 0) {
                this.currentMoveBlockIndex -= 1;
                this.currentMoveIndex = 1;
            } else if (currentMoveIndex == 1) {
                currentMoveIndex -= 1;
            }
        }

        if (currentMoveIndex != -1 && currentMoveBlockIndex != -1) {
            highlightAnnotation(Color.RED, this.currentMoveBlockIndex, this.currentMoveIndex);
        }
        if (this.hasStarted) {
            this.timer.cancel();
            this.pause.setVisible(false);
            this.play.setVisible(true);
        }

        this.reinitBoard();
    }

    /**
     * Handle click to the button next
     *
     * @throws IOException
     */
    public void next() throws IOException {
        this.prev.setVisible(true);

        if (this.currentMoveBlockIndex == -1) {
            this.currentMoveBlockIndex = 0;
            this.currentMoveIndex = 0;
        } else if (this.currentMoveBlockIndex == this.list_round.size() - 1 && this.currentMoveIndex == 0) {
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

        Figure figure = null;
        BoardField moveTo = null;

        if (this.currentMoveIndex == 0) {
            Move move = this.list_round.get(currentMoveBlockIndex).getWhite();
            moveTo = this.game.getBoard().getField(move.getTo_col(), move.getTo_row());
            figure = this.find_figure(move);
        } else if (this.currentMoveIndex == 1) {
            Move move = this.list_round.get(currentMoveBlockIndex).getBlack();
            figure = this.find_figure(this.list_round.get(currentMoveBlockIndex).getBlack());
            moveTo = this.game.getBoard().getField(move.getTo_col(), move.getTo_row());
        }

        if (figure != null) {
            figure.move(moveTo);
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
                        Platform.runLater(() -> {
                            try {
                                next();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

                        hasStarted = true;
                    }
                }, 0, this.timer_interval);
    }

    public void highlightAnnotation(Color color, int blockIndex, int moveIndex) {
        HBox moveBlock = (HBox) this.annotationContainer.getChildren().get(blockIndex);
        HBox moveAnnotationBlock = (HBox) moveBlock.getChildren().get(1);
        Text moveAnnotation = (Text) moveAnnotationBlock.getChildren().get(moveIndex);
        moveAnnotation.setFill(color);
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
            HBox annotationBlock = new HBox();
            annotationBlock.getChildren().add(new Text((i + 1) + ". "));

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

    public void reinitBoard() {
        for (int i = 0; i < this.game.getBoard().getBoardSize(); i++) {
            for (int j = 0; j < this.game.getBoard().getBoardSize(); j++) {
                if (this.game.getBoard().getField(i, j).get() != null) {
                    this.game.getBoard().getField(i, j).remove();
                }
            }
        }

        Board board = new Board(8);
        board.setGamePanelController(this);
        board.setWhites_round(true);
        board.setMaxWidth(630);
        board.setMaxHeight(630);
        board.setTranslateY(20);
        this.game_panel.getChildren().addAll(board);
        StackPane.setAlignment(board, Pos.TOP_CENTER);
        Figure figure;
        BoardField moveTo;
        Move move;

        this.game = GameFactory.crateChessGame(board);
        if (currentMoveBlockIndex == -1 && currentMoveIndex == -1) {
            return;
        }

        for (int i = 0; i <= currentMoveBlockIndex; i++) {

            move = this.list_round.get(i).getWhite();
            moveTo = this.game.getBoard().getField(move.getTo_col(), move.getTo_row());
            figure = this.find_figure(move);

            if (figure != null) {
                figure.move(moveTo);
            }

            if (i == currentMoveBlockIndex && currentMoveIndex == 0) {
                break;
            }

            move = this.list_round.get(i).getBlack();
            moveTo = this.game.getBoard().getField(move.getTo_col(), move.getTo_row());
            figure = this.find_figure(move);


            if (figure != null) {
                figure.move(moveTo);
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
        annotation.setOnMouseClicked(event -> {
            HBox currentBlock = (HBox) annotation.getParent();
            if (this.currentMoveBlockIndex >= 0) {
                this.highlightAnnotation(Color.BLACK, this.currentMoveBlockIndex, this.currentMoveIndex);
            }
            this.currentMoveIndex = currentBlock.getChildren().indexOf(annotation);
            this.currentMoveBlockIndex = this.annotationContainer.getChildren().indexOf(currentBlock.getParent());

            this.reinitBoard();
            this.highlightAnnotation(Color.RED, this.currentMoveBlockIndex, this.currentMoveIndex);
            if (this.currentMoveBlockIndex >= 0) {
                this.prev.setVisible(true);
            }
            if(this.currentMoveBlockIndex == this.list_round.size() - 1 && this.currentMoveIndex == 1) {
                this.next.setVisible(false);
                this.pause.setVisible(false);
                this.play.setVisible(false);
            }
        });
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
        try {
            this.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.game_panel.setMouseTransparent(true);
    }

    /**
     * find figure which can be movet to move
     *
     * @param move where to move
     * @return figure which can be movet to move or null
     */
    public Figure find_figure(Move move) {
        Figure tmp;
        BoardField moveTo;

        moveTo = game.getBoard().getField(move.getTo_col(), move.getTo_row());

        if (move.getFrom_col() != 0 && move.getFrom_row() != 0) {

            tmp = (Figure) game.getBoard().getField(move.getFrom_col(), move.getFrom_row()).get();
            if (tmp.getFigureName().equals("P")) {
                if (move.getStone() != null) {
                    return null;
                }
            } else {
                if (!move.getStone().equals(tmp.getFigureName())) {
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
                    if (tmp.getFigureName().equals("P")) {
                        if (move.getStone() != null) {
                            return null;
                        }
                    } else {
                        if (!move.getStone().equals(tmp.getFigureName())) {
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
                    if (tmp.getFigureName().equals("P")) {
                        if (move.getStone() != null) {
                            return null;
                        }
                    } else {
                        if (!move.getStone().equals(tmp.getFigureName())) {
                            return null;
                        }
                    }

                    return tmp;
                }


            }
            return null;


        } else {
            for (int i = 1; i <= game.getBoard().getBoardSize() - 2; i++) {
                for (int j = 1; j <= game.getBoard().getBoardSize() - 2; j++) {
                    tmp = (Figure) game.getBoard().getField(i, j).get();
                    if (tmp == null) {
                        continue;
                    }
                    if (tmp.check_move(moveTo) == true) {
                        if (tmp.getFigureName().equals("P")) {
                            if (move.getStone() != null) {
                                continue;
                            }
                        } else {
                            if (move.getStone() == null) {
                                continue;
                            }
                            if (!move.getStone().equals(tmp.getFigureName())) {
                                continue;
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

    public int getCurrentMoveIndex() {
        return currentMoveIndex;
    }

    public int getCurrentMoveBlockIndex() {
        return currentMoveBlockIndex;
    }

    public ArrayList<Round> getList_round() {
        return list_round;
    }

    public void setList_round(ArrayList<Round> list_round) {
        this.list_round = list_round;
    }

    /**
     * overwrite list of round with move
     *
     * @param move will be add after active move
     */
    public void overwrite_list_round(Move move) {
        ArrayList<Round> list = new ArrayList<>();
        if (this.currentMoveBlockIndex == -1 && this.currentMoveIndex == -1) {
            this.currentMoveIndex = 0;
            list = new ArrayList<>();
            Round round = new Round(move, null);
            list.add(round);
            this.setList_round(list);
            this.createAnnotationPanel();

        } else {
            for (int i = 0; i <= currentMoveBlockIndex; i++) {
                list.add(this.list_round.get(i));
            }

            if (currentMoveIndex == 1) {
                Round round = new Round(move, null);
                list.add(round);
                //Possible bug
                currentMoveIndex = 0;
            } else {
                currentMoveBlockIndex++;

                Round round = new Round(this.list_round.get(currentMoveBlockIndex).getWhite(), move);
                list.add(round);
                currentMoveIndex = 1;

            }

            this.setList_round(list);
            this.createAnnotationPanel();
        }


    }


}
