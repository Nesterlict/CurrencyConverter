package com.kryak.controllers;

import com.kryak.controllers.ParseFromURL;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.util.Duration;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class Controller {
    ParseFromURL parse = new ParseFromURL();
    HashMap<String,Double> currencyValue = parse.currencyValue;

    public  double convertCurrency(double number, String currency1, String currency2) {
        double result = number * currencyValue.get(currency1) / currencyValue.get(currency2);
        return result;
    }

    public  String createStringResult(double quantity, double result, String choiceInsert, String choiceResult) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String resultat = formatter.format(date) + ": " + quantity + " " + choiceInsert + " = " + result + " " + choiceResult + "\n";
        return resultat;
    }
    public void initClock(Label dateTime) {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public static void scrollingMouse(TextField field) {
        field.setOnScroll((ScrollEvent event) -> {
            if (event.getDeltaY() > 0) {
                int s = Integer.parseInt(field.getText()) + 1;
                field.setText(s + "");
            } else {
                int s = Integer.parseInt(field.getText()) - 1;
                if (s < 0) s = 0;
                field.setText(s + "");
            }
        });
    }

    public static void logToFile(String res) {
        File file = new File("src/main/resources/log.txt");
        try{
            if(!file.exists()){
                System.out.println("We had to make a new file.");
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(res);
            bufferedWriter.close();
            System.out.println(res);
        } catch(IOException e) {
            System.out.println("COULD NOT LOG!!");
        }
    }

    public static double cutDouble(double res) {
        return ((double)((int)(res *100.0)))/100.0;
    }

}
