package com.cssquad.client;

import com.cssquad.commons.ResponseCode;
import com.cssquad.server.Validation;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GUILogin extends GridPane {

    Stage st;

    public GUILogin(Stage st) {
        this.st = st;

        Initializable.initGUI(this);
        Constraints.setColumnConstraints(this);
        createForm();
    }

    private void createForm() {
        Form.header(this, "Login Form");
        TextField username = Form.username(this, "Username: ");
        TextField password = Form.password(this);
        Button submitButton = Form.submit(this, "Log in");

        Button disconnect = Form.disconnect(this);
        disconnect.hoverProperty().addListener((obs, notHovering, isHovering) -> {

            if (isHovering) {
                disconnect.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 12));
            } else {
                disconnect.fontProperty().set(Font.font("Helvetica", FontWeight.SEMI_BOLD, 12));

            }

        });
        disconnect.setOnAction(e -> {
            st.setScene(new Scene(new GUIConnection(st)));
        });

        this.forgotPassword();
        this.register();

        this.getFormData(submitButton, username, password);

    }

    private void forgotPassword() {
        Button fgt = new Button("Forgot Password?");
        fgt.alignmentProperty().set(Pos.BASELINE_CENTER);
        fgt.textFillProperty().set(
                Color.CADETBLUE);
        fgt.hoverProperty().addListener((obs, notHovering, isHovering) -> {
            if (isHovering) {
                fgt.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 12));
            } else {
                fgt.fontProperty().set(Font.font("Helvetica", FontWeight.MEDIUM, 12));
            }
        });
        fgt.setOnAction(event -> {

            st.setScene(new Scene(new GUIForgotPassword(st)));

        });

        this.add(fgt, 1, 5, 2, 1);
    }

    private void register() {
        Button register = new Button("Don't have an account? Sign Up");
        register.alignmentProperty().set(Pos.BASELINE_CENTER);
        register.textFillProperty().set(Color.RED);

        register.hoverProperty().addListener((obs, notHovering, isHovering) -> {
            if (isHovering) {
                register.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 12));
            } else {
                register.fontProperty().set(Font.font("Helvetica", FontWeight.MEDIUM, 12));
            }

        });

        this.add(register, 1, 6, 2, 1);

        register.setOnAction(event -> {
            st.setScene(new Scene(new GUIRegistration(st)));
        });

    }

    //repeated. Needs refactoring
    private void getFormData(Button submitButton, TextField username, TextField password) {
        submitButton.setOnAction(event -> {
            if (username.getText().isEmpty()) {
                Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Form Error!", "Please enter a username");
                return;
            }

            if (password.getText().isEmpty()) {
                Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Form Error!", "Please enter a password");
                return;
            }
            System.out.println(password.getText());
            if (!Validation.validPassword(password.getText())) {
                Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Invalid password", "Please enter valid password");
                return;
            }

            //send to client
            String httpResponse = GUIMain.client.sendString("login" + "," + username.getText() + "," + password.getText());
            System.out.println(httpResponse);
            if (httpResponse.equals(ResponseCode.CANNOT_LOGIN)) {
                Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Invalid Login", "Incorrect username or password. Please enter correct information");
                return;
            } else if (httpResponse.equals(ResponseCode.FAILED_LOGIN)) {
                Form.showAlert(Alert.AlertType.WARNING, this.getScene().getWindow(), "Failed login", "Failed login 5 times. Check your email for your keycode");
                return;
            }
            Form.showAlert(Alert.AlertType.INFORMATION, this.getScene().getWindow(), "Logged In", "Welcome, " + username.getText());
            st.setScene(new Scene(new GUIAccount(st, username.getText())));

        });
    }

}
