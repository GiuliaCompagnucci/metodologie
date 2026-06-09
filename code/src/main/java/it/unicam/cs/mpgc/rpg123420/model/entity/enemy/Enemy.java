package it.unicam.cs.mpgc.rpg123420.model.entity.enemy;

import it.unicam.cs.mpgc.rpg123420.model.entity.Combatant;

public abstract class Enemy implements Combatant {
    private String name;
    private int currentHealth;
    private int maxHealth;
    private int damage;

    public Enemy(String name, int maxHealth, int damage) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.damage = damage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCurrentHealth() {
        return currentHealth;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void takeDamage(int amount) {
        this.currentHealth = Math.max(0, this.currentHealth - amount);
    }

    @Override
    public boolean isAlive() {
        return currentHealth > 0;
    }

    public int getDamage() {
        return damage;
    }

    // Metodo opzionale per comportamenti speciali (es. abilità del boss)
    public String specialAbility() {
        return "";
    }

    public void setStats(String name, int currentHealth, int maxHealth, int damage) {
        this.name = name;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.damage = damage;
    }
}