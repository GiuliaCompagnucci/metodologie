package it.unicam.cs.mpgc.rpg123420.persistence.dto;

import it.unicam.cs.mpgc.rpg123420.model.entity.character.Player;
import it.unicam.cs.mpgc.rpg123420.model.game.Dungeon;

/**
 * Data Transfer Object (DTO) utilizzato per la serializzazione e deserializzazione dello stato di gioco.
 * Incapsula le entità principali (Player e Dungeon) in una struttura piatta adatta alla persistenza su file JSON,
 * separando il modello di dominio dalla logica di storage.
 */
public class GameStateDTO {
    private Player player;
    private Dungeon dungeon;
    private String saveDate;

    /**
     * Costruttore vuoto richiesto da Gson per la deserializzazione.
     */
    public GameStateDTO() {}

    /**
     * Costruisce un nuovo DTO contenente lo stato attuale del giocatore e del dungeon.
     * Registra automaticamente la data e l'ora del salvataggio.
     * @param player L'istanza corrente del giocatore da salvare.
     * @param dungeon L'istanza corrente del dungeon da salvare.
     */
    public GameStateDTO(Player player, Dungeon dungeon) {
        this.player = player;
        this.dungeon = dungeon;
        this.saveDate = java.time.LocalDateTime.now().toString();
    }

    /**
     * Restituisce il giocatore salvato nello stato.
     * @return L'oggetto Player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Imposta il giocatore nello stato salvato.
     * Utilizzato durante il caricamento da file.
     * @param player L'oggetto Player da impostare.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Restituisce il dungeon salvato nello stato.
     * @return L'oggetto Dungeon.
     */
    public Dungeon getDungeon() {
        return dungeon;
    }

    /**
     * Imposta il dungeon nello stato salvato.
     * Utilizzato durante il caricamento da file.
     * @param dungeon L'oggetto Dungeon da impostare.
     */
    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    /**
     * Restituisce la data e l'ora in cui è stato effettuato il salvataggio.
     * @return Una stringa rappresentante il timestamp del salvataggio.
     */
    public String getSaveDate() {
        return saveDate;
    }
}
