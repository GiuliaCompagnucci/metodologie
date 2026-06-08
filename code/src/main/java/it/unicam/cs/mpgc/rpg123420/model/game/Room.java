package it.unicam.cs.mpgc.rpg123420.model.game;

import it.unicam.cs.mpgc.rpg123420.model.entity.enemy.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int roomNumber;
    private List<Enemy> enemies;
    private boolean isBossRoom;

    public Room(int roomNumber, boolean isBossRoom) {
        this.roomNumber = roomNumber;
        this.isBossRoom = isBossRoom;
        this.enemies = new ArrayList<>();
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public boolean hasLivingEnemies() {
        return enemies.stream().anyMatch(Enemy::isAlive);
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isBossRoom() {
        return isBossRoom;
    }
}