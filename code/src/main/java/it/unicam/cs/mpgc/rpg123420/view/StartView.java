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

        Button startBtn = new Button("Inizia Avventura");
        Button loadBtn = new Button("Carica Partita Salvata");

        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title, nameField, classCombo, startBtn, loadBtn);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

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
}
