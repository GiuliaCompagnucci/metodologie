package it.unicam.cs.mpgc.rpg123420.model.service;

import it.unicam.cs.mpgc.rpg123420.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg123420.model.entity.Player;
import it.unicam.cs.mpgc.rpg123420.model.game.Room;

public class CombatService {

    // Il giocatore attacca un nemico specifico
    public String playerAttack(Player player, Enemy target) {
        if (!player.isAlive() || !target.isAlive()) return "Combattimento finito.";

        int damage = player.attack(target);
        String log = player.getName() + " infligge " + damage + " danni a " + target.getName() + "!";

        if (!target.isAlive()) {
            log += " " + target.getName() + " è stato sconfitto!";
        }
        return log;
    }

    // Tutti i nemici vivi attaccano il giocatore
    public String enemiesTurn(Room room, Player player) {
        if (!player.isAlive()) return "Sei stato sconfitto!";

        StringBuilder log = new StringBuilder();
        for (Enemy enemy : room.getEnemies()) {
            if (enemy.isAlive()) {
                int damage = enemy.getDamage();
                player.takeDamage(damage);
                log.append(enemy.getName()).append(" ti infligge ").append(damage).append(" danni. ");
            }
        }

        if (!player.isAlive()) {
            log.append(" Sei morto! Game Over.");
        }
        return log.toString();
    }
}
