package it.unicam.cs.mpgc.rpg123420.model.entity.enemy;

/**
 * Rappresenta un nemico di tipo Goblin, una creatura debole ma numerosa.
 * Estende la classe Enemy definendo statistiche base ridotte (50 HP, 10 Danno)
 * tipiche dei nemici iniziali del dungeon.
 */
public class Goblin extends Enemy {

    /**
     * Costruisce un nuovo Goblin con nome, salute e danno predefiniti.
     * Inizializza il nemico con 50 punti ferita massimi e 10 punti di danno base.
     */
    public Goblin() {
        super("Goblin", 50, 10);
    }
}
