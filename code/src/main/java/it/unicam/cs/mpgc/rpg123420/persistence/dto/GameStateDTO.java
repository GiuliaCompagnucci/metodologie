package it.unicam.cs.mpgc.rpg123420.persistence.dto;

import it.unicam.cs.mpgc.rpg123420.model.entity.Player;
import it.unicam.cs.mpgc.rpg123420.model.game.Dungeon;

/**
 * Data Transfer Object per la persistenza dello stato di gioco.
 * Contiene tutte le informazioni necessarie per salvare e ricaricare una partita.
 */
public class GameStateDTO {

    private Player player;
    private Dungeon dungeon;

    // Costruttore vuoto necessario per Gson
    public GameStateDTO() {
    }

    public GameStateDTO(Player player, Dungeon dungeon) {
        this.player = player;
        this.dungeon = dungeon;
    }

    // Getter e Setter
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

}
