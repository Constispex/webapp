package de.constispex.webapp.schiffeversenken.controller;

import de.constispex.webapp.schiffeversenken.service.SchiffeVersenkenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SchiffeVersenkenViewController {
    private static final String URL = "/schiffe-versenken";
    private final SchiffeVersenkenService service = new SchiffeVersenkenService();

    @GetMapping(URL)
    public String schiffeVersenken() {
        return "schiffe-versenken";
    }

    @GetMapping(URL + "/game/{gameId}")
    public String schiffeVersenkenGame(@PathVariable int gameId, Model model) {
        model.addAttribute("gameId", gameId);
        String p1 = service.getGame(gameId).getPlayer1().getName();
        String p2 = service.getGame(gameId).getPlayer2().getName();

        service.getGame(gameId).start();

        model.addAttribute("player1", p1);
        model.addAttribute("player2", p2);

        return "schiffe-versenken-game";
    }
}
