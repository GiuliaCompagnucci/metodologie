package it.unicam.cs.mpgc.rpg123420.persistence;

import com.google.gson.*;
import it.unicam.cs.mpgc.rpg123420.model.entity.character.*;
import it.unicam.cs.mpgc.rpg123420.model.entity.enemy.*;
import it.unicam.cs.mpgc.rpg123420.model.entity.item.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonProvider {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Player.class, new PlayerAdapter())
            .registerTypeAdapter(Enemy.class, new EnemyAdapter())
            .registerTypeAdapter(Item.class, new ItemAdapter())
            .create();

    public static Gson getGson() {
        return gson;
    }

    // --- ADAPTER PER PLAYER ---
    private static class PlayerAdapter implements JsonSerializer<Player>, JsonDeserializer<Player> {
        @Override
        public JsonElement serialize(Player src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();
            json.addProperty("type", src.getClass().getSimpleName());
            json.addProperty("name", src.getName());
            json.addProperty("currentHealth", src.getCurrentHealth());
            json.addProperty("maxHealth", src.getMaxHealth());
            json.addProperty("bonusDamage", src.getBonusDamage());
            // Serializza l'inventario ricorsivamente
            json.add("inventory", context.serialize(src.getInventory()));
            return json;
        }

        @Override
        public Player deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            String type = obj.get("type").getAsString();
            String name = obj.get("name").getAsString();

            Player player;
            if ("Warrior".equals(type)) player = new Warrior(name);
            else player = new Mage(name);

            player.setStats(
                    obj.get("currentHealth").getAsInt(),
                    obj.get("maxHealth").getAsInt(),
                    obj.get("bonusDamage").getAsInt()
            );

            // Deserializza l'inventario
            List<Item> inventory = new ArrayList<>();
            if (obj.has("inventory")) {
                for (JsonElement itemElem : obj.getAsJsonArray("inventory")) {
                    inventory.add(context.deserialize(itemElem, Item.class));
                }
            }
            player.setInventory(inventory);

            return player;
        }
    }

    // --- ADAPTER PER ENEMY ---
    private static class EnemyAdapter implements JsonSerializer<Enemy>, JsonDeserializer<Enemy> {
        @Override
        public JsonElement serialize(Enemy src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();
            json.addProperty("type", src.getClass().getSimpleName());
            json.addProperty("name", src.getName());
            json.addProperty("currentHealth", src.getCurrentHealth());
            json.addProperty("maxHealth", src.getMaxHealth());
            json.addProperty("damage", src.getDamage());
            return json;
        }

        @Override
        public Enemy deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            String type = obj.get("type").getAsString();

            Enemy enemy;
            switch (type) {
                case "Goblin": enemy = new Goblin(); break;
                case "Orc": enemy = new Orc(); break;
                case "Skeleton": enemy = new Skeleton(); break;
                case "Boss": enemy = new DragonBoss(); break;
                case "Lich": enemy = new LichBoss(); break;
                default: enemy = new Goblin(); break;
            }

            enemy.setStats(
                    obj.get("name").getAsString(),
                    obj.get("currentHealth").getAsInt(),
                    obj.get("maxHealth").getAsInt(),
                    obj.get("damage").getAsInt()
            );
            return enemy;
        }
    }

    // --- ADAPTER PER ITEM ---
    private static class ItemAdapter implements JsonSerializer<Item>, JsonDeserializer<Item> {
        @Override
        public JsonElement serialize(Item src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();
            json.addProperty("type", src.getClass().getSimpleName());
            return json;
        }

        @Override
        public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();

            if (!obj.has("type") || obj.get("type") == null || obj.get("type").isJsonNull()) {
                System.err.println("Item senza tipo rilevato, restituisco pozione base.");
                return new HealthPotion(50);
            }

            String type = obj.get("type").getAsString();

            if ("HealthPotion".equals(type)) return new HealthPotion(50);
            if ("StrengthPotion".equals(type)) return new StrengthPotion(10);
            return new HealthPotion(50); // default
        }
    }
}