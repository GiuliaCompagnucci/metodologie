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

        // Label per la descrizione
        descriptionLabel = new Label();
        descriptionLabel.setWrapText(true); // Permette al testo di andare a capo
        descriptionLabel.setStyle("-fx-font-style: italic; -fx-text-fill: #555;");
        updateDescription("Warrior"); // Imposta la descrizione iniziale


        Button startBtn = new Button("Inizia Avventura");
        Button loadBtn = new Button("Carica Partita Salvata");

        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        // Ordine degli elementi: Titolo, Nome, Combo, Descrizione, Bottoni
        root.getChildren().addAll(title, nameField, classCombo, descriptionLabel, startBtn, loadBtn);

        Scene scene = new Scene(root, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Listener per aggiornare la descrizione quando cambia la selezione
        classCombo.setOnAction(e -> {
            String selected = classCombo.getValue();
            if (selected != null) {
                updateDescription(selected);
            }
        });

        startBtn.setOnAction(e -> {
            String name = nameField.getText().isEmpty() ? "Eroe" : nameField.getText();
            String selectedClass = classCombo.getValue();

            System.out.println("Avvio nuovo gioco per: " + name + " come " + selectedClass);

            controller.startNewGame(selectedClass, name);
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
}
