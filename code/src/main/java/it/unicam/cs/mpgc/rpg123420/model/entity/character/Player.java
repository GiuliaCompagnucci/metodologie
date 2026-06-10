package it.unicam.cs.mpgc.rpg123420.model.entity.character;

import it.unicam.cs.mpgc.rpg123420.model.entity.Combatant;
import it.unicam.cs.mpgc.rpg123420.model.entity.item.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class Player implements Combatant {
    private String name;
    private int currentHealth;
    private int maxHealth;
    private int bonusDamage;
    private List<Item> inventory;

    public Player(String name, int maxHealth) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.bonusDamage = 0;
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

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public void removeItem(Item item) {
        this.inventory.remove(item);
    }

    // Metodo per curarsi
    public void heal(int amount) {
        this.currentHealth = Math.min(this.maxHealth, this.currentHealth + amount);
    }

    public void addBonusDamage(int amount) {
        this.bonusDamage += amount;
    }

    public int getBonusDamage() {
        return bonusDamage;
    }

    // Metodo astratto per l'attacco specifico della classe
    public abstract int attack(Combatant target);

    public abstract int getBaseDamage();

    public void setStats(int currentHealth, int maxHealth, int bonusDamage) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.bonusDamage = bonusDamage;
    }
}
