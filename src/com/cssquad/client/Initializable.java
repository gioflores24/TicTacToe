package com.cssquad.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public abstract class Initializable implements IGUI {

    public static void initGUI(GridPane p) {

        prefAndInsets(p);
        p.alignmentProperty().set(Pos.CENTER);
        p.hgapProperty().set(20);
        p.vgapProperty().set(10);
    }

    private static void prefAndInsets(Pane p) {
        p.prefWidthProperty().set(IGUI.PANE_WIDTH);
        p.prefHeightProperty().set(IGUI.PANE_HEIGHT);
        p.paddingProperty().set(new Insets(25, 25, 25, 25));
    }

    public static void initGUI(BorderPane p) {
        prefAndInsets(p);

    }

    public static void initGUI(Pane p) {
        prefAndInsets(p);
    }

}
