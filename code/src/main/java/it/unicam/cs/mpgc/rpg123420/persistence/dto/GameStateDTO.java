package it.unicam.cs.mpgc.rpg123420.persistence.dto;

import it.unicam.cs.mpgc.rpg123420.model.entity.character.Player;
import it.unicam.cs.mpgc.rpg123420.model.game.Dungeon;

/**
 * Data Transfer Object per la persistenza dello stato di gioco.
 * Contiene tutte le informazioni necessarie per salvare e ricaricare una partita.
 */
public class GameStateDTO {
    private Player player;
    private Dungeon dungeon;
    private String saveDate;

    public GameStateDTO() {}

    public GameStateDTO(Player player, Dungeon dungeon) {
        this.player = player;
        this.dungeon = dungeon;
        this.saveDate = java.time.LocalDateTime.now().toString();
    }

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

    public String getSaveDate() {
        return saveDate;
    }
}
