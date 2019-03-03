/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssquad.client;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Displays once email is sent to user who forgot their password
 *
 * @author Giovanni
 */
public class GUIUnlocked extends GridPane {

    Stage st;

    public GUIUnlocked(Stage st, String email) {
        this.st = st;
        this.setup();
        this.sentConfirmation(email);
    }

    

    private void setup() {

        Initializable.initGUI(this);
        Constraints.setColumnConstraints(this);
    }

    private void sentConfirmation(String email) {
        Label sent = new Label("Email was sent to:  " + email);
        sent.textFillProperty().set(Color.GREEN);
        sent.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 24));

        Label checkEmailLabel = new Label("Please check your email for your temporary password.");
        checkEmailLabel.textFillProperty().set(Color.BLACK);
        checkEmailLabel.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 18));

        Button ok = new Button("Ok");
        ok.styleProperty().set("-fx-base: rgba(0,128,0);");
        ok.setOnAction(event -> {
            st.setScene(new Scene(new GUILogin(st)));
        });

        this.add(sent, 0, 0, 2, 1);
        this.add(checkEmailLabel, 0, 1, 2, 1);
        this.add(ok, 0, 2, 2, 1);
    }

    

}
