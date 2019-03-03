package com.cssquad.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GUIConnection extends GridPane {

    Stage st;

    public GUIConnection(Stage st) {

        this.st = st;
        Initializable.initGUI(this);
        this.getIPInput();
    }

    private void getIPInput() {

        Label ipEnter = new Label("Enter IP Address: ");
        ipEnter.fontProperty().set(Font.font("Helvetica", FontWeight.BOLD, 24));
        TextField ipField = new TextField();

        Button ipSubmit = new Button("Connect");

        ipSubmit.styleProperty().set("fx-background-color: linear-gradient(#ffd65b, #e68400), linear-gradient(#ffef84, #f2ba44),"
                + "linear-gradient(#ffea6a, #efaa22), linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),"
                + "linear-gradient(from 0% 0% to 15% 50%, rgba(255,255,255,0.9), rgba(255,255,255,0));-fx-background-radius: 30;\n"
                + "    -fx-background-insets: 0,1,2,3,0;\n"
                + "    -fx-text-fill: #654b00;\n"
                + "    -fx-font-weight: bold;\n"
                + "    -fx-font-size: 14px;\n"
                + "    -fx-padding: 10 20 10 20;");

        this.add(ipEnter, 0, 0, 2, 1);
        this.add(ipField, 0, 1, 2, 1);
        this.add(ipSubmit, 0, 2, 2, 1);
        ipSubmit.setOnAction(e -> {
            isValidIP(ipField.getText());
        });

    }

    /**
     * Will only let you register if
     *
     * @param input
     */
    private void isValidIP(String input) {

        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println(localhost.getHostAddress()); // -- 192.168.56.1
            if (input.equals("127.0.0.1") || input.equals(localhost.getHostAddress())) {
                st.setScene(new Scene(new GUILogin(st)));
            } else {
                Label invalidIP = new Label("Invalid IP");
                invalidIP.textFillProperty().set(Color.RED);
                this.add(invalidIP, 0, 3, 2, 1);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(GUIConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
