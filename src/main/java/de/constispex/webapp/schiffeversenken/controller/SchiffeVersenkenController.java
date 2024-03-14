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

    @PostMapping(URL + "/join/{gameId}")
    public String joinGame(@PathVariable int gameId, @RequestBody PlayerInfo playerInfo) {
        try {
            Player player = new Player(playerInfo.getPlayerName());
            service.joinGame(gameId, player);
        } catch (IllegalArgumentException e) {
            return "Game not found";
        }
        return String.valueOf(gameId);
    }

    @PostMapping(URL + "/attack/{gameId}/{x}/{y}")
    public AttackResult attack(@PathVariable int gameId, @PathVariable int x, @PathVariable int y) {
        try {
            Game game = service.getGame(gameId);
            return game.attack(new Position(x, y));
        } catch (IllegalArgumentException e) {
            return new AttackResult(false, "Game not found");
        }
    }

    @GetMapping(URL + "/game/{gameId}/getBoard/{playerId}")
    public FieldState[][] getBoardByPlayerId(@PathVariable int gameId, @PathVariable int playerId) {
        Game game = service.getGame(gameId);
        return game.getBoard(playerId);
    }

    @GetMapping(URL + "/game/{gameId}/player/{playerName}")
    public String getPlayerId(@PathVariable int gameId, @PathVariable String playerName) {
        Game game = service.getGame(gameId);
        if (game == null) {
            return "Game not found";
        }
        return game.getPlayerId(playerName) == 1 ? "1" : "2";
    }

    @GetMapping(URL + "/game/{gameId}/player/id/{playerId}")
    public PlayerInfo getPlayerName(@PathVariable int gameId, @PathVariable int playerId) {
        Game game = service.getGame(gameId);
        PlayerInfo pInfo = new PlayerInfo();
        if (game == null) {
            pInfo.setPlayerName("");
            return pInfo;
        }
        pInfo.setPlayerName(game.getPlayerName(playerId));
        return pInfo;
    }
}
