package com.cssquad.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 */
public class GUIMain extends Application {

    Stage stage;
    public static Client client; // unsafe

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        
        // "official" disconnect
        this.stage.setOnCloseRequest(e -> {
            client.disconnect();
        });
        // TODO Auto-generated method stub
        this.stage.titleProperty().set("TicTacToe");
        this.stage.setScene(initGUI());
        this.stage.show();
    }

    private Scene initGUI() {
        GUIConnection guiConnection = new GUIConnection(stage);
        Scene initialScene = new Scene(guiConnection);
        return initialScene;
    }

    public static void main(String[] args) {
        client = new Client();
        launch(args);
    }

}
