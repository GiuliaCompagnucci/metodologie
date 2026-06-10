package it.unicam.cs.mpgc.rpg123420.model.game;

import it.unicam.cs.mpgc.rpg123420.model.entity.enemy.Enemy;
import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta una singola stanza all'interno del Dungeon.
 * Ogni stanza contiene un elenco di nemici da sconfiggere e può essere contrassegnata
 * come stanza del Boss finale. Gestisce lo stato dei combattimenti interni alla stanza.
 */
public class Room {
    private int roomNumber;
    private List<Enemy> enemies;
    private boolean isBossRoom;

    /**
     * Costruisce una nuova stanza.
     * @param roomNumber Il numero identificativo della stanza nel dungeon.
     * @param isBossRoom true se questa è la stanza finale contenente il Boss, false altrimenti.
     */
    public Room(int roomNumber, boolean isBossRoom) {
        this.roomNumber = roomNumber;
        this.isBossRoom = isBossRoom;
        this.enemies = new ArrayList<>();
    }

    /**
     * Aggiunge un nemico alla lista dei presenti nella stanza.
     * @param enemy L'istanza del nemico da aggiungere al combattimento.
     */
    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    /**
     * Restituisce la lista completa di tutti i nemici presenti nella stanza (vivi o morti).
     * @return La lista degli oggetti Enemy.
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Verifica se ci sono ancora nemici vivi nella stanza.
     * Utilizza uno stream per controllare lo stato di ogni nemico.
     * @return true se almeno un nemico è ancora in vita, false se tutti sono stati sconfitti.
     */
    public boolean hasLivingEnemies() {
        return enemies.stream().anyMatch(Enemy::isAlive);
    }

    /**
     * Restituisce il numero identificativo della stanza.
     * @return L'indice numerico della stanza.
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Indica se la stanza corrente è quella del Boss finale.
     * @return true se è la stanza del Boss, false altrimenti.
     */
    public boolean isBossRoom() {
        return isBossRoom;
    }
}