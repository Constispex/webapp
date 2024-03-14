package de.constispex.webapp.schiffeversenken.service;

import de.constispex.webapp.schiffeversenken.model.Game;
import de.constispex.webapp.schiffeversenken.model.Player;

import java.util.HashMap;
import java.util.Map;

public class SchiffeVersenkenService {
    private static final Map<Integer, Game> games = new HashMap<>();

    public Game createGame() {
        Game game = new Game(games.size());
        games.put(game.getId(), game);
        return game;
    }

    public void joinGame(int gameId, Player player) {
        Game game = games.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }
        game.addPlayer(player);
    }

    public Game getGame(int gameId) {
        return games.get(gameId);
    }
}