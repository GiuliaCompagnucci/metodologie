package it.unicam.cs.mpgc.rpg123420.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.unicam.cs.mpgc.rpg123420.persistence.dto.GameStateDTO;

import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonDataStore implements DataStore {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void saveGame(GameStateDTO gameState, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(gameState, writer);
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public GameStateDTO loadGame(String filename) {
        try {
            String json = Files.readString(Paths.get(filename));
            return gson.fromJson(json, GameStateDTO.class);
        } catch (IOException e) {
            System.err.println("Errore durante il caricamento: " + e.getMessage());
            return null;
        }
    }
}