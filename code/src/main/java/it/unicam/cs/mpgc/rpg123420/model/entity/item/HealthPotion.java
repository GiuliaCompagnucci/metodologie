package it.unicam.cs.mpgc.rpg123420.model.entity.item;

import it.unicam.cs.mpgc.rpg123420.model.entity.character.Player;

/**
 * Implementazione concreta dell'interfaccia Item che rappresenta una pozione curativa.
 * Quando utilizzata, ripristina una quantità specifica di punti ferita al giocatore.
 */
public class HealthPotion implements Item {
    private int healAmount;

    /**
     * Costruisce una nuova pozione di cura.
     * @param healAmount La quantità di punti vita da ripristinare all'uso.
     */
    public HealthPotion(int healAmount) {
        this.healAmount = healAmount;
    }

    /**
     * Restituisce il nome dell'oggetto.
     * @return "Pozione di Cura".
     */
    @Override
    public String getName() {
        return "Pozione di Cura";
    }

    /**
     * Restituisce la descrizione dell'effetto della pozione.
     * @return Una stringa che indica quanti punti vita vengono ripristinati.
     */
    @Override
    public String getDescription() {
        return "Ripristina " + healAmount + " punti vita.";
    }

    /**
     * Applica l'effetto curativo sul giocatore specificato.
     * Incrementa i punti ferita attuali del giocatore fino al massimo consentito.
     * @param player Il giocatore che utilizza la pozione.
     */
    @Override
    public void use(Player player) {
        player.heal(healAmount);
    }
}