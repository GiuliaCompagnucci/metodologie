package it.unicam.cs.mpgc.rpg123420;

import it.unicam.cs.mpgc.rpg123420.controller.GameController;
import it.unicam.cs.mpgc.rpg123420.view.StartView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameController controller = new GameController();
        StartView startView = new StartView(primaryStage, controller);
        startView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}