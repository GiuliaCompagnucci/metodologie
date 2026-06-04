package it.unicam.cs.mpgc.rpg123420.model.game;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {
    private List<Room> rooms;
    private int currentRoomIndex;

    public Dungeon() {
        this.rooms = new ArrayList<>();
        this.currentRoomIndex = 0;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public Room getCurrentRoom() {
        if (currentRoomIndex >= 0 && currentRoomIndex < rooms.size()) {
            return rooms.get(currentRoomIndex);
        }
        return null;
    }

    public void nextRoom() {
        if (currentRoomIndex < rooms.size() - 1) {
            currentRoomIndex++;
        }
    }

    public boolean isFinished() {
        // Finito se siamo all'ultima stanza e non ci sono nemici vivi
        if (rooms.isEmpty()) return true;
        Room lastRoom = rooms.get(rooms.size() - 1);
        return currentRoomIndex == rooms.size() - 1 && !lastRoom.hasLivingEnemies();
    }

    public int getCurrentRoomIndex() {
        return currentRoomIndex;
    }

    public int getTotalRooms() {
        return rooms.size();
    }
}
