package it.unicam.cs.mpgc.rpg123420.model.service;

import it.unicam.cs.mpgc.rpg123420.model.entity.enemy.Enemy;
import it.unicam.cs.mpgc.rpg123420.model.entity.character.Player;
import it.unicam.cs.mpgc.rpg123420.model.game.Room;

/**
 * Servizio responsabile della gestione della logica di combattimento tra il giocatore e i nemici.
 * Incapsula le regole di calcolo del danno, l'applicazione degli effetti e la generazione dei log di battaglia,
 * mantenendo separata la logica di business dalle entità di gioco e dall'interfaccia utente.
 */
public class CombatService {

    /**
     * Gestisce l'attacco del giocatore verso un nemico specifico.
     * Calcola il danno tramite il metodo polimorfico del player, lo applica al bersaglio
     * e genera un report testuale dell'azione.
     * @param player Il giocatore che esegue l'attacco.
     * @param target Il nemico bersaglio dell'attacco.
     * @return Una stringa di log descrivente l'esito dell'attacco (danno inflitto ed eventuale sconfitta del nemico).
     */
    public String playerAttack(Player player, Enemy target) {
        // Controllo se il combattimento è già finito
        if (!player.isAlive() || !target.isAlive()) {
            return "Combattimento finito.";
        }

        // Calcola il danno usando il polimorfismo (chiama attack() di Warrior o Mage)
        int damage = player.attack(target);

        // APPLICA il danno al nemico (Questo era il passaggio mancante o errato!)
        target.takeDamage(damage);

        // Genera il log
        String log = player.getName() + " infligge " + damage + " danni a " + target.getName() + "!";

        // Controlla se il nemico è morto
        if (!target.isAlive()) {
            log += " " + target.getName() + " è stato sconfitto!";
        }

        return log;
    }

    /**
     * Gestisce il turno di controattacco di tutti i nemici vivi presenti nella stanza.
     * Itera sulla lista dei nemici e applica il loro danno base al giocatore.
     * @param room La stanza corrente contenente i nemici attivi.
     * @param player Il giocatore che subisce gli attacchi.
     * @return Una stringa di log cumulativa descrivente i danni subiti dal giocatore.
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