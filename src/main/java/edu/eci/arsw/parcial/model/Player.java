package edu.eci.arsw.parcial.model;

public class Player {
    private String playerId;
    private String playerName;
    private String symbol;

    public Player(String playerId, String playerName, String symbol) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.symbol = symbol;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
