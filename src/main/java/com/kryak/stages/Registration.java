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

public class Registration implements Stages{
    public void Registration(Stage primaryStage){

        Label registration = new Label("Create new account");
        TextField loginField = new TextField();
        Label loginLabel = new Label("Login");
        TextField emailField = new TextField();
        Label emaildLabel = new Label("Email");
        TextField passwordField = new TextField();
        Label passwordLabel = new Label("Password");
        Button signup = new Button("Create account");
        Button already = new Button("Already have account");
        Label errors = new Label("");


        VBox secondaryLayout = new VBox();
        FlowPane emailPane = new FlowPane(10,10);
        secondaryLayout.setAlignment(Pos.CENTER);
        emailPane.getChildren().addAll(emailField,emaildLabel);
        FlowPane loginPane = new FlowPane(10,10);
        loginPane.getChildren().addAll(loginField,loginLabel);
        FlowPane passwordPane = new FlowPane(10,10);
        passwordPane.getChildren().addAll(passwordField,passwordLabel);
        secondaryLayout.getChildren().addAll(registration,emailPane,loginPane,passwordPane,signup,errors,already);
        secondaryLayout.setPadding(new Insets(10,10,10,10));
        secondaryLayout.setSpacing(10);
        Scene registrationScene = new Scene(secondaryLayout, 300, 300);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Registration");
        newWindow.setScene(registrationScene);

        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);
        newWindow.show();

        already.setOnAction(event -> {
            Authentication auth = new Authentication();
            auth.Authentication(primaryStage);
            newWindow.close();
        });

        signup.setOnAction(event -> {
            DataBaseController dataBaseController = new DataBaseController();
            dataBaseController.registerUser(emailField.getText(),loginField.getText(),passwordField.getText());
        });
    }
}
