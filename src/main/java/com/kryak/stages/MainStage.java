package com.kryak.stages;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import com.kryak.controllers.Controller;
import com.kryak.controllers.DataBaseController;


import java.util.ArrayList;
import java.util.Arrays;

import static com.kryak.controllers.Controller.*;

public class MainStage extends Application {
    ArrayList<String> valueNames = new ArrayList<String>(Arrays.asList("RUB", "USD", "EUR","GBP","CNY","JPY"));
    Controller controller = new Controller();

    private TextField insert = new TextField("0");
    private ChoiceBox choiceBoxInsert = new ChoiceBox(FXCollections.observableList(valueNames));
    private Label equals = new Label("===");
    private ChoiceBox choiceBoxResult = new ChoiceBox(FXCollections.observableList(valueNames));
    private Button button = new Button("Get Result");
    private TextArea area = new TextArea();
    private Button login = new Button("Log In");
    private Button signup = new Button("Sign Up");
    private Button settings = new Button("Settings");
    public static Label auth = new Label("guest");

    public String choiceInsert;
    public String choiceResult;
    public Label dateTime = new Label();

    @Override
    public void start(Stage primaryStage) throws Exception {

        scrollingMouse(insert);
        DataBaseController dataBaseController = new DataBaseController();
        dataBaseController.getID();

        choiceBoxInsert.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> choiceInsert = valueNames.get(new_value.intValue()));
        choiceBoxResult.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> choiceResult = valueNames.get(new_value.intValue()));

        button.setOnAction(ae -> {
            double quantity = cutDouble(Double.parseDouble(insert.getText()));
            double resultat = cutDouble(controller.convertCurrency(quantity, choiceInsert, choiceResult));
            String res = controller.createStringResult(quantity, resultat, choiceInsert, choiceResult);
            area.setText(area.getText() + res);
            logToFile(res);
            dataBaseController.addOperation(res,auth.getText());
        });

        settings.setOnAction(event -> {
            Settings settings = new Settings();
            settings.Settings(primaryStage);
        });

        signup.setOnAction(event ->  {
            Registration reg = new Registration();
            reg.Registration(primaryStage);
        });

        login.setOnAction(event ->  {
            Authentication auth = new Authentication();
            auth.Authentication(primaryStage);
        });

        controller.initClock(dateTime);
        area.setPrefSize(600, 300);
        area.setEditable(false);

        primaryStage.setTitle("CurrencyConverter");
        BorderPane rootNode = new BorderPane();
        rootNode.setPadding(new Insets(10,10,10,10));
        BorderPane topPane = new BorderPane();
        topPane.setRight(dateTime);
        FlowPane topFlowPane = new FlowPane(10,10);
        topFlowPane.getChildren().addAll(login, signup,settings,auth);
        topPane.setLeft(topFlowPane);
        rootNode.setTop(topPane);
        FlowPane flowPane = new FlowPane(10,10);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.getChildren().addAll(insert, choiceBoxInsert, equals, choiceBoxResult, button);
        rootNode.setCenter(flowPane);
        rootNode.setBottom(area);
        primaryStage.setScene(new Scene(rootNode, 750, 500));
        primaryStage.show();

    }

    public static void main(String[] args) { launch(args);
    }
}
