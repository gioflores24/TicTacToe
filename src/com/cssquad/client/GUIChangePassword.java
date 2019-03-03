/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cssquad.client;

import com.cssquad.commons.ResponseCode;
import com.cssquad.server.Validation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author Giovanni
 */
public class GUIChangePassword extends GridPane {

    Stage st;
    TextField username;
    TextField password;

    public GUIChangePassword(Stage st) {
        this.st = st;
        Initializable.initGUI(this);
        Constraints.setColumnConstraints(this);
        Form.header(this, "Please enter your current information");
        username = Form.username(this, "");
        password = Form.password(this);
        this.getFormData();

    }

    private void getFormData() {
        Button submit = Form.submit(this, "submit");
        submit.setOnAction(e -> {

            if (username.getText().isEmpty()) {
                Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Form Error!", "Please enter a username");
                return;
            }
            if (password.getText().isEmpty()) {
                Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Form Error!", "Please enter a password");
                return;
            }

            String response = GUIMain.client.sendString("login" + "," + username.getText() + "," + password.getText());
            if (response.equals(ResponseCode.CAN_LOGIN)) {
                st.setScene(new Scene(new GUIEnterNewPassword()));
            }
        });
    }

    class GUIEnterNewPassword extends GridPane {

        public GUIEnterNewPassword() {

            Initializable.initGUI(this);
            Constraints.setColumnConstraints(this);
            this.showForm();
        }

        private void showForm() {
            Form.header(this, "Please enter your new password");
            TextField newPassword = Form.password(this);
            Button submit = Form.submit(this, "Change Password");
            submit.textFillProperty().set(Color.WHITE);
            submit.styleProperty().set("-fx-background-color: #5F9EA0"); // -- cadet blue
            submit.prefWidthProperty().set(120);
            submit.setOnAction(e -> {
                if (newPassword.getText().isEmpty()) {
                    Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Form Error!", "Please enter a password");
                    return;
                }
                if (!Validation.validPassword(password.getText())) {
                    Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Invalid password", "Please enter a valid password");
                }
                String response = GUIMain.client.sendString("changePassword" + ","
                        + username.getText() + ","
                        + newPassword.getText());
                if (response.equals(ResponseCode.PASSWORD_CHANGED)) {
                    st.setScene(new Scene(new GUIConfirmPasswordChange()));
                }
            });
        }
    }

    class GUIConfirmPasswordChange extends GridPane {

        public GUIConfirmPasswordChange() {
            Initializable.initGUI(this);
            Constraints.setColumnConstraints(this);
            this.showForm();
        }

        private void showForm() {
            Label success = new Label("Success!");
            success.textFillProperty().set(Color.GREEN);
            success.alignmentProperty().set(Pos.CENTER);
            success.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 24));

            Label clickHere = new Label("Click Go Back to go back to your account");
            clickHere.textFillProperty().set(Color.BLACK);
            clickHere.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 18));

            this.add(success, 0, 0, 2, 1);
            this.add(clickHere, 0, 1, 2, 1);

            Button ok = new Button("Go Back");
            ok.textFillProperty().set(Color.WHITE);
            ok.styleProperty().set("-fx-background-color: blue;");
            this.add(ok, 0, 2, 2, 1);
            ok.setOnAction(event -> {
                st.setScene(new Scene(new GUIAccount(st, username.getText())));
            });

        }
    }
}
