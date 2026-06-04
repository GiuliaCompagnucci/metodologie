package it.unicam.cs.mpgc.rpg123420.model.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements Combatant {
    private String name;
    private int currentHealth;
    private int maxHealth;
    private List<String> inventory; // Semplificato come lista di stringhe per ora

    public Player(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.inventory = new ArrayList<>();
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

    public void addItem(String item) {
        inventory.add(item);
    }

    public List<String> getInventory() {
        return inventory;
    }

    // Metodo astratto per l'attacco specifico della classe
    public abstract int attack(Combatant target);
}
