package it.unicam.cs.mpgc.rpg123420.model.entity.character;

import it.unicam.cs.mpgc.rpg123420.model.entity.Combatant;

public class Warrior extends Player {
    public Warrior(String name) {
        super(name, 150);
    }

    @Override
    public int attack(Combatant target) {
        System.out.println(getName() + " colpisce con la spada!");
        return 25 + getBonusDamage(); // Danno fisico
    }
}
