package it.unicam.cs.mpgc.rpg123420.model.entity.item;

import it.unicam.cs.mpgc.rpg123420.model.entity.character.Player;

public class HealthPotion implements Item {
    private int healAmount;

    public HealthPotion(int healAmount) {
        this.healAmount = healAmount;
    }

    @Override
    public String getName() {
        return "Pozione di Cura";
    }

    @Override
    public String getDescription() {
        return "Ripristina " + healAmount + " punti vita.";
    }

    @Override
    public void use(Player player) {
        player.heal(healAmount);
    }
}