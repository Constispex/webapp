package de.constispex.webapp.hangman.controller;

import de.constispex.webapp.hangman.model.GuessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
public class HangmanController {

    private final Random random = new Random();
    private final List<Character> guessedLetters = new ArrayList<>();
    List<String> words = List.of("Haus", "Baum", "Auto", "Computer", "Programmieren", "Laptop", "Tisch", "Stuhl", "Fenster", "Tür");
    private String currWord = "";
    private String guessedWord = "";
    private int tries = 0;

    /**
     * Get the word to guess. If no word is set, a random word is chosen from the list of words.
     *
     * @return the word to guess
     */
    @GetMapping("/hangman/startGame")
    public GuessResponse getWord() {
        String msg = "Game already started";
        if (currWord.isBlank() || currWord.isEmpty()) {
            // initial word
            currWord = words.get(random.nextInt(words.size()));
            guessedWord = currWord.replaceAll("[a-zA-ZäöüÄÖÜß]", "_");
            tries = 0;
            guessedLetters.clear();
            msg = "Game started";
        }
        return new GuessResponse(true, guessedWord, tries, guessedLetters, msg);
    }

    @PostMapping("/hangman/guess/{letter}")
    public GuessResponse guessWord(@PathVariable String letter) {
        if (!letter.matches("[a-zA-ZäöüÄÖÜß]")) {
            return new GuessResponse(false, guessedWord, tries, guessedLetters, "Invalid input");
        }
        String msg;
        boolean isInWord = isLetterInWord(letter);
        if (guessedLetters.contains(letter.charAt(0))) {
            return new GuessResponse(false, guessedWord, tries, guessedLetters, "You already guessed this letter");
        } else {
            guessedLetters.add(letter.charAt(0));
            guessedLetters.sort(Character::compareTo);
        }
        if (currWord == null || currWord.isEmpty()) {
            return new GuessResponse(false, guessedWord, tries, guessedLetters, "No word to guess");
        }

        if (isInWord) {
            msg = "You guessed correctly";
            guessedWord = updateGuessedWord();
            if (guessedWord.equals(currWord)) {
                msg = "you won";
            }
        } else {
            tries++;
            msg = "You guessed wrong";
        }
        return new GuessResponse(isInWord, guessedWord, tries, guessedLetters, msg);
    }

    private String updateGuessedWord() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < currWord.length(); i++) {
            if (guessedLetters.contains(currWord.toLowerCase().charAt(i))) {
                sb.append(currWord.charAt(i));
            } else {
                sb.append("_");
            }
        }
        return sb.toString();
    }

    private boolean isLetterInWord(String letter) {
        return currWord.toLowerCase().contains(letter.toLowerCase());
    }
}
