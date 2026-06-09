package it.unicam.cs.mpgc.rpg123420.controller;

import it.unicam.cs.mpgc.rpg123420.model.entity.character.*;
import it.unicam.cs.mpgc.rpg123420.model.entity.enemy.*;
import it.unicam.cs.mpgc.rpg123420.model.entity.item.*;
import it.unicam.cs.mpgc.rpg123420.model.game.Dungeon;
import it.unicam.cs.mpgc.rpg123420.model.game.Room;
import it.unicam.cs.mpgc.rpg123420.model.service.CombatService;
import it.unicam.cs.mpgc.rpg123420.persistence.DataStore;
import it.unicam.cs.mpgc.rpg123420.persistence.JsonDataStore;
import it.unicam.cs.mpgc.rpg123420.persistence.dto.GameStateDTO;

import java.util.List;
import java.util.Random;

public class GameController {
    private Player player;
    private Dungeon dungeon;
    private CombatService combatService;
    private DataStore dataStore; // Usiamo l'interfaccia, non l'implementazione concreta (Dependency Inversion)
    private boolean gameStarted = false;
    private Random random;

    public GameController() {
        this.combatService = new CombatService();
        this.dataStore = new JsonDataStore();
        this.random = new Random();
    }

    // Metodo chiamato dalla UI quando l'utente sceglie la classe
    public void startNewGame(String className, String playerName, String difficulty) {
        if (className.equals("Warrior")) {
            this.player = new Warrior(playerName);
        } else if (className.equals("Mage")) {
            this.player = new Mage(playerName);
        } else {
            // Default o eccezione
            this.player = new Warrior(playerName);
        }

        initDungeon(difficulty);
        this.gameStarted = true;
    }

    private void initDungeon(String difficulty) {
        this.dungeon = new Dungeon();

        // Logica per il numero di stanze in base alla difficoltà
        int numRooms;
        if ("Difficile".equals(difficulty)) {
            numRooms = 5 + random.nextInt(3); // Tra 5 e 7 stanze
        } else {
            numRooms = 3 + random.nextInt(3); // Tra 3 e 5 stanze (Normale)
        }

        for (int i = 1; i <= numRooms; i++) {
            Room room = new Room(i, false);

            // In modalità difficile, più nemici per stanza
            int maxEnemies = "Difficile".equals(difficulty) ? 3 : 2;
            int numEnemies = 1 + random.nextInt(maxEnemies);

            for (int j = 0; j < numEnemies; j++) {
                room.addEnemy(generateRandomEnemy());
            }

            // Chance di loot
            if (random.nextDouble() < 0.3) {
                player.addItem(generateRandomItem());
            }

            dungeon.addRoom(room);
        }

        // Boss Room
        Room bossRoom = new Room(numRooms + 1, true);
        bossRoom.addEnemy(generateRandomBoss());
        dungeon.addRoom(bossRoom);
    }

    private Enemy generateRandomEnemy() {
        int type = random.nextInt(3);
        if (type == 0) return new Goblin();
        else if (type == 1) return new Orc();
        else return new Skeleton();
    }

    private Enemy generateRandomBoss() {
        int type = random.nextInt(2);
        if (type == 0) return new DragonBoss();
        else return new LichBoss();
    }

    private Item generateRandomItem() {
        int type = random.nextInt(2);
        if (type == 0) return new HealthPotion(50);
        else return new StrengthPotion(10);
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

    public void useItem(int itemIndex) {
        if (!gameStarted || player == null) return;

        List<Item> items = player.getInventory();
        if (itemIndex >= 0 && itemIndex < items.size()) {
            Item item = items.get(itemIndex);
            item.use(player); // Polimorfismo: chiama use() specifico dell'oggetto
            player.removeItem(item); // Rimuovi l'oggetto dopo l'uso (se consumabile)
        }
    }

    public List<Item> getPlayerItems() {
        if (player == null) return List.of();
        return player.getInventory();
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
