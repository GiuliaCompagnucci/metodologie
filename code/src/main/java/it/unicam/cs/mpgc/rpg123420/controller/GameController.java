package it.unicam.cs.mpgc.rpg123420.controller;

import it.unicam.cs.mpgc.rpg123420.model.entity.Enemy;
import it.unicam.cs.mpgc.rpg123420.model.entity.Player;
import it.unicam.cs.mpgc.rpg123420.model.entity.Warrior;
import it.unicam.cs.mpgc.rpg123420.model.game.Dungeon;
import it.unicam.cs.mpgc.rpg123420.model.game.Room;
import it.unicam.cs.mpgc.rpg123420.model.service.CombatService;
import it.unicam.cs.mpgc.rpg123420.persistence.GameSaveData;
import it.unicam.cs.mpgc.rpg123420.persistence.JsonDataStore;

import java.util.List;

public class GameController {
    private Player player;
    private Dungeon dungeon;
    private CombatService combatService;
    private JsonDataStore dataStore;

    public GameController() {
        this.combatService = new CombatService();
        this.dataStore = new JsonDataStore();
        initNewGame();
    }

    private void initNewGame() {
        // Creazione Giocatore
        this.player = new Warrior("Eroe");

        // Creazione Dungeon
        this.dungeon = new Dungeon();

        // Stanza 1
        Room r1 = new Room(1, false);
        r1.addEnemy(new Enemy("Goblin", 50, 10));
        r1.addEnemy(new Enemy("Orco", 80, 15));
        dungeon.addRoom(r1);

        // Stanza 2 (Boss)
        Room r2 = new Room(2, true);
        r2.addEnemy(new Enemy("Drago Boss", 300, 40));
        dungeon.addRoom(r2);
    }

    // Metodi esposti alla View
    public Player getPlayer() { return player; }
    public Dungeon getDungeon() { return dungeon; }

    public List<Enemy> getCurrentEnemies() {
        Room current = dungeon.getCurrentRoom();
        return current != null ? current.getEnemies() : List.of();
    }

    public String attackEnemy(int enemyIndex) {
        Room currentRoom = dungeon.getCurrentRoom();
        if (currentRoom == null) return "Nessuna stanza attiva.";

        List<Enemy> enemies = currentRoom.getEnemies();
        if (enemyIndex < 0 || enemyIndex >= enemies.size()) return "Bersaglio invalido.";

        Enemy target = enemies.get(enemyIndex);

        // Turno Giocatore
        String log = combatService.playerAttack(player, target);

        // Se il giocatore è ancora vivo e ci sono nemici vivi, turno nemici
        if (player.isAlive() && currentRoom.hasLivingEnemies()) {
            log += "\n" + combatService.enemiesTurn(currentRoom, player);
        }

        // Controllo fine stanza
        if (!currentRoom.hasLivingEnemies() && player.isAlive()) {
            log += "\nStanza liberata! Premi Avanti per procedere.";
        }

        return log;
    }

    public void nextRoom() {
        if (dungeon.getCurrentRoom() != null && !dungeon.getCurrentRoom().hasLivingEnemies()) {
            dungeon.nextRoom();
        }
    }

    public void saveGame() {
        dataStore.saveGame(player, dungeon, "savegame.json");
    }

    public void loadGame() {
        GameSaveData data = dataStore.loadGame("savegame.json");
        if (data != null) {
            this.player = data.player;
            this.dungeon = data.dungeon;
        }
    }

    public boolean isGameOver() {
        return !player.isAlive();
    }

    public boolean isVictory() {
        return dungeon.isFinished() && !dungeon.getCurrentRoom().hasLivingEnemies();
    }
}
