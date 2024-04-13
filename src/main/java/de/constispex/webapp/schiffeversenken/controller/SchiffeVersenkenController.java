package de.constispex.webapp.schiffeversenken.controller;

//luaks war hier

import de.constispex.webapp.schiffeversenken.model.*;
import de.constispex.webapp.schiffeversenken.model.player.Player;
import de.constispex.webapp.schiffeversenken.model.player.PlayerInfo;
import de.constispex.webapp.schiffeversenken.model.state.FieldState;
import de.constispex.webapp.schiffeversenken.model.state.GameState;
import de.constispex.webapp.schiffeversenken.service.SchiffeVersenkenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for the SchiffeVersenken game.
 * Die Klasse SchiffeVersenkenController ist ein RestController, der die REST-Endpunkte für das SchiffeVersenken-Spiel bereitstellt.
 */
@RestController
public class SchiffeVersenkenController {
    protected static final String URL = "/schiffe-versenken";
    private final SchiffeVersenkenService service;

    public SchiffeVersenkenController(SchiffeVersenkenService service) {
        this.service = service;
    }

    /**
     * Erstellt ein neues Spiel und gibt die Spiel-ID zurück.
     * @param playerInfo Enthält den Namen des Spielers.
     * @return die Spiel-ID des erstellten Spiels.
     */
    @PostMapping(URL + "/createGame")
    public ResponseEntity<Long> createGame(@RequestBody PlayerInfo playerInfo) {
        Game nGame = service.createGame();
        return ResponseEntity.ok(nGame.getId());
    }

    /**
     * Gibt die Spiel-ID zurück, zu der der Spieler beigetreten ist.
     * @param gameId Die Spiel-ID, zu der der Spieler beitreten möchte.
     * @param playerInfo Enthält den Namen des Spielers.
     * @return die Spiel-ID des Spiels, zu dem der Spieler beigetreten ist.
     */
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

    @GetMapping(URL + "/game/{gameId}/player/{playerId}/attack/{x}/{y}")
    public AttackResult attack(@PathVariable int gameId, @PathVariable int playerId, @PathVariable int x, @PathVariable int y) {
        return service.attack(gameId, playerId, new Position(x, y));

    }

        /**
         * Place Ships
         * */
        @PostMapping(URL + "/game/{gameId}/player/{playerId}/placeShip/{x}/{y}/{length}/{direction}")
        public void placeShip(@PathVariable int gameId, @PathVariable int playerId, @PathVariable int x, @PathVariable int y, @PathVariable int length, @PathVariable String direction) {
            try {
                service.placeShip(gameId, playerId, new Position(x,y));
            } catch (IllegalArgumentException e) {
                System.err.println("Game not found");
            }

        }



    @GetMapping(URL + "/game/{gameId}/board/{playerId}")
    public List<FieldState> getBoardByPlayerId(@PathVariable int gameId, @PathVariable int playerId) {
        Game game = service.getGame(gameId);
        return game.getBoard(playerId);
    }

    @GetMapping(URL + "/game/{gameId}/player/{playerName}")
    public PlayerInfo getPlayerId(@PathVariable int gameId, @PathVariable String playerName) {
        Game game = service.getGame(gameId);
        PlayerInfo pInfo = new PlayerInfo();
        if (game == null) {
            pInfo.setPlayerId(-1);
            return pInfo;
        }
        pInfo.setPlayerId(game.getPlayerId(playerName));
        return pInfo;
    }

    @GetMapping(URL + "/game/{gameId}/player/id/{playerId}")
    public PlayerInfo getPlayerName(@PathVariable int gameId, @PathVariable int playerId) {
        Game game = service.getGame(gameId);
        PlayerInfo pInfo = new PlayerInfo();
        if (game == null) {
            pInfo.setPlayerName("Game not found");
            return pInfo;
        }
        pInfo.setPlayerName(game.getPlayer(playerId).getName());
        return pInfo;
    }

    @GetMapping(URL + "/game/{gameId}/state")
    public String getGameStatus(@PathVariable int gameId) {
        Game game = service.getGame(gameId);
        if (game == null) {
            return "Game not found";
        }
        return game.getState().toString();
    }

    @PostMapping(URL + "/game/{gameId}/setReady/{playerId}")
    public boolean setReady(@PathVariable int gameId, @PathVariable int playerId) {
            service.getGame(gameId).getPlayer(playerId).setReady(true);
             return true;
    }

    @PostMapping(URL + "/game/{gameId}/start")
    public boolean canStart(@PathVariable int gameId) {
        Game game = service.getGame(gameId);
        if (game.canStart()) {
            game.setState(GameState.WAITING_FOR_MOVE);
            return true;
        }
        return false;
    }
}
