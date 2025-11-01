package edu.eci.arsw.parcial.service;

import edu.eci.arsw.parcial.model.Player;
import edu.eci.arsw.parcial.model.Room;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class GameService {
    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    public Room createRoom(String playerName) {
        String roomId = UUID.randomUUID().toString().substring(0, 8);
        String playerId = "p1";
        
        Room room = new Room(roomId);
        Player player = new Player(playerId, playerName, "X");
        room.addPlayer(player);
        
        rooms.put(roomId, room);
        return room;
    }

    public Room joinRoom(String roomId, String playerName) {
        Room room = rooms.get(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found");
        }
        if (room.isFull()) {
            throw new IllegalStateException("Room is full");
        }

        String playerId = "p2";
        Player player = new Player(playerId, playerName, "O");
        room.addPlayer(player);
        
        return room;
    }

    public Room getRoom(String roomId) {
        return rooms.get(roomId);
    }

    public void removeRoom(String roomId) {
        rooms.remove(roomId);
    }
}
