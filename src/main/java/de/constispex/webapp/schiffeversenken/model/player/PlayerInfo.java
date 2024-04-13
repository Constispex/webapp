package de.constispex.webapp.schiffeversenken.model.player;

import jakarta.persistence.Embeddable;

@Embeddable
public class PlayerInfo {
    private int playerId;
    private String playerName;
    private boolean isPlayer;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }
}
