package it.unicam.cs.mpgc.rpg123420;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("RPG Project");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}