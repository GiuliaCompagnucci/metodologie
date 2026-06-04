package it.unicam.cs.mpgc.rpg123420.persistence;

import it.unicam.cs.mpgc.rpg123420.model.entity.Player;
import it.unicam.cs.mpgc.rpg123420.model.game.Dungeon;

public interface DataStore {
    void saveGame(Player player, Dungeon dungeon, String filename);
    GameStateDTO loadGame(String filename);
}