package de.constispex.webapp.schiffeversenken.controller;

import de.constispex.webapp.schiffeversenken.model.*;
import de.constispex.webapp.schiffeversenken.service.SchiffeVersenkenService;
import org.springframework.web.bind.annotation.*;

@RestController
public class SchiffeVersenkenController {
    private static final String URL = "/schiffe-versenken";
    private final SchiffeVersenkenService service = new SchiffeVersenkenService();

    @PostMapping(URL + "/createGame")
    public String createGame(@RequestBody PlayerInfo playerInfo) {
        Game game = service.createGame();
        Player player = new Player(playerInfo.getPlayerName());
        service.joinGame(game.getId(), player);

        return String.valueOf(game.getId());
    }

    @GetMapping(URL + "/joinGame/{gameId}/{playerName}")
    public String joinGame(@PathVariable int gameId, @PathVariable String playerName) {
        try {
            Player player = new Player(playerName);
            service.joinGame(gameId, player);
        } catch (IllegalArgumentException e) {
            return "Game not found";
        }
        return String.valueOf(gameId);
    }

    @GetMapping(URL + "/game/{gameId}")
    public String startGame(@PathVariable int gameId) {
        try {
            Game game = service.getGame(gameId);
            game.start();
        } catch (IllegalArgumentException e) {
            return "Game not found";
        } catch (NullPointerException n) {
            return "Game is not full yet";
        }
        return "Game started";
    }

    @GetMapping(URL + "/attack/{gameId}/{x}/{y}")
    public AttackResult attack(@PathVariable int gameId, @PathVariable int x, @PathVariable int y) {
        try {
            Game game = service.getGame(gameId);
            return game.attack(new Position(x, y));
        } catch (IllegalArgumentException e) {
            return new AttackResult(false, "Game not found");
        }
    }
}
