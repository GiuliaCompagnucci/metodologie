package it.unicam.cs.mpgc.rpg123420;

import it.unicam.cs.mpgc.rpg123420.controller.GameController;
import it.unicam.cs.mpgc.rpg123420.view.StartView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe principale di avvio dell'applicazione RPG Dungeon Crawler.
 * Estende la classe Application di JavaFX per gestire il ciclo di vita della GUI.
 * Inizializza il GameController (Model/Controller) e la StartView (View) secondo il pattern MVC.
 */
public class MainApp extends Application {

    /**
     * Metodo chiamato automaticamente dal runtime JavaFX all'avvio dell'applicazione.
     * Configura lo stage principale e mostra la schermata di selezione iniziale.
     * @param primaryStage Lo stage principale fornito da JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        // Creazione del controller che gestisce la logica di business e lo stato del gioco
        GameController controller = new GameController();

        // Creazione e visualizzazione della vista iniziale, passando il controller per la coordinazione
        StartView startView = new StartView(primaryStage, controller);
        startView.show();
    }

    /**
     * Punto di ingresso standard dell'applicazione Java.
     * Delega l'avvio al framework JavaFX tramite il metodo launch.
     * @param args Argomenti passati dalla riga di comando (non utilizzati in questa implementazione).
     */
    public static void main(String[] args) {
        launch(args);
    }
}