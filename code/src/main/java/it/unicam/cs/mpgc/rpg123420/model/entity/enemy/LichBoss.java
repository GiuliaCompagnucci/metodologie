package it.unicam.cs.mpgc.rpg123420.model.entity.enemy;

/**
 * Rappresenta un Boss di tipo Lich, un potente negromante non-morto.
 * Estende la classe Enemy definendo statistiche elevate (250 HP, 35 Danno)
 * e implementando un'abilità speciale descrittiva.
 */
public class LichBoss extends Enemy {

    /**
     * Costruisce un nuovo Lich Boss con nome, salute e danno predefiniti.
     * Inizializza il nemico con 250 punti ferita massimi e 35 punti di danno base.
     */
    public LichBoss() {
        super("Lich Boss", 250, 35);
    }

    /**
     * Restituisce la descrizione dell'abilità speciale del Lich.
     * Questa abilità rappresenta un effetto narrativo o di stato durante il combattimento.
     * @return Una stringa che descrive l'azione intimidatoria del Lich.
     */
    @Override
    public String specialAbility() {
        return "Il Lich ruggisce, incutendo terrore!";
    }
}
