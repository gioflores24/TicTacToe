/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssquad.client;

import java.util.Optional;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
 * @author Giovanni
 */
public class GUIComputerGame extends BorderPane {

    Stage st;
    String username;
    // -- if player wins, set to 1, else if computer set to 0;
    private int winner = -1;
    private final Square[][] board = new Square[3][3];
    private char player, computer;
    private final Pane gamePane = new Pane();
    private Label result = new Label("Click to begin");
    private boolean compFirst;

    public GUIComputerGame(Stage st, String username, String playerChosen) {
        this.st = st;
        this.username = username;

        this.setup(playerChosen);

        this.gameInfo();
        this.initGamePane();

    }

    private void setup(String choice) {
        if (choice.equals("X")) {

            this.player = 'X';
            this.computer = 'O';
            this.compFirst = false;

        } else if (choice.equals("O")) {
            this.player = 'O';
            this.computer = 'X';
            this.compFirst = true;

        }
        System.out.println(player + "," + computer);
    }

    private void gameInfo() {
        Label playerName = new Label("Player: " + this.username + " vs Computer");
        playerName.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 18));
        playerName.alignmentProperty().set(Pos.CENTER);
        this.topProperty().set(playerName);
    }

    private void initGamePane() {
        gamePane.setPrefSize(600, 600);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                Square square = new Square();
                square.translateXProperty().set(x * 200);
                square.translateYProperty().set(y * 200);
                gamePane.getChildren().add(square);
                board[x][y] = square;

            }
        }
        result.fontProperty().set(Font.font("Helvetica", FontWeight.SEMI_BOLD, 24));
        result.alignmentProperty().set(Pos.CENTER);

        Button resign = new Button("Resign");
        
        resign.textFillProperty().set(Color.WHITE);
        resign.styleProperty().set("-fx-background-color: red");
        

        resign.setOnAction(event -> {
            resignHandler();
        });
        HBox bottom = new HBox();
        HBox.setMargin(bottom, new Insets(10));
        bottom.spacingProperty().set(200);
        bottom.getChildren().addAll(result, resign);

        this.centerProperty().set(gamePane);
        this.bottomProperty().set(bottom);
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getToken() == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isWon(char token) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getToken() == token
                    && board[i][1].getToken() == token
                    && board[i][2].getToken() == token) {
                drawLine(i, 0, i, 2);
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (board[0][j].getToken() == token
                    && board[1][j].getToken() == token
                    && board[2][j].getToken() == token) {
                drawLine(0, j, 2, j);
                return true;
            }
        }

        if (board[0][0].getToken() == token
                && board[1][1].getToken() == token
                && board[2][2].getToken() == token) {
            drawLine(0, 0, 2, 2);
            return true;
        }

        if (board[0][2].getToken() == token
                && board[1][1].getToken() == token
                && board[2][0].getToken() == token) {
            drawLine(0, 2, 2, 0);
            return true;
        }

        return false;
    }

    class Square extends StackPane {

        private Text currentChar = new Text();
        private char token = ' ';

        public Square() {
            Rectangle border = new Rectangle(200, 200);
            border.fillProperty().set(null);
            border.strokeProperty().set(Color.BLACK);
            this.currentChar.fontProperty().set(new Font("Helvetica", 100));
            this.alignmentProperty().set(Pos.CENTER);
            this.getChildren().addAll(border, currentChar);

            this.setOnMouseClicked(click -> {
                playerTurn();
            });

        }

        public void placeToken(char character) {
            this.token = character;

            if (token == 'X') {
                currentChar.textProperty().set("X");
                return;

            }
            currentChar.textProperty().set("O");
        }

        public char getToken() {
            return token;
        }

        private void playerTurn() {

            if (token == ' ' && player != ' ') {
                if (compFirst) {
                    computerTurn();
                    compFirst = false;
                    result.setText("Your Turn");
                    return;
                }
                placeToken(player);

                if (isWon(player)) {
                    winner = 1;
                    result.textProperty().set(player + " won");
                    player = ' ';
                } else if (isFull()) {
                    result.textProperty().set("Draw");
                    player = ' ';
                } else {
                    computerTurn();
                }
            }
            if (player == ' ') {
                updateScore();
                handleEndgame();
            }

        }
            

        private void computerTurn() {
            int min = 0;
            int max = 2;
            int range = max - min + 1;
            int canPlace = 0;
            while (canPlace == 0) {
                int i = (int) (Math.random() * range) + min;
                int j = (int) (Math.random() * range) + min;
                if (board[i][j].getToken() == ' ') {
                    board[i][j].placeToken(computer);
                    canPlace = 1;
                }
                if (isWon(computer)) {
                    winner = 0;
                    result.textProperty().set(computer + "won!");
                    computer = ' ';
                } else if (isFull()) {
                    result.textProperty().set("Draw");
                    computer = ' ';
                }
            }
            if (computer == ' ') {
                System.out.println("Game has ended");
                updateScore();
                handleEndgame();
            }

        }

        // -- needed so we can draw the line
        public double getCenterX() {
            return this.translateXProperty().get() + 100;
        }

        public double getCenterY() {
            return this.translateYProperty().get() + 100;
        }

    }

    private void handleEndgame() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("TicTacToe");
        alert.setHeaderText("Game Ended");
        alert.setContentText("Would you like to play again?");
        alert.initOwner(this.getScene().getWindow());
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");
        alert.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> selectedButton = alert.showAndWait();
        if (selectedButton.get() == yes) {
            st.setScene(new Scene(new GUIComputerGame(st, username, randomizePlayer())));
        } else if (selectedButton.get() == no) {
            st.setScene(new Scene(new GUIAccount(st, username)));
        } else {
            handleAlertClose();

        }
    }

    private void drawLine(int iStart, int jStart, int iEnd, int jEnd) {
        Line winLine = new Line();
        winLine.strokeWidthProperty().set(5); // -- T H I C C line
        winLine.startXProperty().set(board[iStart][jStart].getCenterX());
        winLine.startYProperty().set(board[iStart][jStart].getCenterY());
        winLine.endXProperty().set(board[iStart][jStart].getCenterX());
        winLine.endYProperty().set(board[iStart][jStart].getCenterY());

        gamePane.getChildren().add(winLine);

        Timeline timeLine = new Timeline();
        timeLine.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(2), new KeyValue(winLine.endXProperty(), board[iEnd][jEnd].getCenterX()),
                        new KeyValue(winLine.endYProperty(), board[iEnd][jEnd].getCenterY())));
        timeLine.play();

    }

    private void handleAlertClose() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("TicTacToe");
        alert.setHeaderText("Dialog Closed");
        alert.setContentText("Would you like to log out or go back to your account?");
        alert.initOwner(this.getScene().getWindow());
        ButtonType logout = new ButtonType("Log out");
        ButtonType goBack = new ButtonType("Go back");
        alert.getButtonTypes().setAll(logout, goBack);
        Optional<ButtonType> selectedButton = alert.showAndWait();
        if (selectedButton.get() == logout) {
            Form.showAlert(AlertType.INFORMATION, this.getScene().getWindow(), "Logged out", "You have been successfully logged out");
            st.setScene(new Scene(new GUILogin(st)));
        } else if (selectedButton.get() == goBack) {
            Form.showAlert(AlertType.INFORMATION, this.getScene().getWindow(), "Account", "You have successfully gone back to your account");
            st.setScene(new Scene(new GUIAccount(st, username)));

        } else {
            handleAlertClose();
        }

    }

    private String randomizePlayer() {
        String p = "";
        Random rn = new Random();
        int choice = rn.nextInt(2);
        System.out.println("random choice: " + choice);
        switch (choice) {
            case 0:
                p = "X";
                break;
            case 1:
                p = "O";
                break;
        }

        return p;
    }

    private void updateScore() {

        String score = "";
        switch (winner) {
            case 0:
                score = "loss";
                break;
            case 1:
                score = "win";
                break;
            case -1:
                score = "tie";
                break;
        }
        GUIMain.client.sendString("setData" + "," + this.username + "," + score);
    }

    private void resignHandler() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("TicTacToe");
        alert.initOwner(this.getScene().getWindow());
        alert.setHeaderText("Resignation Window");
        alert.setContentText("Are you sure you'd like to resign?");

        ButtonType yes = new ButtonType("Yes");
        ButtonType cancel = new ButtonType("Cancel");
        
        alert.getButtonTypes().addAll(yes, cancel);
        Optional<ButtonType> selectedButton = alert.showAndWait();
        if (selectedButton.get() == yes) {
            String score = "loss";
            GUIMain.client.sendString("setData" + "," + this.username + "," + score);
            Form.showAlert(AlertType.INFORMATION, this.getScene().getWindow(), "Resigned", "Game forfeited");
        }
        else if(selectedButton.get() == cancel)
        {
            result.setText("Keep On Playing");
        }
        else if(selectedButton.get() == yes)
        {
            Form.showAlert(AlertType.INFORMATION, this.getScene().getWindow(), "Exited", "Window closed");
            st.setScene(new Scene(new GUIAccount(st, this.username)));
        }

    }
}
