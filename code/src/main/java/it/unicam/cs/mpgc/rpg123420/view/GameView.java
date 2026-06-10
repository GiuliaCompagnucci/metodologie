package it.unicam.cs.mpgc.rpg123420.view;

import it.unicam.cs.mpgc.rpg123420.controller.GameController;
import it.unicam.cs.mpgc.rpg123420.model.entity.enemy.Enemy;
import it.unicam.cs.mpgc.rpg123420.model.entity.item.Item;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Classe responsabile della presentazione grafica del gioco (View).
 * Gestisce la visualizzazione dello stato dell'eroe, dei nemici, dell'inventario
 * e fornisce i controlli utente per interagire con il GameController.
 * Implementa il pattern Observer aggiornando dinamicamente l'UI in base allo stato del Model.
 */
public class GameView {
    private GameController controller;
    private Stage primaryStage;

    // Componenti UI principali
    private TextArea logArea;
    private HBox enemyContainer;
    private VBox inventoryContainer;
    private Label statusLabel;
    private HBox actionContainer; // Contiene i bottoni di attacco dinamici
    private Button nextRoomBtn;   // Bottone per avanzare alla stanza successiva

    /**
     * Costruttore della View.
     * @param controller Il controller che gestisce la logica di business.
     * @param primaryStage Lo stage principale dell'applicazione JavaFX.
     */
    public GameView(GameController controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;
    }

    /**
     * Inizializza e mostra la scena di gioco.
     * Configura il layout, i componenti grafici e gli event handler.
     * @param primaryStage Lo stage su cui mostrare la scena.
     */
    public void start(Stage primaryStage) {
        if (controller == null) {
            throw new IllegalStateException("Controller non inizializzato!");
        }

        primaryStage.setTitle("RPG Dungeon Crawler - Matricola 123420");

        // --- Inizializzazione Componenti UI ---

        // Label di Stato: mostra Nome, Classe, HP, Stanza e Danno
        statusLabel = new Label();
        statusLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-padding: 10px;");

        // Area di Log: visualizza le azioni di combattimento e gli eventi di gioco
        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(150);
        logArea.setWrapText(true);

        // Contenitore Nemici: lista visiva dei nemici nella stanza corrente
        enemyContainer = new HBox(10);
        enemyContainer.setAlignment(Pos.CENTER);
        enemyContainer.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10px;");

        // Contenitore Azioni: bottoni di attacco generati dinamicamente
        actionContainer = new HBox(10);
        actionContainer.setAlignment(Pos.CENTER);

        // Contenitore Inventario: lista degli oggetti posseduti dall'eroe
        inventoryContainer = new VBox(5);
        inventoryContainer.setAlignment(Pos.CENTER);
        inventoryContainer.setStyle("-fx-border-color: #aaa; -fx-border-width: 1; -fx-padding: 10px; -fx-background-color: #f9f9f9;");

        // Bottoni Globali di navigazione e sistema
        nextRoomBtn = new Button("Prossima Stanza");
        nextRoomBtn.setStyle("-fx-base: #4caf50; -fx-text-fill: white;");

        Button saveBtn = new Button("Salva Partita");
        Button loadBtn = new Button("Carica Partita");
        Button exitBtn = new Button("Esci dal Gioco");
        exitBtn.setStyle("-fx-base: #d9534f; -fx-text-fill: white;");

        // --- Layout Principale ---
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        HBox bottomButtons = new HBox(10, saveBtn, loadBtn, exitBtn);
        bottomButtons.setAlignment(Pos.CENTER);

        // Assemblaggio della scena
        root.getChildren().addAll(
                statusLabel,
                enemyContainer,
                actionContainer,
                new Label("--- Inventario ---"),
                inventoryContainer,
                logArea,
                nextRoomBtn,
                bottomButtons
        );

        Scene scene = new Scene(root, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Aggiornamento iniziale dell'UI in base allo stato del controller
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

        // Logica di uscita intelligente: cambia messaggio in base allo stato di vittoria/sconfitta
        exitBtn.setOnAction(e -> {
            if (controller.isVictory()) {
                showVictoryExitDialog();
            } else if (controller.isGameOver()) {
                showDefeatExitDialog();
            } else {
                showNormalExitDialog();
            }
        });
    }

    /**
     * Mostra un dialog di conferma per l'uscita in caso di Vittoria.
     * Permette all'utente di iniziare una nuova partita o chiudere il gioco.
     */
    private void showVictoryExitDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Vittoria!");
        alert.setHeaderText("Complimenti! Hai completato il Dungeon.");
        alert.setContentText("Vuoi iniziare una nuova avventura?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Torna alla StartView creando una nuova istanza
                StartView startView = new StartView(this.primaryStage, this.controller);
                startView.show();
            } else {
                Platform.exit();
            }
        });
    }

    /**
     * Mostra un dialog di conferma per l'uscita in caso di Sconfitta.
     * Permette all'utente di riprovare o chiudere il gioco.
     */
    private void showDefeatExitDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sconfitta...");
        alert.setHeaderText("Il tuo eroe è caduto in battaglia.");
        alert.setContentText("Vuoi provare di nuovo con una nuova avventura?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Torna alla StartView
                StartView startView = new StartView(this.primaryStage, this.controller);
                startView.show();
            } else {
                Platform.exit();
            }
        });
    }

    /**
     * Mostra un dialog standard di conferma uscita durante il gioco.
     * Ricorda all'utente di salvare i progressi.
     */
    private void showNormalExitDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma Uscita");
        alert.setHeaderText("Stai per uscire dal gioco");
        alert.setContentText("Sei sicuro? Ricorda di salvare la partita se vuoi riprendere in seguito!");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit();
            }
        });
    }

    /**
     * Aggiorna tutti i componenti grafici riflettendo lo stato attuale del GameController.
     * Gestisce la visualizzazione condizionale di nemici, bottoni di attacco e inventario.
     */
    private void updateUI() {
        // Sicurezza: verifica inizializzazione
        if (controller == null || !controller.isGameStarted()) {
            statusLabel.setText("In attesa di inizio gioco...");
            return;
        }

        // Gestione stati terminali (Game Over / Vittoria)
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

        // Aggiornamento Status Bar con dati calcolati
        String heroClass = controller.getPlayer().getClass().getSimpleName();
        String heroName = controller.getPlayer().getName();
        int currentHp = controller.getPlayer().getCurrentHealth();
        int maxHp = controller.getPlayer().getMaxHealth();
        int roomIndex = controller.getDungeon().getCurrentRoomIndex() + 1;

        // Calcolo Danno Totale (Base + Bonus da oggetti)
        int baseDamage = controller.getPlayer().getBaseDamage();
        int bonusDamage = controller.getPlayer().getBonusDamage();
        int totalDamage = baseDamage + bonusDamage;

        statusLabel.setText(String.format("%s (%s) | Stanza: %d | HP: %d/%d | Danno: %d",
                heroName, heroClass, roomIndex, currentHp, maxHp, totalDamage));
        statusLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Rigenerazione dinamica della lista nemici e dei relativi bottoni di attacco
        enemyContainer.getChildren().clear();
        actionContainer.getChildren().clear();

        List<Enemy> enemies = controller.getCurrentEnemies();
        boolean allEnemiesDefeated = true;

        if (enemies.isEmpty()) {
            enemyContainer.getChildren().add(new Label("Nessun nemico in vista."));
        } else {
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);

                // Label informativa del nemico
                Label enemyLabel = new Label(e.getName() + " (HP: " + e.getCurrentHealth() + "/" + e.getMaxHealth() + ")");
                if (!e.isAlive()) {
                    enemyLabel.setStyle("-fx-text-fill: gray; -fx-strikethrough: true;");
                } else {
                    allEnemiesDefeated = false; // Flag per gestire la visibilità del tasto "Avanti"
                }
                enemyContainer.getChildren().add(enemyLabel);

                // Creazione bottone attacco solo per nemici vivi
                if (e.isAlive()) {
                    Button attackBtn = new Button("Attacca " + e.getName());
                    int enemyIndex = i;

                    attackBtn.setOnAction(event -> {
                        String result = controller.attackEnemy(enemyIndex);
                        logArea.appendText(result + "\n");
                        updateUI(); // Aggiornamento ricorsivo post-azione
                    });

                    actionContainer.getChildren().add(attackBtn);
                }
            }
        }

        // Logica di visibilità del bottone "Prossima Stanza"
        if (allEnemiesDefeated && !enemies.isEmpty()) {
            nextRoomBtn.setVisible(true);
            nextRoomBtn.setDisable(false);
        } else if (enemies.isEmpty()) {
            nextRoomBtn.setVisible(true);
            nextRoomBtn.setDisable(false);
        } else {
            nextRoomBtn.setVisible(false); // Nascosto durante il combattimento
        }

        // Aggiornamento dinamico dell'inventario
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

    /**
     * Pulisce le aree di azione e inventario negli stati terminali di gioco.
     */
    private void clearActionAreas() {
        actionContainer.getChildren().clear();
        inventoryContainer.getChildren().clear();
        nextRoomBtn.setVisible(false);
    }
}