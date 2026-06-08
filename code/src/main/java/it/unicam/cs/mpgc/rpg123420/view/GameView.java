package it.unicam.cs.mpgc.rpg123420.view;

import it.unicam.cs.mpgc.rpg123420.controller.GameController;
import it.unicam.cs.mpgc.rpg123420.model.entity.Enemy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import java.util.List;

public class GameView {
    private GameController controller;
    private TextArea logArea;
    private VBox enemyContainer;
    private Label statusLabel;
    private HBox actionContainer; // Contenitore per i bottoni di attacco dinamici

    // Costruttore che riceve il controller
    public GameView(GameController controller) {
        this.controller = controller;
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("RPG Dungeon Crawler - Matricola 123420");

        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(150);

        enemyContainer = new VBox(10);
        enemyContainer.setAlignment(Pos.CENTER);

        // Contenitore per le azioni (attacchi)
        actionContainer = new HBox(10);
        actionContainer.setAlignment(Pos.CENTER);

        Button nextRoomBtn = new Button("Prossima Stanza");
        Button saveBtn = new Button("Salva Partita");
        Button loadBtn = new Button("Carica Partita");

        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Aggiungiamo i componenti in ordine logico
        root.getChildren().addAll(statusLabel, enemyContainer, actionContainer, logArea, nextRoomBtn, saveBtn, loadBtn);

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        updateUI();

        // Listener per il bottone Next Room
        nextRoomBtn.setOnAction(e -> {
            controller.nextRoom();
            logArea.appendText("Entri nella prossima stanza...\n");
            updateUI();
        });

        saveBtn.setOnAction(e -> {
            controller.saveGame();
            logArea.appendText("Partita Salvata!\n");
        });

        loadBtn.setOnAction(e -> {
            controller.loadGame();
            logArea.appendText("Partita Caricata!\n");
            updateUI();
        });
    }

    private void updateUI() {
        // Gestione Game Over / Vittoria
        if (controller.isGameOver()) {
            statusLabel.setText("GAME OVER");
            statusLabel.setStyle("-fx-text-fill: red;");
            actionContainer.getChildren().clear(); // Pulisce i bottoni attacco
            return;
        }
        if (controller.isVictory()) {
            statusLabel.setText("VITTORIA! Dungeon Completato.");
            statusLabel.setStyle("-fx-text-fill: green;");
            actionContainer.getChildren().clear();
            return;
        }

        // Recupera informazioni dal controller
        String heroClass = controller.getPlayer().getClass().getSimpleName();
        String heroName = controller.getPlayer().getName();
        int currentHp = controller.getPlayer().getCurrentHealth();
        int maxHp = controller.getPlayer().getMaxHealth();
        int roomIndex = controller.getDungeon().getCurrentRoomIndex() + 1;

        // Aggiorna la label di stato con tutte le informazioni rilevanti
        statusLabel.setText(String.format("%s (%s) | Stanza: %d | HP: %d/%d",
                heroName, heroClass, roomIndex, currentHp, maxHp));
        statusLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Pulisce e rigenera la lista nemici e i bottoni
        enemyContainer.getChildren().clear();
        actionContainer.getChildren().clear();

        List<Enemy> enemies = controller.getCurrentEnemies();

        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);

            // Label del nemico
            Label enemyLabel = new Label(e.getName() + " (HP: " + e.getCurrentHealth() + "/" + e.getMaxHealth() + ")");
            if (!e.isAlive()) {
                enemyLabel.setStyle("-fx-text-fill: gray; -fx-strikethrough: true;");
            }
            enemyContainer.getChildren().add(enemyLabel);

            // Bottone di attacco dinamico (solo se il nemico è vivo)
            if (e.isAlive()) {
                Button attackBtn = new Button("Attacca " + e.getName());
                int enemyIndex = i; // Variabile finale per la lambda

                attackBtn.setOnAction(event -> {
                    String result = controller.attackEnemy(enemyIndex);
                    logArea.appendText(result + "\n");
                    updateUI(); // Aggiorna la UI dopo l'attacco
                });

                actionContainer.getChildren().add(attackBtn);
            }
        }

        // Se non ci sono bottoni di attacco (tutti morti), mostra messaggio
        if (actionContainer.getChildren().isEmpty()) {
            Label msg = new Label("Nemici sconfitti! Usa 'Prossima Stanza'.");
            msg.setStyle("-fx-text-fill: green;");
            actionContainer.getChildren().add(msg);
        }
    }
}