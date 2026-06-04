package it.unicam.cs.mpgc.rpg123420;

import it.unicam.cs.mpgc.rpg123420.view.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameView view = new GameView();
        view.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}