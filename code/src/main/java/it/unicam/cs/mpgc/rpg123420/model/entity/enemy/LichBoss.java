package it.unicam.cs.mpgc.rpg123420.model.entity.enemy;

public class LichBoss extends Enemy {
    public LichBoss() {
        super("Lich Boss", 250, 35);
    }

    public String specialAbility() {
        return "Il Lich ruggisce, incutendo terrore!";
    }
}
