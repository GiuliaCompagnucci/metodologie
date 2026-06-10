package it.unicam.cs.mpgc.rpg123420.persistence;

import com.google.gson.Gson;
import it.unicam.cs.mpgc.rpg123420.persistence.dto.GameStateDTO;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Implementazione concreta dell'interfaccia DataStore che gestisce la persistenza
 * dei dati di gioco utilizzando il formato JSON.
 * Sfrutta la libreria Gson con adapter custom (tramite GsonProvider) per serializzare
 * e deserializzare correttamente gerarchie di classi polimorfiche come Player, Enemy e Item.
 */
public class JsonDataStore implements DataStore {

    private final Gson gson = GsonProvider.getGson();

    /**
     * Salva lo stato corrente del gioco su un file JSON specificato.
     * Utilizza FileWriter per scrivere la rappresentazione stringa dell'oggetto DTO.
     * @param gameState L'oggetto DTO contenente i dati del giocatore e del dungeon da salvare.
     * @param filename Il nome del file di destinazione (es. "savegame.json").
     */
    @Override
    public void saveGame(GameStateDTO gameState, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(gameState, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carica lo stato di un gioco precedentemente salvato da un file JSON.
     * Legge il contenuto del file e lo deserializza in un oggetto GameStateDTO.
     * @param filename Il nome del file da cui leggere i dati.
     * @return Un oggetto GameStateDTO popolato con i dati caricati, oppure null se si verifica un errore di I/O o il file non esiste.
     */
    @Override
    public GameStateDTO loadGame(String filename) {
        try {
            String json = Files.readString(Paths.get(filename));
            return gson.fromJson(json, GameStateDTO.class);
        } catch (IOException e) {
            return null;
        }
    }
}