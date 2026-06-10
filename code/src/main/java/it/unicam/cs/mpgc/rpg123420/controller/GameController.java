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

/**
 * Classe Controller principale che coordina la logica di business del gioco.
 * Gestisce l'inizializzazione della partita, la generazione procedurale del dungeon,
 * le azioni di combattimento, l'uso degli oggetti e la persistenza dei dati.
 * Funge da intermediario tra la View (UI) e il Model (Entità di gioco).
 */
public class GameController {
    private Player player;
    private Dungeon dungeon;
    private CombatService combatService;
    private DataStore dataStore; // Usiamo l'interfaccia, non l'implementazione concreta (Dependency Inversion)
    private boolean gameStarted = false;
    private Random random;

    /**
     * Costruisce un nuovo GameController inizializzando i servizi di supporto.
     */
    public GameController() {
        this.combatService = new CombatService();
        this.dataStore = new JsonDataStore();
        this.random = new Random();
    }

    /**
     * Avvia una nuova partita configurando il personaggio e generando il dungeon in base alla difficoltà.
     * @param className La classe dell'eroe scelta (es. "Warrior", "Mage").
     * @param playerName Il nome assegnato all'eroe.
     * @param difficulty Il livello di difficoltà selezionato ("Normale" o "Difficile").
     */
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

    /**
     * Genera proceduralmente il dungeon con un numero di stanze e nemici basato sulla difficoltà.
     * @param difficulty La stringa che indica la difficoltà scelta dall'utente.
     */
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

            // Chance di loot (30%)
            if (random.nextDouble() < 0.3) {
                player.addItem(generateRandomItem());
            }

            dungeon.addRoom(room);
        }

        // Boss Room finale
        Room bossRoom = new Room(numRooms + 1, true);
        bossRoom.addEnemy(generateRandomBoss());
        dungeon.addRoom(bossRoom);
    }

    /**
     * Genera un nemico comune casuale tra Goblin, Orco e Skeleton.
     * @return Un'istanza di Enemy.
     */
    private Enemy generateRandomEnemy() {
        int type = random.nextInt(3);
        if (type == 0) return new Goblin();
        else if (type == 1) return new Orc();
        else return new Skeleton();
    }

    /**
     * Genera un Boss casuale tra DragonBoss e LichBoss.
     * @return Un'istanza di Enemy rappresentante il Boss.
     */
    private Enemy generateRandomBoss() {
        int type = random.nextInt(2);
        if (type == 0) return new DragonBoss();
        else return new LichBoss();
    }

    /**
     * Genera un oggetto consumabile casuale (Pozione di Cura o di Forza).
     * @return Un'istanza di Item.
     */
    private Item generateRandomItem() {
        int type = random.nextInt(2);
        if (type == 0) return new HealthPotion(50);
        else return new StrengthPotion(10);
    }

    /**
     * Verifica se una partita è attualmente in corso.
     * @return true se il gioco è stato avviato e non è terminato, false altrimenti.
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    // Metodi esposti alla View per accedere allo stato del Model

    /**
     * Restituisce l'istanza corrente del giocatore (Eroe).
     * @return L'oggetto Player attivo nella partita.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Restituisce l'istanza corrente del Dungeon generato.
     * @return L'oggetto Dungeon contenente le stanze e i nemici.
     */
    public Dungeon getDungeon() {
        return dungeon;
    }

    /**
     * Utilizza un oggetto presente nell'inventario del giocatore all'indice specificato.
     * Rimuove l'oggetto dopo l'uso se è consumabile.
     * @param itemIndex L'indice dell'oggetto nella lista dell'inventario.
     */
    public void useItem(int itemIndex) {
        if (!gameStarted || player == null) return;

        List<Item> items = player.getInventory();
        if (itemIndex >= 0 && itemIndex < items.size()) {
            Item item = items.get(itemIndex);
            item.use(player); // Polimorfismo: chiama use() specifico dell'oggetto
            player.removeItem(item); // Rimuovi l'oggetto dopo l'uso (se consumabile)
        }
    }

    /**
     * Restituisce la lista degli oggetti attualmente presenti nell'inventario del giocatore.
     * Utilizzato dalla View per visualizzare e gestire l'uso degli item.
     * @return Una lista di Item, oppure una lista vuota se il giocatore non è stato inizializzato.
     */
    public List<Item> getPlayerItems() {
        if (player == null) return List.of();
        return player.getInventory();
    }

    /**
     * Restituisce la lista dei nemici presenti nella stanza corrente.
     * @return Lista di Enemy, oppure lista vuota se il gioco non è iniziato.
     */
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

    /**
     * Gestisce il turno di combattimento: attacco del giocatore e controattacco dei nemici.
     * @param enemyIndex L'indice del nemico bersaglio nell'array dei nemici della stanza.
     * @return Una stringa di log che descrive l'esito del combattimento.
     */
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

    /**
     * Sposta il giocatore alla stanza successiva se quella corrente è stata liberata dai nemici.
     */
    public void nextRoom() {
        if (!gameStarted) return;
        if (dungeon.getCurrentRoom() != null && !dungeon.getCurrentRoom().hasLivingEnemies()) {
            dungeon.nextRoom();
        }
    }

    /**
     * Salva lo stato attuale del gioco (giocatore e dungeon) su file JSON.
     */
    public void saveGame() {
        if (!gameStarted) return;
        GameStateDTO state = new GameStateDTO(player, dungeon);
        dataStore.saveGame(state, "savegame.json");
    }

    /**
     * Carica uno stato di gioco precedentemente salvato da file JSON.
     */
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

    /**
     * Verifica se il giocatore ha perso tutti i punti vita.
     * @return true se il gioco è finito per sconfitta.
     */
    public boolean isGameOver() {
        if (!gameStarted) return false;
        return !player.isAlive();
    }

    /**
     * Verifica se il giocatore ha completato tutte le stanze e sconfitto l'ultimo boss.
     * @return true se il dungeon è stato completato con successo.
     */
    public boolean isVictory() {
        if (!gameStarted) return false;
        Room current = dungeon.getCurrentRoom();
        return dungeon.isFinished() && (current == null || !current.hasLivingEnemies());
    }
}
