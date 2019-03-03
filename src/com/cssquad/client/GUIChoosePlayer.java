/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssquad.client;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Giovanni
 */
public class GUIChoosePlayer extends GridPane {

    Stage st;
    String username;
    String playerChosen;

    public GUIChoosePlayer(Stage st, String username) {
        this.st = st;
        this.username = username;
        Initializable.initGUI(this);
        Constraints.setColumnConstraints(this);
        Form.header(this, "Choose your player, " + this.username);

        HBox players = new HBox();
        players.alignmentProperty().set(Pos.CENTER);
        players.spacingProperty().set(25);
        Button cross = new Button("X");
        cross.prefWidthProperty().set(100);
        cross.prefHeightProperty().set(100);

        Button nought = new Button("O");
        nought.prefWidthProperty().set(100);
        nought.prefHeightProperty().set(100);
        players.getChildren().addAll(cross, nought);
        this.add(players, 0, 2, 2, 1);

        cross.setOnAction(event -> {
            playerChosen = cross.getText();
            st.setScene(new Scene(new GUIComputerGame(st, this.username, this.playerChosen)));
        });

        nought.setOnAction(event -> {
            playerChosen = nought.getText();
            st.setScene(new Scene(new GUIComputerGame(st, this.username, this.playerChosen)));
        });
        

    }
}
