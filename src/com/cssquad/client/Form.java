/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssquad.client;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;

/**
 *
 * @author Giovanni
 */
public abstract class Form implements IForm {

    public static Label header(GridPane pane, String labelText) {
        Label headerLabel = new Label(labelText);
        headerLabel.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 24));
        pane.add(headerLabel, 0, 0, 2, 1);

        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));
        return null;
    }

    public static TextField username(GridPane pane, String usernameText) {
        Label usernameLabel = new Label("Username: ");
        pane.add(usernameLabel, 0, 1);

        // username: (textField)
        TextField usernameField = new TextField();
        usernameField.setPrefHeight(40);
        pane.add(usernameField, 1, 1);

        return usernameField;
    }

    public static TextField email(GridPane pane) {
        // email: (textField)
        Label emailLabel = new Label("Email: ");
        pane.add(emailLabel, 0, 2);

        TextField emailField = new TextField();
        emailField.prefHeightProperty().set(IGUI.FIELD_HEIGHT);
        pane.add(emailField, 1, 2);

        return emailField;
    }

    public static TextField password(GridPane pane) {
        // password: (textField)
        Label passwordLabel = new Label("Password: ");
        pane.add(passwordLabel, 0, 3);

        PasswordField passwordField = new PasswordField();
        passwordField.prefHeightProperty().set(IGUI.FIELD_HEIGHT);
        pane.add(passwordField, 1, 3);
        return passwordField;
    }

   
    public static Button submit(GridPane pane, String btnText) {
        Button submitButton = new Button(btnText);
        submitButton.styleProperty().set("     linear-gradient(#f0ff35, #a9ff00),\n" +
"        radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);\n" +
"    -fx-background-radius: 6, 5;\n" +
"    -fx-background-insets: 0, 1;\n" +
"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\n" +
"    -fx-text-fill: #395306;");
		submitButton.prefHeightProperty().set(IGUI.FIELD_HEIGHT);
		submitButton.defaultButtonProperty().set(true);
		submitButton.prefWidthProperty().set(IGUI.BUTTON_HEIGHT);
		pane.add(submitButton, 0, 4, 2, 1);

		GridPane.setHalignment(submitButton, HPos.CENTER);
		GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

		return submitButton;
    }
    public static Button disconnect(GridPane pane)
    {
        Button disconnect = new Button("Disconnect");
        disconnect.textFillProperty().set(Color.WHITE);
        disconnect.styleProperty().set("-fx-background-color: purple");
        
        pane.add(disconnect, 2, 3);
        return disconnect;
    }
     public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
