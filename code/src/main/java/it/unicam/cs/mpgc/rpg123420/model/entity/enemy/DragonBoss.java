package it.unicam.cs.mpgc.rpg123420.model.entity.enemy;

/**
 * Rappresenta il Boss finale sotto forma di Drago.
 * Estende la classe Enemy definendo statistiche elevate (300 HP, 40 Danno)
 * e implementando un'abilità speciale descrittiva.
 */
public class DragonBoss extends Enemy {

    /**
     * Costruisce un nuovo Drago Boss con nome, salute e danno predefiniti.
     * Inizializza il nemico con 300 punti ferita massimi e 40 punti di danno base.
     */
    public DragonBoss() {
        super("Drago Boss", 300, 40);
    }

    /**
     * Restituisce la descrizione dell'abilità speciale del Drago.
     * Questa abilità rappresenta un effetto narrativo o di stato durante il combattimento.
     * @return Una stringa che descrive l'azione intimidatoria del Drago.
     */
    @Override
    public String specialAbility() {
        return "Il Drago ruggisce, incutendo terrore!";
    }
}