package it.unicam.cs.mpgc.rpg123420.persistence;

import it.unicam.cs.mpgc.rpg123420.persistence.dto.GameStateDTO;

/**
 * Interfaccia che definisce il contratto per i meccanismi di persistenza dei dati di gioco.
 * Permette di astrarre l'implementazione specifica del salvataggio (es. JSON, XML, Database),
 * rispettando il principio di Dependency Inversion e facilitando future estensioni del sistema di storage.
 */
public interface DataStore {

    /**
     * Salva lo stato corrente del gioco su un file specifico.
     * Serializza il DTO contenente i dati del giocatore e del dungeon.
     * @param gameState L'oggetto DTO contenente i dati da serializzare e salvare.
     * @param filename Il nome del file di destinazione per il salvataggio.
     */
    void saveGame(GameStateDTO gameState, String filename);

    /**
     * Carica lo stato di un gioco precedentemente salvato da un file.
     * Deserializza i dati dal file specificato e li restituisce in un DTO.
     * @param filename Il nome del file da cui leggere i dati salvati.
     * @return Un oggetto GameStateDTO contenente i dati caricati, oppure null in caso di errore o file mancante.
     */
    GameStateDTO loadGame(String filename);
}