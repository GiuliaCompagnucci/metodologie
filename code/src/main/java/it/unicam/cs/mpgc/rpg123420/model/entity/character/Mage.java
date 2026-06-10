package it.unicam.cs.mpgc.rpg123420.model.entity.character;

import it.unicam.cs.mpgc.rpg123420.model.entity.Combatant;

/**
 * Rappresenta la classe del Mago, un personaggio specializzato in attacchi magici ad alto danno.
 * Estende la classe Player definendo statistiche base inferiori in termini di salute ma superiori
 * in termini di potenza offensiva rispetto ad altre classi.
 */
public class Mage extends Player {

    /**
     * Costruisce un nuovo Mago con il nome specificato.
     * Inizializza il personaggio con 80 punti ferita massimi, riflettendo la sua natura fragile.
     * @param name Il nome scelto per il mago.
     */
    public Mage(String name) {
        super(name, 80);
    }

    /**
     * Restituisce il danno base degli attacchi magici del Mago.
     * @return 40, il valore base del danno magico.
     */
    @Override
    public int getBaseDamage() {
        return 40;
    }

    /**
     * Esegue un attacco magico contro il bersaglio specificato.
     * Il danno totale è dato dal danno base (40) sommato agli eventuali bonus attivi.
     * Stampa un messaggio descrittivo dell'azione sulla console.
     * @param target Il combattente nemico da colpire.
     * @return Il valore totale del danno inflitto.
     */
    @Override
    public int attack(Combatant target) {
        System.out.println(getName() + " lancia una palla di fuoco!");
        return 40 + getBonusDamage(); // Danno magico alto
    }
}