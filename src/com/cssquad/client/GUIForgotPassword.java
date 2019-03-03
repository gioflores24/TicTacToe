/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssquad.client;

import com.cssquad.commons.Email;
import com.cssquad.commons.ResponseCode;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author Giovanni
 */
public class GUIForgotPassword extends GridPane {

    Stage st;
    String newPassword = "";

    public GUIForgotPassword(Stage st) {
        this.st = st;
        Initializable.initGUI(this);

        this.promptEmail();
    }

    private void promptEmail() {

        VBox header = new VBox();

        header.prefHeightProperty().set(100);
        Label forgotPassword = new Label("Forgot Password? ");
        forgotPassword.textFillProperty().set(Color.CADETBLUE);
        forgotPassword.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 24));
        header.getChildren().add(forgotPassword);

        Label enterEmail = new Label("Enter your email and email password");
        enterEmail.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 20));

        TextField emailField = new TextField();
        emailField.promptTextProperty().set("email");

        Button send = new Button("Send Email");

        send.setOnAction(event -> {
            this.newPassword = Email.sendPassword(emailField.getText(), "If you're receiving this email, it's because you forgot your password.\n"
                    + "Your temporary password is ");
            String response = GUIMain.client.sendString("forgotPassword" + "," + emailField.getText() + "," + this.newPassword);
            System.out.println(response);
            if (response.equals(ResponseCode.PASSWORD_CHANGED)) {
                st.setScene(new Scene(new GUIUnlocked(st, emailField.getText())));
            } else {
                System.out.println("Something went wrong");
            }
        });

        Button goBack = new Button("Login screen");
        goBack.hoverProperty().addListener((obs, notHovering, isHovering) -> {
            if (isHovering) {
                goBack.fontProperty().set(Font.font("Arial", FontWeight.BOLD, 14));

            } else {
                goBack.fontProperty().set(Font.font("Arial", FontWeight.MEDIUM, 12));
            }
        });
        goBack.setWrapText(true);
        goBack.textFillProperty().set(Color.WHITE);
        goBack.styleProperty().set("-fx-background-color: \n"
                + "        #090a0c,\n"
                + "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n"
                + "        linear-gradient(#20262b, #191d22),\n"
                + "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n"
                + "    -fx-text-fill: white;\n"
                + "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n"
                + "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n"
                + "    -fx-padding: 10 20 10 20;");
        goBack.setOnAction(event -> {
            st.setScene(new Scene(new GUILogin(st)));
        });

        this.add(header, 0, 0, 2, 1);
        this.add(enterEmail, 0, 1, 2, 1);
        this.add(emailField, 0, 2, 2, 1);

        this.add(send, 0, 4, 2, 1);

        this.add(goBack, 0, 6, 2, 1);

    }

}
