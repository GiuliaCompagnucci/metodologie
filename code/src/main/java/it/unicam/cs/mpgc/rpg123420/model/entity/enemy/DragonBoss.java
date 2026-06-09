package it.unicam.cs.mpgc.rpg123420.model.entity.enemy;

public class DragonBoss extends Enemy {
    public DragonBoss() {
        super("Drago Boss", 300, 40);
    }

    @Override
    public String specialAbility() {
        return "Il Drago ruggisce, incutendo terrore!";
    }
}