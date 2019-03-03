package com.cssquad.client;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author giovanniflores Simple TicTacToe game implementation
 *
 */
public class GUIPlayerGame extends BorderPane {

    Stage st;

    private final Pane pane = new Pane();
    private boolean isXPlaying = true;
    private boolean hasEnded = false;
    private final Square[][] gameBoard = new Square[3][3]; // -- size will never change for tic tac toe
    private final List<Combination> combinations = new ArrayList<>();
    
    public GUIPlayerGame(Stage st) {
        this.st = st;

        
        this.paddingProperty().set(new Insets(25, 25, 25, 25));
        this.gameInfo();
        this.initGamePane();
    }

    /**
     * Shows player names, w/l/t ratio, etc.
     */
        private void gameInfo() {
        Label playerName = new Label("Player: johndoe335"); // -- hardcoded. Must change
        playerName.textFillProperty().set(Color.BLACK);
        playerName.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 24));
        this.topProperty().set(playerName);
    }

    /**
     * Represents an X or an O on the game board
     *
     * @param None
     *
     */
    class Square extends StackPane {

        private Text playerChar = new Text();

        public Square() {
            Rectangle border = new Rectangle(200, 200);
            border.fillProperty().set(null);
            border.strokeProperty().set(Color.BLACK);

            playerChar.fontProperty().set(new Font("Helvetica", 100));

            this.alignmentProperty().set(Pos.CENTER);
            this.getChildren().addAll(border, playerChar);
            this.setOnMouseClicked(click -> {
                if (hasEnded) {
                    return;
                }

                if (click.getButton() == MouseButton.PRIMARY) {
                    if (!isXPlaying) {
                        return;
                    }
                    drawX();
                    isXPlaying = false;
                    checkBoard();
                } else if (click.getButton() == MouseButton.SECONDARY) {
                    if (isXPlaying) {
                        return;
                    }
                    drawO();
                    isXPlaying = true;
                    checkBoard();
                }
            });
        }

        private void drawX() {
            playerChar.textProperty().set("X");
        }

        private void drawO() {
            playerChar.textProperty().set("O");
        }

        public String getChar() {
            return playerChar.textProperty().get();
        }

        public double getCenterX() {
            return this.translateXProperty().get() + 100;
        }

        public double getCenterY() {
            return this.translateYProperty().get() + 100;
        }
    }

    class Combination {

        private final Square[] squares;

        public Combination(Square... squares) {
            this.squares = squares;
        }
        

        public boolean isFilled() {
            if (squares[0].getChar().isEmpty()) {
                return false;
            }
            return squares[0].getChar().equals(squares[1].getChar())
                    && squares[0].getChar().equals(squares[2].getChar());
        }
    }

//	@Override
//	public void start(Stage primaryStage) throws Exception {
//
//		primaryStage.titleProperty().set("Tic Tac Toe");
//		primaryStage.setScene(new Scene(initGamePane()));
//		primaryStage.show();
//	}
    /**
     * Creates the board itself
     *
     * @return Pane
     */
    private void initGamePane() {
        pane.setPrefSize(600, 600);

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {

                Square square = new Square();
                square.translateXProperty().set(x * 200);
                square.translateYProperty().set(y * 200);
                pane.getChildren().add(square);
                gameBoard[x][y] = square;
            }
        }
        // -- horizontal
        for (int y = 0; y < 3; y++) {
            combinations.add(new Combination(gameBoard[0][y], gameBoard[1][y], gameBoard[2][y]));
        }
        // -- vertical
        for (int x = 0; x < 3; x++) {
            combinations.add(new Combination(gameBoard[x][0], gameBoard[x][1], gameBoard[x][2]));
        }
        combinations.add(new Combination(gameBoard[0][0], gameBoard[1][1], gameBoard[2][2]));
        combinations.add(new Combination(gameBoard[2][0], gameBoard[1][1], gameBoard[0][2]));
        //this.getChildren().add(pane);
        this.centerProperty().set(pane);
    }

    private void checkBoard() {
        for (Combination c : combinations) {
            if (c.isFilled()) {
                drawWinLine(c);
                hasEnded = true;
                break;
            }
        }
    }

    private void drawWinLine(Combination c) {
        Line winLine = new Line();
        winLine.strokeWidthProperty().set(5); // -- T H I C C line
        winLine.startXProperty().set(c.squares[0].getCenterX());
        winLine.startYProperty().set(c.squares[0].getCenterY());
        winLine.endXProperty().set(c.squares[0].getCenterX());
        winLine.endYProperty().set(c.squares[0].getCenterY());

        pane.getChildren().add(winLine);

        Timeline timeLine = new Timeline();
        timeLine.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(2), new KeyValue(winLine.endXProperty(), c.squares[2].getCenterX()),
                        new KeyValue(winLine.endYProperty(), c.squares[2].getCenterY())));
        timeLine.play();

    }

}
