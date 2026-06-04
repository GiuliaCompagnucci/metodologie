package it.unicam.cs.mpgc.rpg123420.model.entity;

public interface Combatant {
    String getName();
    int getCurrentHealth();
    int getMaxHealth();
    void takeDamage(int amount);
    boolean isAlive();
}
