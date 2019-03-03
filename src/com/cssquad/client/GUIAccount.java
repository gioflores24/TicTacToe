/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssquad.client;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author Giovanni
 */
public class GUIAccount extends BorderPane {

    Stage st;

    private final String username;
    private final String[] winLossTie; //[0: win, 1: loss, 2: tie]
    private final int LEFT_HEIGHT = 120;
    private final int LEFT_WIDTH = 150, RIGHT_WIDTH = 150;
    

    public GUIAccount(Stage st, String username) {

        this.st = st;
        this.username = username;
        this.winLossTie = GUIMain.client.sendString("getData" + "," + this.username).split(",");
        Initializable.initGUI(this);

        this.leftSidebar();

        this.myAccount();

        this.rightSidebar();

    }

    

    private void myAccount() {
        VBox centerContainer = new VBox();
        centerContainer.alignmentProperty().set(Pos.CENTER);

        HBox row1 = new HBox();
        row1.alignmentProperty().set(Pos.TOP_CENTER);
        Label name = new Label("Name: ");

        name.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 17));
        Label actualName = new Label(username);
        actualName.fontProperty().set(Font.font("Helvetica", FontWeight.MEDIUM, 17));

        row1.getChildren().addAll(name, actualName);

        Label winLossTie = new Label("Win/Loss/Tie: ");

        winLossTie.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 24));

        Label ratio = new Label(this.winLossTie[0] + "/" + this.winLossTie[1] + "/" + this.winLossTie[2]); // -- Hardcoded. Will change.
        ratio.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 24));

        centerContainer.getChildren().addAll(row1, winLossTie, ratio);
        BorderPane.setMargin(centerContainer, new Insets(10));
        this.centerProperty().set(centerContainer);
    }

    private void rightSidebar() {
        Button cp = this.changePassword();
        cp.setOnAction(event
                -> {
            st.setScene(new Scene(new GUIChangePassword(st)));
        });
    }

    private Button changePassword() {
        Button btn = new Button("Change Password");
        btn.textFillProperty().set(Color.WHITE);
        btn.prefWidthProperty().set(RIGHT_WIDTH);
        btn.paddingProperty().set(new Insets(15, 15, 15, 15));

        String color = "-fx-base: rgba(57, 50, 80, 0.80);";
        btn.styleProperty().set(color);
        btn.alignmentProperty().set(Pos.BOTTOM_CENTER);
        this.rightProperty().set(btn);

        return btn;
    }

    private void leftSidebar() {
        GridPane leftSelection = new GridPane();

        leftSelection.add(leftBox("My Account"), 0, 0, 1, 1);

        leftSelection.add(leftBox("Versus"), 0, 1, 1, 1);

        leftSelection.add(leftBox("Computer"), 0, 2, 1, 1);

        Button logout = this.logout();

        leftSelection.add(logout, 0, 3, 1, 1);

        this.leftProperty().set(leftSelection);
    }

    private Button logout() {
        Button btn = new Button("Logout");

        btn.textFillProperty().set(Color.WHITE);
        btn.prefWidthProperty().set(LEFT_WIDTH);
        btn.paddingProperty().set(new Insets(15, 15, 15, 15));

        btn.alignmentProperty().set(Pos.CENTER);
        String color = "-fx-base: rgba(255, 78, 50, 0.89);";
        btn.styleProperty().set(color);

        btn.setOnAction(event -> {
            Form.showAlert(Alert.AlertType.INFORMATION, this.getScene().getWindow(), "Logged Out", "You have successfully logged out");
            st.setScene(new Scene(new GUILogin(st)));
        });

        return btn;
    }

    private VBox leftBox(String contents) {
        VBox vBox = new VBox();
        vBox.prefWidthProperty().set(LEFT_WIDTH);
        vBox.prefHeightProperty().set(LEFT_HEIGHT);
        vBox.setStyle(vBoxBorder());

        Button btn = new Button(contents);

        btn.prefHeightProperty().set(LEFT_HEIGHT);
        btn.prefWidthProperty().set(LEFT_WIDTH);

        btn.setOnAction(event
                -> {
            switch (btn.textProperty().get()) {
                case "My Account":
                    this.myAccount();
                    break;
                case "Versus":
                    st.setScene(new Scene(new GUIPlayerGame(st)));
                    break;
                case "Computer":
                    System.out.println("Play vs computer");
                    st.setScene(new Scene(new GUIChoosePlayer(st, username)));
                    break;

            }

        });

        vBox.getChildren().add(btn);

        return vBox;
    }

    private String vBoxBorder() {
        String cssLayout = "-fx-padding: 0;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;"
                + "-fx-border-color: black;";
        return cssLayout;
    }

}
