package de.constispex.webapp.schiffeversenken.service;

import de.constispex.webapp.schiffeversenken.model.AttackResult;
import de.constispex.webapp.schiffeversenken.model.Game;

import de.constispex.webapp.schiffeversenken.model.GameRepository;
import de.constispex.webapp.schiffeversenken.model.Position;
import de.constispex.webapp.schiffeversenken.model.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class SchiffeVersenkenService {

    private final GameRepository repos;

    @Autowired
    public SchiffeVersenkenService(GameRepository repos) {
        this.repos = repos;
    }
    public Game createGame() {
        Game game = new Game();
        repos.save(game);
        return game;
    }


    public void joinGame(long gameId, Player player) {
        Game game = getGame(gameId);
        game.join(player);
        repos.save(game);
    }

    public Game getGame(long gameId) {
        Game curr = repos.findById(gameId).orElse(null);
        if (curr == null) {
            throw new IllegalArgumentException("Game not found with id " + gameId);
        }
        return curr;
    }

    public void placeShip(int gameId, int playerId, Position position) {
        Game game = getGame(gameId);
        game.placeShip(game.getPlayer(playerId), position);
        repos.save(game);
    }

    public AttackResult attack(int gameId, int playerId, Position position) {
        Game game = getGame(gameId);
        AttackResult res = game.attack(playerId, position);
        repos.save(game);
        return res;
    }
}