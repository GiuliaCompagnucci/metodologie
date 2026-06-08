package it.unicam.cs.mpgc.rpg123420.model.entity.character;

import it.unicam.cs.mpgc.rpg123420.model.entity.Combatant;

public class Mage extends Player {
    public Mage(String name) {
        super(name, 80);
    }

    @Override
    public int attack(Combatant target) {
        System.out.println(getName() + " lancia una palla di fuoco!");
        return 40; // Danno magico alto
    }
}