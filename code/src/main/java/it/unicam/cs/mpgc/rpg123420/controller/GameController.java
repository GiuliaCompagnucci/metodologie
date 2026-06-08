package it.unicam.cs.mpgc.rpg123420.controller;

import it.unicam.cs.mpgc.rpg123420.model.entity.character.Mage;
import it.unicam.cs.mpgc.rpg123420.model.entity.character.Player;
import it.unicam.cs.mpgc.rpg123420.model.entity.character.Warrior;
import it.unicam.cs.mpgc.rpg123420.model.entity.enemy.Boss;
import it.unicam.cs.mpgc.rpg123420.model.entity.enemy.Enemy;
import it.unicam.cs.mpgc.rpg123420.model.entity.enemy.Goblin;
import it.unicam.cs.mpgc.rpg123420.model.entity.enemy.Orc;
import it.unicam.cs.mpgc.rpg123420.model.entity.item.HealthPotion;
import it.unicam.cs.mpgc.rpg123420.model.entity.item.Item;
import it.unicam.cs.mpgc.rpg123420.model.game.Dungeon;
import it.unicam.cs.mpgc.rpg123420.model.game.Room;
import it.unicam.cs.mpgc.rpg123420.model.service.CombatService;
import it.unicam.cs.mpgc.rpg123420.persistence.DataStore;
import it.unicam.cs.mpgc.rpg123420.persistence.JsonDataStore;
import it.unicam.cs.mpgc.rpg123420.persistence.dto.GameStateDTO;

import java.util.List;

public class GameController {
    private Player player;
    private Dungeon dungeon;
    private CombatService combatService;
    private DataStore dataStore; // Usiamo l'interfaccia, non l'implementazione concreta (Dependency Inversion)
    private boolean gameStarted = false;

    public GameController() {
        this.combatService = new CombatService();
        this.dataStore = new JsonDataStore();
    }

    // Metodo chiamato dalla UI quando l'utente sceglie la classe
    public void startNewGame(String className, String playerName) {
        if (className.equals("Warrior")) {
            this.player = new Warrior(playerName);
        } else if (className.equals("Mage")) {
            this.player = new Mage(playerName);
        } else {
            // Default o eccezione
            this.player = new Warrior(playerName);
        }

        initDungeon();
        this.gameStarted = true;
    }

    public void useItem(int itemIndex) {
        if (!gameStarted || player == null) return;

        List<Item> items = player.getInventory();
        if (itemIndex >= 0 && itemIndex < items.size()) {
            Item item = items.get(itemIndex);
            item.use(player); // Polimorfismo: chiama use() specifico dell'oggetto
            player.removeItem(item); // Rimuovi l'oggetto dopo l'uso (se consumabile)
            // Logica di log potrebbe essere gestita dalla View o ritornata qui
        }
    }

    public List<Item> getPlayerItems() {
        if (player == null) return List.of();
        return player.getInventory();
    }

    private void initDungeon() {
        this.dungeon = new Dungeon();

        // Diamo al giocatore una pozione iniziale per aiutare contro il boss
        this.player.addItem(new HealthPotion(50));

        // Stanza 1: Goblin e Orco
        Room r1 = new Room(1, false);
        r1.addEnemy(new Goblin());
        r1.addEnemy(new Orc());
        dungeon.addRoom(r1);

        // Stanza 2: Boss
        Room r2 = new Room(2, true);
        r2.addEnemy(new Boss());
        dungeon.addRoom(r2);
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    // Metodi esposti alla View
    public Player getPlayer() {
        return player;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public List<Enemy> getCurrentEnemies() {
        if (!gameStarted || dungeon == null) {
            System.out.println("Gioco non iniziato o dungeon nullo");
            return List.of();
        }
        Room current = dungeon.getCurrentRoom();
        if (current == null) {
            System.out.println("Stanza corrente nulla");
            return List.of();
        }
        return current.getEnemies();
    }

    public String attackEnemy(int enemyIndex) {
        if (!gameStarted) return "Gioco non iniziato.";
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
            log += "\nStanza liberata! Premi 'Prossima stanza' per procedere.";
        }

        return log;
    }

    public void nextRoom() {
        if (!gameStarted) return;
        if (dungeon.getCurrentRoom() != null && !dungeon.getCurrentRoom().hasLivingEnemies()) {
            dungeon.nextRoom();
        }
    }

    public void saveGame() {
        if (!gameStarted) return;
        GameStateDTO state = new GameStateDTO(player, dungeon);
        dataStore.saveGame(state, "savegame.json");
    }

    public void loadGame() {
        GameStateDTO state = dataStore.loadGame("savegame.json");
        if (state != null && state.getPlayer() != null && state.getDungeon() != null) {
            this.player = state.getPlayer();
            this.dungeon = state.getDungeon();
            this.gameStarted = true;
        } else {
            System.out.println("Nessun salvataggio valido trovato.");
        }
    }

    public boolean isGameOver() {
        if (!gameStarted) return false;
        return !player.isAlive();
    }

    public boolean isVictory() {
        if (!gameStarted) return false;
        Room current = dungeon.getCurrentRoom();
        return dungeon.isFinished() && (current == null || !current.hasLivingEnemies());
    }
}
