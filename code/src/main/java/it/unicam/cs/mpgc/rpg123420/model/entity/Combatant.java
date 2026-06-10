package it.unicam.cs.mpgc.rpg123420.model.entity;

/**
 * Interfaccia comune che definisce il contratto per tutte le entità in grado di partecipare a un combattimento.
 * Implementata da Player ed Enemy, permette al sistema di gestire polimorficamente eroi e nemici
 * durante le fasi di attacco, calcolo dei danni e verifica dello stato di sopravvivenza.
 */
public interface Combatant {

    /**
     * Restituisce il nome identificativo del combattente.
     * @return Il nome dell'entità.
     */
    String getName();

    /**
     * Restituisce i punti ferita attuali del combattente.
     * @return Il valore attuale degli HP.
     */
    int getCurrentHealth();

    /**
     * Restituisce i punti ferita massimi del combattente.
     * Utile per calcolare percentuali di vita o per interfacce grafiche (es. barre della vita).
     * @return Il valore massimo degli HP.
     */
    int getMaxHealth();

    /**
     * Applica una quantità di danno al combattente, riducendo i suoi punti ferita attuali.
     * La logica interna deve garantire che gli HP non scendano sotto lo zero.
     * @param amount La quantità di danno da sottrarre.
     */
    void takeDamage(int amount);

    /**
     * Verifica se il combattente è ancora in vita.
     * @return true se i punti ferita attuali sono maggiori di zero, false altrimenti.
     */
    boolean isAlive();
}
