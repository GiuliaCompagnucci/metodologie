package it.unicam.cs.mpgc.rpg123420.view;

import it.unicam.cs.mpgc.rpg123420.controller.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartView {
    private Stage primaryStage;
    private GameView gameView;
    private GameController controller;
    private Label descriptionLabel;
    private Label difficultyDescriptionLabel;

    public StartView(Stage primaryStage, GameController controller) {
        this.primaryStage = primaryStage;
        this.controller = controller;
        this.gameView = new GameView(controller); // Passa il controller anche a GameView
    }

    public void show() {
        primaryStage.setTitle("Selezione Classe - RPG Project");

        Label title = new Label("Scegli la tua Classe");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Nome Eroe");

        ComboBox<String> classCombo = new ComboBox<>();
        classCombo.getItems().addAll("Warrior", "Mage");
        classCombo.setValue("Warrior"); // Default

        // Selezione Difficoltà
        ComboBox<String> difficultyCombo = new ComboBox<>();
        difficultyCombo.getItems().addAll("Normale", "Difficile");
        difficultyCombo.setValue("Normale"); // Default

        // Label per la descrizione
        descriptionLabel = new Label();
        descriptionLabel.setWrapText(true); // Permette al testo di andare a capo
        descriptionLabel.setStyle("-fx-font-style: italic; -fx-text-fill: #555;");
        updateDescription("Warrior"); // Imposta la descrizione iniziale

        difficultyDescriptionLabel = new Label();
        difficultyDescriptionLabel.setWrapText(true); // Permette al testo di andare a capo
        difficultyDescriptionLabel.setStyle("-fx-font-style: italic; -fx-text-fill: #555;");
        updateDifficultyDescription("Normale"); // Imposta la descrizione iniziale

        Button startBtn = new Button("Inizia Avventura");
        Button loadBtn = new Button("Carica Partita Salvata");

        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        // Ordine degli elementi: Titolo, Nome, Classe, Descrizione, Difficoltà, Bottoni
        root.getChildren().addAll(title, nameField, classCombo, descriptionLabel, difficultyCombo, difficultyDescriptionLabel, startBtn, loadBtn);

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Listener per aggiornare la descrizione quando cambia la selezione
        classCombo.setOnAction(e -> {
            String selected = classCombo.getValue();
            if (selected != null) {
                updateDescription(selected);
            }
        });

        // Listener per la Difficoltà
        difficultyCombo.setOnAction(e -> {
            String selected = difficultyCombo.getValue();
            if (selected != null) {
                updateDifficultyDescription(selected);
            }
        });

        startBtn.setOnAction(e -> {
            String name = nameField.getText().isEmpty() ? "Eroe" : nameField.getText();
            String selectedClass = classCombo.getValue();
            String difficulty = difficultyCombo.getValue();

            System.out.println("Avvio nuovo gioco per: " + name + " come " + selectedClass + " in difficoltà " + difficulty);

            controller.startNewGame(selectedClass, name, difficulty);
            gameView.start(primaryStage); // Mostra la vista di gioco
        });

        loadBtn.setOnAction(e -> {
            controller.loadGame();
            if (controller.isGameStarted()) {
                gameView.start(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Nessun salvataggio trovato!");
                alert.showAndWait();
            }
        });
    }

    private void updateDescription(String className) {
        if (className.equals("Warrior")) {
            descriptionLabel.setText("Guerriero: Alta resistenza fisica e danni costanti. Ideale per chi preferisce la sopravvivenza.");
        } else if (className.equals("Mage")) {
            descriptionLabel.setText("Mago: Bassa resistenza ma danni magici devastanti. Ideale per chi vuole finire i nemici in pochi colpi.");
        } else {
            descriptionLabel.setText("");
        }
    }

    private void updateDifficultyDescription(String difficulty) {
        if (difficulty.equals("Normale")) {
            difficultyDescriptionLabel.setText("Normale: Dungeon più corto (3-5 stanze) con meno nemici per stanza.");
        } else if (difficulty.equals("Difficile")) {
            difficultyDescriptionLabel.setText("Difficile: Dungeon più lungo (5-7 stanze) con più nemici e sfide impegnative.");
        } else {
            difficultyDescriptionLabel.setText("");
        }
    }
}
