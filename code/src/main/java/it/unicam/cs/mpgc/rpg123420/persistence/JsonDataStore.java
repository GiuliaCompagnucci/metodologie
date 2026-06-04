package it.unicam.cs.mpgc.rpg123420.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.unicam.cs.mpgc.rpg123420.model.entity.Player;
import it.unicam.cs.mpgc.rpg123420.model.game.Dungeon;

import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

// Classe contenitore per salvare tutto insieme
class GameSaveData {
    public Player player;
    public Dungeon dungeon;
}

public class JsonDataStore implements DataStore {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void saveGame(Player player, Dungeon dungeon, String filename) {
        GameSaveData data = new GameSaveData();
        data.player = player;
        data.dungeon = dungeon;

        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GameSaveData loadGame(String filename) {
        try {
            String json = Files.readString(Paths.get(filename));
            return gson.fromJson(json, GameSaveData.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
