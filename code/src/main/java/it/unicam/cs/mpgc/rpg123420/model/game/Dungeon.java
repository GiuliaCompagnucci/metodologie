package it.unicam.cs.mpgc.rpg123420.model.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta la struttura del Dungeon, composta da una sequenza ordinata di Stanze (Room).
 * Gestisce la navigazione del giocatore attraverso le stanze e determina lo stato di completamento dell'avventura.
 */
public class Dungeon {
    private List<Room> rooms;
    private int currentRoomIndex;

    /**
     * Costruisce un nuovo Dungeon vuoto.
     * Inizializza la lista delle stanze e imposta l'indice corrente alla prima stanza (0).
     */
    public Dungeon() {
        this.rooms = new ArrayList<>();
        this.currentRoomIndex = 0;
    }

    /**
     * Aggiunge una nuova stanza alla fine della sequenza del dungeon.
     * @param room La stanza da aggiungere al dungeon.
     */
    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    /**
     * Restituisce la stanza in cui si trova attualmente il giocatore.
     * @return L'oggetto Room corrente, oppure null se l'indice non è valido o il dungeon è vuoto.
     */
    public Room getCurrentRoom() {
        if (currentRoomIndex >= 0 && currentRoomIndex < rooms.size()) {
            return rooms.get(currentRoomIndex);
        }
        return null;
    }

    /**
     * Sposta il giocatore alla stanza successiva, se presente.
     * Incrementa l'indice corrente solo se non si è già nell'ultima stanza.
     */
    public void nextRoom() {
        if (currentRoomIndex < rooms.size() - 1) {
            currentRoomIndex++;
        }
    }

    /**
     * Verifica se il dungeon è stato completato con successo.
     * Il dungeon è considerato finito se il giocatore si trova nell'ultima stanza
     * e tutti i nemici presenti in essa sono stati sconfitti.
     * @return true se il dungeon è completato, false altrimenti.
     */
    public boolean isFinished() {
        // Finito se siamo all'ultima stanza e non ci sono nemici vivi
        if (rooms.isEmpty()) return true;
        Room lastRoom = rooms.get(rooms.size() - 1);
        return currentRoomIndex == rooms.size() - 1 && !lastRoom.hasLivingEnemies();
    }

    /**
     * Restituisce l'indice numerico della stanza corrente (base 0).
     * @return L'indice della stanza attuale.
     */
    public int getCurrentRoomIndex() {
        return currentRoomIndex;
    }

    /**
     * Restituisce il numero totale di stanze che compongono il dungeon.
     * @return La dimensione della lista delle stanze.
     */
    public int getTotalRooms() {
        return rooms.size();
    }
}