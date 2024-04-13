package de.constispex.webapp.hangman.controller;

import de.constispex.webapp.hangman.model.GuessResponse;
import de.constispex.webapp.hangman.model.Hangman;
import de.constispex.webapp.hangman.model.HangmanRepository;
import de.constispex.webapp.hangman.service.HangmanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
public class HangmanController {

    private final HangmanService service;

    public HangmanController(HangmanService service) {
        this.service = service;
    }

    /**
     * Get the word to guess. If no word is set, a random word is chosen from the list of words.
     *
     * @return the word to guess
     */
    @GetMapping("/hangman/startGame")
    public Long startGame() {
        return service.createGame();
    }

    @GetMapping("/hangman/{gameId}/guess/{letter}")
    public GuessResponse guessWord(@PathVariable Long gameId, @PathVariable String letter) {
        return service.guessLetter(gameId, letter);
    }

    @GetMapping("/hangman/status/{gameId}")
    public GuessResponse getStatus(@PathVariable Long gameId) {
        return service.getGame(gameId).getGuessResponse();
    }


}
