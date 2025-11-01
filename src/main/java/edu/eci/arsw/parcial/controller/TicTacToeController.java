package edu.eci.arsw.parcial.controller;

import edu.eci.arsw.parcial.model.Room;
import edu.eci.arsw.parcial.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TicTacToeController {

    private static final String ACTION = "action";
    private static final String ROOM_ID = "roomId";
    private static final String PLAYER_ID = "playerId";
    private static final String TOPIC_ROOM = "/topic/room/";

    private final GameService gameService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TicTacToeController(GameService gameService, SimpMessagingTemplate messagingTemplate) {
        this.gameService = gameService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/createRoom")
    @SendToUser("/queue/reply")
    public Map<String, Object> createRoom(@Payload Map<String, Object> payload) {
        System.out.println("createRoom called with: " + payload);
        
        String playerName = (String) payload.get("playerName");
        Room room = gameService.createRoom(playerName);
        
        System.out.println("Room created: " + room.getRoomId() + ", Player: " + room.getPlayers().get(0).getPlayerId());
        
        Map<String, Object> response = new HashMap<>();
        response.put(ACTION, "roomCreated");
        response.put(ROOM_ID, room.getRoomId());
        response.put(PLAYER_ID, room.getPlayers().get(0).getPlayerId());
        response.put("symbol", room.getPlayers().get(0).getSymbol());
        
        System.out.println("Sending response: " + response);
        
        return response;
    }

    @MessageMapping("/joinRoom")
    public void joinRoom(@Payload Map<String, Object> payload, SimpMessageHeaderAccessor headerAccessor) {
        String roomId = (String) payload.get(ROOM_ID);
        String playerName = (String) payload.get("playerName");
        
        try {
            Room room = gameService.joinRoom(roomId, playerName);

            Map<String, Object> response = new HashMap<>();
            response.put(ACTION, "playerJoined");
            response.put(ROOM_ID, room.getRoomId());
            response.put(PLAYER_ID, room.getPlayers().get(1).getPlayerId());
            response.put("symbol", room.getPlayers().get(1).getSymbol());

            messagingTemplate.convertAndSend(TOPIC_ROOM + roomId, response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put(ACTION, "error");
            errorResponse.put("message", e.getMessage());

            messagingTemplate.convertAndSend(TOPIC_ROOM + roomId, errorResponse);
        }
    }

    @MessageMapping("/makeMove")
    public void makeMove(@Payload Map<String, Object> payload) {
        String roomId = (String) payload.get(ROOM_ID);

        messagingTemplate.convertAndSend(TOPIC_ROOM + roomId, payload);
    }
}
