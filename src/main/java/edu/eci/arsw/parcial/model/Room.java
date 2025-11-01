package edu.eci.arsw.parcial.model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomId;
    private List<Player> players;
    private String state;

    public Room(String roomId) {
        this.roomId = roomId;
        this.players = new ArrayList<>();
        this.state = "waiting";
    }

    public boolean isFull() {
        return players.size() >= 2;
    }

    public boolean canStart() {
        return players.size() == 2;
    }

    public void addPlayer(Player player) {
        if (!isFull()) {
            players.add(player);
            if (canStart()) {
                state = "playing";
            }
        }
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
