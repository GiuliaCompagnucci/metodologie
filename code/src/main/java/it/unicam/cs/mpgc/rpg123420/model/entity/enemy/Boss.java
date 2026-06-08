package it.unicam.cs.mpgc.rpg123420.model.entity.enemy;

public class Boss extends Enemy {
    public Boss() {
        super("Drago Boss", 300, 40);
    }

    @Override
    public String specialAbility() {
        return "Il Drago ruggisce, incutendo terrore!";
    }
}