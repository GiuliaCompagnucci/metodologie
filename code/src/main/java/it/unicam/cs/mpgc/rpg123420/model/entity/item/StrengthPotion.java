package it.unicam.cs.mpgc.rpg123420.model.entity.item;

import it.unicam.cs.mpgc.rpg123420.model.entity.character.Player;

/**
 * Implementazione concreta dell'interfaccia Item che rappresenta una pozione di potenziamento fisico.
 * Quando utilizzata, aumenta permanentemente (per la durata della partita) il danno inflitto dal giocatore.
 */
public class StrengthPotion implements Item {
    private int damageBoost;

    /**
     * Costruisce una nuova pozione di forza.
     * @param damageBoost La quantità di punti danno da aggiungere agli attacchi del giocatore.
     */
    public StrengthPotion(int damageBoost) {
        this.damageBoost = damageBoost;
    }

    /**
     * Restituisce il nome dell'oggetto.
     * @return "Pozione di Forza".
     */
    @Override
    public String getName() {
        return "Pozione di Forza";
    }

    /**
     * Restituisce la descrizione dell'effetto della pozione.
     * @return Una stringa che indica di quanto viene aumentato il danno.
     */
    @Override
    public String getDescription() {
        return "Aumenta il danno di " + damageBoost + " punti.";
    }

    /**
     * Applica l'effetto di potenziamento sul giocatore specificato.
     * Incrementa il bonus di danno attuale del giocatore, influenzando tutti i suoi futuri attacchi.
     * @param player Il giocatore che utilizza la pozione.
     */
    @Override
    public void use(Player player) {
        player.addBonusDamage(damageBoost);
    }
}