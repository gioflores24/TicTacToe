package com.cssquad.client;

import com.cssquad.commons.ResponseCode;
import com.cssquad.server.Validation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author giovanniflores
 *
 */
public class GUIRegistration extends GridPane {

    Stage st;

    public GUIRegistration(Stage st) {
        this.st = st;
        Initializable.initGUI(this);
        Constraints.setColumnConstraints(this);
        createForm();

    }

    private void createForm() {
        Form.header(this, "Registration Form");

        TextField usernameField = Form.username(this, "Registration Form");

        TextField emailField = Form.email(this);
        TextField passwordField = Form.password(this);

        Button submitButton = Form.submit(this, "Sign Up");

        Button goBack = new Button("Login Screen");
        goBack.prefWidthProperty().set(150);
        goBack.hoverProperty().addListener((obs, notHovering, isHovering) ->{
            if(isHovering)
            {
                goBack.fontProperty().set(Font.font("Arial", FontWeight.BOLD, 14));
                
            }
            else goBack.fontProperty().set(Font.font("Arial", FontWeight.MEDIUM, 12));
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
                + "    -fx-font-family: \"Arial\";\n"
                + "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n"
                + "    -fx-font-size: 12px;\n"
                + "    -fx-padding: 10 20 10 20;");
        goBack.setOnAction(e -> {
            st.setScene(new Scene(new GUILogin(st)));
        });
        this.add(goBack, 0, 4);

        Button disconnect = Form.disconnect(this);
        disconnect.hoverProperty().addListener((obs, notHovering, isHovering) -> {
            if (isHovering) {
                disconnect.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 12));
            } else {
                disconnect.fontProperty().set(Font.font("Helvetica", FontWeight.SEMI_BOLD, 12));

            }

        });
        disconnect.setOnAction(e -> {
            Form.showAlert(Alert.AlertType.INFORMATION, this.getScene().getWindow(), "Disconnected", "Disconnected from account");
            st.setScene(new Scene(new GUIConnection(st)));
        });

        this.getFormData(submitButton, usernameField, emailField, passwordField);

    }

    /**
     * Passes information to the connection thread
     */
    private void getFormData(Button submitButton, TextField username, TextField email, TextField password) {
        submitButton.setOnAction(event -> {
            if (username.getText().isEmpty()) {
                Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Form Error!", "Please enter a username");
                return;
            }
            if (email.getText().isEmpty()) {
                Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Form Error!", "Please enter an email");
                return;
            }
            if (password.getText().isEmpty()) {
                Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Form Error!", "Please enter a password");
                return;
            }

            if (!Validation.validEmailAddress(email.getText())) {
                Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Invalid email", "Please enter valid email address");
                return;
            }
            if (!Validation.validPassword(password.getText())) {
                Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Invalid password", "Please enter valid password");
                return;
            }

            String _msg = "register" + "," + username.getText() + "," + password.getText() + "," + email.getText();
            String httpResponse = GUIMain.client.sendString(_msg);
            if (httpResponse.equals(ResponseCode.REGISTERED)) {
                Form.showAlert(Alert.AlertType.ERROR, this.getScene().getWindow(), "Already registered", "Invalid username. Account already registered");
                return;
            }

            Form.showAlert(Alert.AlertType.INFORMATION, this.getScene().getWindow(), "Registered", "Welcome, " + username.getText());
            st.setScene(new Scene(new GUIAccount(st, username.getText())));
        });
    }

}
