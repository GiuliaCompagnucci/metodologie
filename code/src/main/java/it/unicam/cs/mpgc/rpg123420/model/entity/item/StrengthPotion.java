package it.unicam.cs.mpgc.rpg123420.model.entity.item;

import it.unicam.cs.mpgc.rpg123420.model.entity.character.Player;

public class StrengthPotion implements Item {
    private int damageBoost;

    public StrengthPotion(int damageBoost) {
        this.damageBoost = damageBoost;
    }

    @Override
    public String getName() {
        return "Pozione di Forza";
    }

    @Override
    public String getDescription() {
        return "Aumenta il danno di " + damageBoost + " punti.";
    }

    @Override
    public void use(Player player) {
        player.addBonusDamage(damageBoost);
    }
}
