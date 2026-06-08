package it.unicam.cs.mpgc.rpg123420.model.service;

import it.unicam.cs.mpgc.rpg123420.model.entity.enemy.Enemy;
import it.unicam.cs.mpgc.rpg123420.model.entity.character.Player;
import it.unicam.cs.mpgc.rpg123420.model.game.Room;

public class CombatService {

    /**
     * Gestisce l'attacco del giocatore verso un nemico specifico.
     * @param player Il giocatore che attacca
     * @param target Il nemico bersaglio
     * @return Una stringa di log descrivente l'azione
     */
    public String playerAttack(Player player, Enemy target) {
        // Controllo se il combattimento è già finito
        if (!player.isAlive() || !target.isAlive()) {
            return "Combattimento finito.";
        }

        // 1. Calcola il danno usando il polimorfismo (chiama attack() di Warrior o Mage)
        int damage = player.attack(target);

        // 2. APPLICA il danno al nemico (Questo era il passaggio mancante o errato!)
        target.takeDamage(damage);

        // 3. Genera il log
        String log = player.getName() + " infligge " + damage + " danni a " + target.getName() + "!";

        // 4. Controlla se il nemico è morto
        if (!target.isAlive()) {
            log += " " + target.getName() + " è stato sconfitto!";
        }

        return log;
    }

    /**
     * Gestisce il turno di tutti i nemici vivi nella stanza.
     * @param room La stanza corrente
     * @param player Il giocatore
     * @return Una stringa di log descrivente le azioni dei nemici
     */
    public String enemiesTurn(Room room, Player player) {
        if (!player.isAlive()) {
            return "Sei stato sconfitto!";
        }

        StringBuilder log = new StringBuilder();

        // Ogni nemico vivo attacca il giocatore
        for (Enemy enemy : room.getEnemies()) {
            if (enemy.isAlive()) {
                int damage = enemy.getDamage();
                player.takeDamage(damage); // Applica danno al player
                log.append(enemy.getName()).append(" ti infligge ").append(damage).append(" danni. ");
            }
        }

        if (!player.isAlive()) {
            log.append("\nSei morto! Game Over.");
        }

        return log.toString();
    }
}
