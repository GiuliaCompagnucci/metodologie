package it.unicam.cs.mpgc.rpg123420.persistence;

import it.unicam.cs.mpgc.rpg123420.persistence.dto.GameStateDTO;

public interface DataStore {
    /**
     * Salva lo stato di gioco in un file.
     * @param gameState DTO contenente i dati da salvare
     * @param filename nome del file
     */
    void saveGame(GameStateDTO gameState, String filename);

    /**
     * Carica lo stato di gioco da un file.
     * @param filename nome del file
     * @return GameStateDTO caricato, o null se errore
     */
    GameStateDTO loadGame(String filename);
}