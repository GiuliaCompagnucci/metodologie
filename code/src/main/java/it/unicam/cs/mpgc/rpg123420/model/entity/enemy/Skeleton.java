package it.unicam.cs.mpgc.rpg123420.model.entity.enemy;

/**
 * Rappresenta un nemico di tipo Skeleton (Scheletro), una creatura non-morta animata da magia oscura.
 * Estende la classe Enemy definendo statistiche intermedie (60 HP, 12 Danno)
 * che lo rendono una sfida leggermente superiore al Goblin ma inferiore all'Orco.
 */
public class Skeleton extends Enemy {

    /**
     * Costruisce un nuovo Skeleton con nome, salute e danno predefiniti.
     * Inizializza il nemico con 60 punti ferita massimi e 12 punti di danno base.
     */
    public Skeleton() {
        super("Skeleton", 60, 12);
    }
}
