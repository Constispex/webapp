package de.constispex.webapp.hangman.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class GuessResponse {
    private boolean isCorrect;
    private String guessedWord;
    private int tries;
    private String guessedLettersString; // neues Attribut, um die guessedLetters in der Datenbank zu speichern
    @ElementCollection
    private List<Character> guessedLetters;
    private String message;

    public GuessResponse(boolean isCorrect, String guessedWord, int tries, List<Character> guessedLetters, String message) {
        this.isCorrect = isCorrect;
        this.guessedWord = guessedWord;
        this.tries = tries;
        this.message = message;
        setGuessedLetters(guessedLetters);
    }

    public GuessResponse() {

    }

    public List<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public void setGuessedLetters(List<Character> guessedLetters) {
        this.guessedLetters = guessedLetters;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public String getGuessedWord() {
        return guessedWord;
    }

    public void setGuessedWord(String guessedWord) {
        this.guessedWord = guessedWord;
    }
}