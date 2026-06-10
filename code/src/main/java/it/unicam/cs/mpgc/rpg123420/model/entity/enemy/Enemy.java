package it.unicam.cs.mpgc.rpg123420.model.entity.enemy;

import it.unicam.cs.mpgc.rpg123420.model.entity.Combatant;

/**
 * Classe astratta che rappresenta un nemico generico all'interno del Dungeon.
 * Implementa l'interfaccia Combatant definendo le proprietà comuni a tutti i tipi di avversari
 * (come Goblin, Orchi o Boss), tra cui salute, danno e stato di sopravvivenza.
 * Fornisce la base per l'estendibilità attraverso sottoclassi concrete.
 */
public abstract class Enemy implements Combatant {
    private String name;
    private int currentHealth;
    private int maxHealth;
    private int damage;

    /**
     * Costruisce un nuovo nemico con le statistiche specificate.
     * Inizializza la salute corrente al valore massimo.
     * @param name Il nome identificativo del nemico.
     * @param maxHealth I punti ferita massimi del nemico.
     * @param damage Il danno base inflitto dal nemico agli attacchi.
     */
    public Enemy(String name, int maxHealth, int damage) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.damage = damage;
    }

    /**
     * Restituisce il nome del nemico.
     * @return Il nome identificativo dell'entità nemica.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Restituisce i punti ferita attuali del nemico.
     * @return Il valore corrente degli HP.
     */
    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Restituisce i punti ferita massimi del nemico.
     * @return Il valore massimo degli HP definibile per questo tipo di nemico.
     */
    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Sottrae una quantità di punti ferita dalla salute corrente del nemico.
     * Garantisce che la salute non scenda mai sotto lo zero.
     * @param amount La quantità di danno subìto.
     */
    @Override
    public void takeDamage(int amount) {
        this.currentHealth = Math.max(0, this.currentHealth - amount);
    }

    /**
     * Verifica se il nemico è ancora in vita.
     * @return true se i punti ferita attuali sono maggiori di zero.
     */
    @Override
    public boolean isAlive() {
        return currentHealth > 0;
    }

    /**
     * Restituisce il valore del danno base inflitto dal nemico.
     * @return L'ammontare del danno.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Metodo opzionale che può essere sovrascritto dalle sottoclassi per definire abilità speciali.
     * Di default restituisce una stringa vuota.
     * @return Una descrizione dell'abilità speciale, se presente.
     */
    public String specialAbility() {
        return "";
    }

    /**
     * Imposta manualmente le statistiche del nemico.
     * Utilizzato principalmente durante il caricamento di una partita salvata per ripristinare lo stato esatto.
     * @param name Il nome da impostare.
     * @param currentHealth La salute corrente da impostare.
     * @param maxHealth La salute massima da impostare.
     * @param damage Il danno da impostare.
     */
    public void setStats(String name, int currentHealth, int maxHealth, int damage) {
        this.name = name;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.damage = damage;
    }
}