package it.unicam.cs.mpgc.rpg123420.model.entity.character;

import it.unicam.cs.mpgc.rpg123420.model.entity.Combatant;
import it.unicam.cs.mpgc.rpg123420.model.entity.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe astratta che rappresenta il personaggio controllato dal giocatore (Eroe).
 * Implementa l'interfaccia Combatant definendo le proprietà comuni a tutte le classi di personaggi
 * (come Guerriero o Mago), tra cui salute, inventario e danni.
 * Fornisce la struttura base per la gestione dello stato vitale e degli oggetti equipaggiati o consumabili.
 */
public abstract class Player implements Combatant {
    private String name;
    private int currentHealth;
    private int maxHealth;
    private int bonusDamage; // Bonus al danno accumulato tramite oggetti o abilità
    private List<Item> inventory;

    /**
     * Costruisce un nuovo giocatore con nome e salute massima specificati.
     * Inizializza la salute corrente al massimo e l'inventario vuoto.
     * @param name Il nome scelto per l'eroe.
     * @param maxHealth I punti ferita massimi iniziali della classe.
     */
    public Player(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.bonusDamage = 0;
        this.inventory = new ArrayList<>();
    }

    /**
     * Restituisce il nome del giocatore.
     * @return Il nome dell'eroe.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Restituisce i punti ferita attuali del giocatore.
     * @return Il valore corrente degli HP.
     */
    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Restituisce i punti ferita massimi del giocatore.
     * @return Il valore massimo degli HP definibile per questa classe.
     */
    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Sottrae una quantità di punti ferita dalla salute corrente.
     * Garantisce che la salute non scenda mai sotto lo zero.
     * @param amount La quantità di danno subìto.
     */
    @Override
    public void takeDamage(int amount) {
        this.currentHealth = Math.max(0, this.currentHealth - amount);
    }

    /**
     * Verifica se il giocatore è ancora in vita.
     * @return true se i punti ferita attuali sono maggiori di zero.
     */
    @Override
    public boolean isAlive() {
        return currentHealth > 0;
    }

    /**
     * Aggiunge un oggetto all'inventario del giocatore.
     * @param item L'oggetto da raccogliere.
     */
    public void addItem(Item item) {
        this.inventory.add(item);
    }

    /**
     * Restituisce la lista completa degli oggetti nell'inventario.
     * @return La lista di Item posseduti.
     */
    public List<Item> getInventory() {
        return inventory;
    }

    /**
     * Imposta manualmente l'intero inventario.
     * Utilizzato principalmente durante il caricamento di una partita salvata.
     * @param inventory La nuova lista di oggetti.
     */
    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    /**
     * Rimuove un oggetto specifico dall'inventario (es. dopo l'utilizzo).
     * @param item L'oggetto da rimuovere.
     */
    public void removeItem(Item item) {
        this.inventory.remove(item);
    }

    /**
     * Ripristina una quantità di punti ferita, senza superare il massimo consentito.
     * @param amount La quantità di vita da recuperare.
     */
    public void heal(int amount) {
        this.currentHealth = Math.min(this.maxHealth, this.currentHealth + amount);
    }

    /**
     * Aumenta il bonus di danno permanente del giocatore.
     * @param amount La quantità di danno aggiuntivo da sommare.
     */
    public void addBonusDamage(int amount) {
        this.bonusDamage += amount;
    }

    /**
     * Restituisce il bonus di danno attuale.
     * @return Il valore del bonus di danno.
     */
    public int getBonusDamage() {
        return bonusDamage;
    }

    /**
     * Metodo astratto che definisce l'azione di attacco specifica per la sottoclasse.
     * Ogni classe (Warrior, Mage) implementerà la propria logica di calcolo del danno.
     * @param target Il bersaglio dell'attacco.
     * @return Il valore del danno inflitto.
     */
    public abstract int attack(Combatant target);

    /**
     * Metodo astratto che restituisce il danno base della classe del personaggio.
     * @return Il valore del danno base senza bonus.
     */
    public abstract int getBaseDamage();

    /**
     * Imposta manualmente le statistiche vitali e di danno del giocatore.
     * Utilizzato principalmente durante il caricamento di una partita salvata per ripristinare lo stato esatto.
     * @param currentHealth Salute corrente da impostare.
     * @param maxHealth Salute massima da impostare.
     * @param bonusDamage Bonus di danno da impostare.
     */
    public void setStats(int currentHealth, int maxHealth, int bonusDamage) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.bonusDamage = bonusDamage;
    }
}
