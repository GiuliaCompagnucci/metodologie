package it.unicam.cs.mpgc.rpg123420.view;

import it.unicam.cs.mpgc.rpg123420.controller.GameController;
import it.unicam.cs.mpgc.rpg123420.model.entity.enemy.Enemy;
import it.unicam.cs.mpgc.rpg123420.model.entity.item.Item;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import java.util.List;

public class GameView {
    private GameController controller;

    // Componenti UI
    private TextArea logArea;
    private VBox enemyContainer;
    private VBox inventoryContainer;
    private Label statusLabel;
    private HBox actionContainer; // Contiene i bottoni di attacco
    private Button nextRoomBtn;   // Bottone per avanzare

    public GameView(GameController controller) {
        this.controller = controller;
    }

    public void start(Stage primaryStage) {
        if (controller == null) {
            throw new IllegalStateException("Controller non inizializzato!");
        }

        primaryStage.setTitle("RPG Dungeon Crawler - Matricola 123420");

        // 1. Label di Stato (Nome, Classe, HP, Stanza)
        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10px;");

        // 2. Area di Log
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(150);
        logArea.setWrapText(true);

        // 3. Contenitore Nemici
        enemyContainer = new VBox(10);
        enemyContainer.setAlignment(Pos.CENTER);
        enemyContainer.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10px;");

        // 4. Contenitore Azioni (Bottoni Attacco)
        actionContainer = new HBox(10);
        actionContainer.setAlignment(Pos.CENTER);

        // 5. Contenitore Inventario
        inventoryContainer = new VBox(5);
        inventoryContainer.setAlignment(Pos.CENTER);
        inventoryContainer.setStyle("-fx-border-color: #aaa; -fx-border-width: 1; -fx-padding: 10px; -fx-background-color: #f9f9f9;");

        // 6. Bottoni Globali
        nextRoomBtn = new Button("Prossima Stanza");
        nextRoomBtn.setStyle("-fx-base: #4caf50; -fx-text-fill: white;"); // Verde

        Button saveBtn = new Button("Salva Partita");
        Button loadBtn = new Button("Carica Partita");

        // Layout Principale
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        // Aggiunta elementi in ordine verticale
        root.getChildren().addAll(
                statusLabel,
                enemyContainer,
                actionContainer,
                new Label("--- Inventario ---"),
                inventoryContainer,
                logArea,
                nextRoomBtn,
                new HBox(10, saveBtn, loadBtn)
        );

        Scene scene = new Scene(root, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Inizializzazione UI
        updateUI();

        // --- Event Handlers ---

        nextRoomBtn.setOnAction(e -> {
            controller.nextRoom();
            logArea.appendText(">> Ti avventuri nella prossima stanza...\n");
            updateUI();
        });

        saveBtn.setOnAction(e -> {
            controller.saveGame();
            logArea.appendText(">> Partita Salvata con successo!\n");
        });

        loadBtn.setOnAction(e -> {
            controller.loadGame();
            logArea.appendText(">> Partita Caricata.\n");
            updateUI();
        });
    }

    /**
     * Aggiorna tutta l'interfaccia grafica in base allo stato corrente del Controller.
     */
    private void updateUI() {
        // Sicurezza: se il controller non è pronto o il gioco non è iniziato
        if (controller == null || !controller.isGameStarted()) {
            statusLabel.setText("In attesa di inizio gioco...");
            return;
        }

        // 1. Gestione Fine Gioco / Vittoria
        if (controller.isGameOver()) {
            statusLabel.setText("GAME OVER - Sei stato sconfitto!");
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 16px;");
            clearActionAreas();
            return;
        }
        if (controller.isVictory()) {
            statusLabel.setText("VITTORIA! Hai completato il Dungeon.");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 16px;");
            clearActionAreas();
            return;
        }

        // 2. Aggiornamento Status Bar
        String heroClass = controller.getPlayer().getClass().getSimpleName();
        String heroName = controller.getPlayer().getName();
        int currentHp = controller.getPlayer().getCurrentHealth();
        int maxHp = controller.getPlayer().getMaxHealth();
        int roomIndex = controller.getDungeon().getCurrentRoomIndex() + 1;

        statusLabel.setText(String.format("%s (%s) | Stanza: %d | HP: %d/%d",
                heroName, heroClass, roomIndex, currentHp, maxHp));
        statusLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // 3. Aggiornamento Nemici e Bottoni Attacco
        enemyContainer.getChildren().clear();
        actionContainer.getChildren().clear();

        List<Enemy> enemies = controller.getCurrentEnemies();
        boolean allEnemiesDefeated = true;

        if (enemies.isEmpty()) {
            enemyContainer.getChildren().add(new Label("Nessun nemico in vista."));
        } else {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);

                // Label del nemico
                Label enemyLabel = new Label(e.getName() + " (HP: " + e.getCurrentHealth() + "/" + e.getMaxHealth() + ")");
                if (!e.isAlive()) {
                    enemyLabel.setStyle("-fx-text-fill: gray; -fx-strikethrough: true;");
                } else {
                    allEnemiesDefeated = false; // C'è almeno un nemico vivo
                }
                enemyContainer.getChildren().add(enemyLabel);

                // Bottone Attacco (solo se vivo)
                if (e.isAlive()) {
                    Button attackBtn = new Button("Attacca " + e.getName());
                    int enemyIndex = i;

                    attackBtn.setOnAction(event -> {
                        String result = controller.attackEnemy(enemyIndex);
                        logArea.appendText(result + "\n");
                        updateUI(); // Ricorsione per aggiornare HP e stati
                    });

                    actionContainer.getChildren().add(attackBtn);
                }
            }
        }

        // 4. Gestione Visibilità Bottone "Prossima Stanza"
        if (allEnemiesDefeated && !enemies.isEmpty()) {
            nextRoomBtn.setVisible(true);
            nextRoomBtn.setDisable(false);
        } else if (enemies.isEmpty()) {
            nextRoomBtn.setVisible(true);
            nextRoomBtn.setDisable(false);
        } else {
            nextRoomBtn.setVisible(false); // Nascondi se ci sono nemici vivi
        }

        // 5. Aggiornamento Inventario
        inventoryContainer.getChildren().clear();
        List<Item> items = controller.getPlayerItems();

        if (items.isEmpty()) {
            inventoryContainer.getChildren().add(new Label("Zaino vuoto."));
        } else {
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                HBox itemRow = new HBox(10);
                itemRow.setAlignment(Pos.CENTER_LEFT);

                Label itemLabel = new Label(item.getName() + ": " + item.getDescription());
                Button useBtn = new Button("Usa");

                int finalI = i;
                useBtn.setOnAction(e -> {
                    controller.useItem(finalI);
                    logArea.appendText(">> Hai usato: " + item.getName() + "\n");
                    updateUI();
                });

                itemRow.getChildren().addAll(itemLabel, useBtn);
                inventoryContainer.getChildren().add(itemRow);
            }
        }
    }

    private void clearActionAreas() {
        actionContainer.getChildren().clear();
        inventoryContainer.getChildren().clear();
        nextRoomBtn.setVisible(false);
    }
}