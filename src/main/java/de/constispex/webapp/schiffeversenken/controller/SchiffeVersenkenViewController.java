package de.constispex.webapp.schiffeversenken.controller;

import de.constispex.webapp.schiffeversenken.model.Player;
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
        Player p1 = service.getGame(gameId).getPlayer1();
        Player p2 = service.getGame(gameId).getPlayer2();

        service.getGame(gameId).start();

        if (p1 != null) model.addAttribute("player1", p1.getName());
        if (p2 != null) model.addAttribute("player2", p2.getName());

        return "schiffe-versenken-game";
    }
}
