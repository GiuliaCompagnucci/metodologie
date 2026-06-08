package it.unicam.cs.mpgc.rpg123420.model.entity.item;

import it.unicam.cs.mpgc.rpg123420.model.entity.character.Player;

public interface Item {
    String getName();
    String getDescription();
    void use(Player player); // L'oggetto viene usato su un giocatore
}
