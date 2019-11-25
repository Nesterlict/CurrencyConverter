package com.kryak.stages;

import com.kryak.controllers.DataBaseController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Authentication implements Stages{
    public void Authentication(Stage primaryStage){

        Label auth = new Label("Log in your account");
        TextField loginField = new TextField();
        Label loginLabel = new Label("Login");
        TextField passwordField = new TextField();
        Label passwordLabel = new Label("Password");
        Button login = new Button("Log in");
        Button signup = new Button("Create new Account");
        Label errors = new Label("");


        VBox secondaryLayout = new VBox();
        secondaryLayout.setAlignment(Pos.CENTER);
        FlowPane loginPane = new FlowPane(10,10);
        loginPane.getChildren().addAll(loginField,loginLabel);
        FlowPane passwordPane = new FlowPane(10,10);
        passwordPane.getChildren().addAll(passwordField,passwordLabel);
        secondaryLayout.getChildren().addAll(auth,loginPane,passwordPane,login,errors,signup);
        secondaryLayout.setPadding(new Insets(10,10,10,10));
        secondaryLayout.setSpacing(10);
        Scene registrationScene = new Scene(secondaryLayout, 300, 300);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Authentification");
        newWindow.setScene(registrationScene);

        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);
        newWindow.show();

        login.setOnAction(event -> {
            DataBaseController dataBaseController = new DataBaseController();
            dataBaseController.authenicateUser(loginField.getText(),passwordField.getText());
        });

        signup.setOnAction(event -> {
            Registration reg = new Registration();
            reg.Registration(primaryStage);
            newWindow.close();
        });
    }
}
