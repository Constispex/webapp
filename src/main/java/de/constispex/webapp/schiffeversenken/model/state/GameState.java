package de.constispex.webapp.schiffeversenken.model.state;

import jakarta.persistence.Embeddable;

@Embeddable
public enum GameState {
    WAITING_FOR_PLAYERS,
    WAITING_FOR_SHIPS,
    WAITING_FOR_MOVE,
    GAME_OVER
}
