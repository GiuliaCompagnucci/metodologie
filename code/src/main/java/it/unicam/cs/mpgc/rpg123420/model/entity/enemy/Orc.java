package it.unicam.cs.mpgc.rpg123420.model.entity.enemy;

/**
 * Rappresenta un nemico di tipo Orco, una creatura più resistente e pericolosa del Goblin.
 * Estende la classe Enemy definendo statistiche medie (80 HP, 15 Danno)
 * tipiche dei nemici che si incontrano nelle stanze centrali del dungeon.
 */
public class Orc extends Enemy {

    /**
     * Costruisce un nuovo Orco con nome, salute e danno predefiniti.
     * Inizializza il nemico con 80 punti ferita massimi e 15 punti di danno base.
     */
    public Orc() {
        super("Orco", 80, 15);
    }
}