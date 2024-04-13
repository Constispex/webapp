package de.constispex.webapp.hangman.service;

import de.constispex.webapp.hangman.model.GuessResponse;
import de.constispex.webapp.hangman.model.Hangman;
import de.constispex.webapp.hangman.model.HangmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class HangmanService {
    private final Random rand = new Random();
    private final HangmanRepository repository;

    private final List<String> words = List.of("hangman", "java", "spring", "programming", "computer", "software", "developer", "application", "web", "database");

    @Autowired
    public HangmanService(HangmanRepository hangmanRepository) {
        this.repository = hangmanRepository;
    }

    public Long createGame() {
        Hangman game = new Hangman(getRandomWord());
        GuessResponse resp = new GuessResponse(false, getGuessedWord(game.getCorrectWord(), List.of()), 0, List.of(), "Game started!");
        game.setGuessResponse(resp);
        game = saveGame(game);
        System.out.println("Created game: " + game.getId() + " with word: " + game.getCorrectWord());
        return game.getId();
    }

    private String getRandomWord() {
        return words.get(rand.nextInt(words.size()));
    }


    public GuessResponse guessLetter(Long id, String letter) {
        Hangman game = getGame(id);
        String correctWord = game.getCorrectWord();
        GuessResponse response = game.getGuessResponse();

        if (game.isWon()) {
            response.setMessage("Game already won!");
            game.setGuessResponse(response);
            saveGame(game);
            return response;
        }

        if (response.getGuessedLetters().contains(letter.charAt(0))) {
            response.setMessage("Letter already guessed!");
            game.setGuessResponse(response);
            saveGame(game);
            return response;
        }

        response.getGuessedLetters().add(letter.charAt(0));

        if (isLetterInWord(correctWord, letter)) {
            response.setMessage("Letter found!");
            response.setGuessedWord(getGuessedWord(correctWord, response.getGuessedLetters()));
            if (response.getGuessedWord().equals(correctWord)) {
                game.setWon(true);
                response.setMessage("You won!");
            }
        } else {
            response.setMessage("Letter not found!");
            response.setTries(response.getTries() + 1);
            if (response.getTries() >= 6) {
                response.setMessage("You lost!");
            }
        }
        game.setGuessResponse(response);
        saveGame(game);
        return response;
    }

    private String getGuessedWord(String correctWord, List<Character> guessedLetters) {
        StringBuilder guessedWord = new StringBuilder();
        for (char c : correctWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                guessedWord.append(c);
            } else {
                guessedWord.append("_");
            }
        }
        return guessedWord.toString();
    }

    public Hangman getGame(Long gameId) {
        return repository.findById(gameId).orElse(null);
    }

    public Hangman saveGame(Hangman game) {
        return repository.save(game);
    }

    public void deleteGame(Long gameId) {
        repository.deleteById(gameId);
    }

    private boolean isLetterInWord(String word, String letter) {
        return word.toLowerCase().contains(letter.toLowerCase());
    }
}