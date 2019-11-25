package com.kryak.stages;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Settings implements Stages{
    public void Settings(Stage primaryStage) {
        Label secondLabel = new Label("Not yet implemented!");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 300, 100);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Settings");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);

        newWindow.show();
    }
}
