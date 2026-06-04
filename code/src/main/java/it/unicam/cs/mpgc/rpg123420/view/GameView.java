package it.unicam.cs.mpgc.rpg123420.view;

import it.unicam.cs.mpgc.rpg123420.controller.GameController;
import it.unicam.cs.mpgc.rpg123420.model.entity.Enemy;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import java.util.List;

public class GameView {
    private GameController controller;
    private TextArea logArea;
    private VBox enemyContainer;
    private Label statusLabel;

    public void start(Stage primaryStage) {
        controller = new GameController();

        primaryStage.setTitle("RPG Dungeon Crawler - Matricola 123420");

        // UI Components
        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(150);

        enemyContainer = new VBox(10);
        enemyContainer.setAlignment(Pos.CENTER);

        Button attackBtn = new Button("Attacca Nemico 1");
        Button nextRoomBtn = new Button("Prossima Stanza");
        Button saveBtn = new Button("Salva Partita");
        Button loadBtn = new Button("Carica Partita");

        // Layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(statusLabel, enemyContainer, logArea, attackBtn, nextRoomBtn, saveBtn, loadBtn);

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        updateUI();

        // Event Handlers
        attackBtn.setOnAction(e -> {
            String result = controller.attackEnemy(0); // Semplificato: attacca sempre il primo
            logArea.appendText(result + "\n");
            updateUI();
        });

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
        if (controller.isGameOver()) {
            statusLabel.setText("GAME OVER");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        if (controller.isVictory()) {
            statusLabel.setText("VITTORIA! Dungeon Completato.");
            statusLabel.setStyle("-fx-text-fill: green;");
            return;
        }

        statusLabel.setText("Stanza: " + (controller.getDungeon().getCurrentRoomIndex() + 1) +
                " | HP Eroe: " + controller.getPlayer().getCurrentHealth());

        enemyContainer.getChildren().clear();
        List<Enemy> enemies = controller.getCurrentEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            Label enemyLabel = new Label(e.getName() + " (HP: " + e.getCurrentHealth() + "/" + e.getMaxHealth() + ")");
            if (!e.isAlive()) {
                enemyLabel.setStyle("-fx-text-fill: gray; -fx-strikethrough: true;");
            }
            enemyContainer.getChildren().add(enemyLabel);
        }
    }
}