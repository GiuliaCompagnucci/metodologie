package it.unicam.cs.mpgc.rpg123420.view;

import it.unicam.cs.mpgc.rpg123420.controller.GameController;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe responsabile della schermata iniziale di configurazione del gioco.
 * Permette all'utente di selezionare la classe dell'eroe, la difficoltà del dungeon
 * e di avviare una nuova partita o caricarne una esistente.
 * Funge da punto di ingresso principale per l'interazione utente prima dell'inizio del gameplay.
 */
public class StartView {
    private Stage primaryStage;
    private GameView gameView;
    private GameController controller;

    // Label dinamiche per fornire feedback descrittivo all'utente
    private Label classDescriptionLabel;
    private Label difficultyDescriptionLabel;

    /**
     * Costruttore della View iniziale.
     * Inizializza il riferimento allo stage principale, al controller e prepara la GameView.
     * @param primaryStage Lo stage principale dell'applicazione JavaFX.
     * @param controller Il controller che gestisce la logica di business e lo stato del gioco.
     */
    public StartView(Stage primaryStage, GameController controller) {
        this.primaryStage = primaryStage;
        this.controller = controller;
        // Inizializza la vista di gioco passando controller e stage per permettere la navigazione successiva
        this.gameView = new GameView(controller, primaryStage);
    }

    /**
     * Configura e mostra la scena di selezione iniziale.
     * Definisce il layout UI, i componenti di input e registra gli handler per gli eventi utente.
     */
    public void show() {
        primaryStage.setTitle("Selezione Classe - RPG Project");

        Label title = new Label("Scegli la tua Classe");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Nome Eroe");

        // Selezione Classe Eroe
        ComboBox<String> classCombo = new ComboBox<>();
        classCombo.getItems().addAll("Warrior", "Mage");
        classCombo.setValue("Warrior"); // Default

        // Selezione Difficoltà Dungeon
        ComboBox<String> difficultyCombo = new ComboBox<>();
        difficultyCombo.getItems().addAll("Normale", "Difficile");
        difficultyCombo.setValue("Normale"); // Default

        // Label per la descrizione dinamica della classe
        classDescriptionLabel = new Label();
        classDescriptionLabel.setWrapText(true);
        classDescriptionLabel.setStyle("-fx-font-style: italic; -fx-text-fill: #555;");
        updateDescription("Warrior"); // Imposta la descrizione iniziale

        // Label per la descrizione dinamica della difficoltà
        difficultyDescriptionLabel = new Label();
        difficultyDescriptionLabel.setWrapText(true);
        difficultyDescriptionLabel.setStyle("-fx-font-style: italic; -fx-text-fill: #555;");
        updateDifficultyDescription("Normale"); // Imposta la descrizione iniziale

        Button startBtn = new Button("Inizia Avventura");
        Button loadBtn = new Button("Carica Partita Salvata");
        Button exitBtn = new Button("Esci dal Gioco");
        exitBtn.setStyle("-fx-base: #d9534f; -fx-text-fill: white;");

        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);

        VBox buttonBox = new VBox(10, startBtn, loadBtn, exitBtn);
        buttonBox.setAlignment(Pos.CENTER);

        // Assemblaggio della scena
        root.getChildren().addAll(
                title,
                nameField,
                classCombo,
                classDescriptionLabel,
                difficultyCombo,
                difficultyDescriptionLabel,
                buttonBox
        );

        Scene scene = new Scene(root, 400, 450);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Listener: Aggiorna la descrizione testuale quando cambia la classe selezionata
        classCombo.setOnAction(e -> {
            String selected = classCombo.getValue();
            if (selected != null) {
                updateDescription(selected);
            }
        });

        // Listener: Aggiorna la descrizione testuale quando cambia la difficoltà
        difficultyCombo.setOnAction(e -> {
            String selected = difficultyCombo.getValue();
            if (selected != null) {
                updateDifficultyDescription(selected);
            }
        });

        // Handler: Avvia una nuova partita configurando il controller con i parametri scelti
        startBtn.setOnAction(e -> {
            String name = nameField.getText().isEmpty() ? "Eroe" : nameField.getText();
            String selectedClass = classCombo.getValue();
            String difficulty = difficultyCombo.getValue();

            System.out.println("Avvio nuovo gioco per: " + name + " come " + selectedClass + " in difficoltà " + difficulty);

            controller.startNewGame(selectedClass, name, difficulty);
            gameView.start(primaryStage); // Passa alla vista di gioco
        });

        // Handler: Tenta di caricare una partita salvata precedentemente
        loadBtn.setOnAction(e -> {
            controller.loadGame();
            if (controller.isGameStarted()) {
                gameView.start(primaryStage);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Nessun salvataggio trovato!");
                alert.showAndWait();
            }
        });

        // Handler: Chiude l'applicazione
        exitBtn.setOnAction(e -> {
            Platform.exit();
        });
    }

    /**
     * Aggiorna il testo descrittivo in base alla classe dell'eroe selezionata.
     * @param className Il nome della classe (es. "Warrior", "Mage").
     */
    private void updateDescription(String className) {
        if (className.equals("Warrior")) {
            classDescriptionLabel.setText("Guerriero: Alta resistenza fisica e danni costanti. Ideale per chi preferisce la sopravvivenza.");
        } else if (className.equals("Mage")) {
            classDescriptionLabel.setText("Mago: Bassa resistenza ma danni magici devastanti. Ideale per chi vuole finire i nemici in pochi colpi.");
        } else {
            classDescriptionLabel.setText("");
        }
    }

    /**
     * Aggiorna il testo descrittivo in base al livello di difficoltà selezionato.
     * @param difficulty Il livello di difficoltà (es. "Normale", "Difficile").
     */
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
