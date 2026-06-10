package it.unicam.cs.mpgc.rpg123420.model.entity.character;

import it.unicam.cs.mpgc.rpg123420.model.entity.Combatant;

/**
 * Rappresenta la classe del Guerriero, un personaggio specializzato nel combattimento fisico e nella resistenza.
 * Estende la classe Player definendo statistiche base superiori in termini di salute ma inferiori
 * in termini di danno immediato rispetto al Mago, bilanciando la sopravvivenza con la potenza.
 */
public class Warrior extends Player {

    /**
     * Costruisce un nuovo Guerriero con il nome specificato.
     * Inizializza il personaggio con 150 punti ferita massimi, riflettendo la sua alta resistenza fisica.
     * @param name Il nome scelto per il guerriero.
     */
    public Warrior(String name) {
        super(name, 150);
    }

    /**
     * Restituisce il danno base degli attacchi fisici del Guerriero.
     * @return 25, il valore base del danno fisico.
     */
    @Override
    public int getBaseDamage() {
        return 25;
    }

    /**
     * Esegue un attacco fisico contro il bersaglio specificato.
     * Il danno totale è dato dal danno base (25) sommato agli eventuali bonus attivi (es. da pozioni).
     * Stampa un messaggio descrittivo dell'azione sulla console.
     * @param target Il combattente nemico da colpire.
     * @return Il valore totale del danno inflitto.
     */
    @Override
    public int attack(Combatant target) {
        System.out.println(getName() + " colpisce con la spada!");
        return 25 + getBonusDamage(); // Danno fisico
    }
}
