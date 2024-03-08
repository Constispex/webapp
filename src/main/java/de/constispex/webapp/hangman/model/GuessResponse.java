package de.constispex.webapp.hangman.model;

import java.util.List;

public class GuessResponse {
    private boolean isCorrect;
    private String guessedWord;
    private int tries;
    private List<Character> guessedLetters;
    private String message;

    public GuessResponse(boolean isCorrect, String guessedWord, int tries, List<Character> guessedLetters, String message) {
        this.isCorrect = isCorrect;
        this.guessedWord = guessedWord;
        this.guessedLetters = guessedLetters;
        this.tries = tries;
        this.message = message;
    }

    public List<Character> getGuessedLetters() {
        return guessedLetters;
    }

    public void setGuessedLetters(List<Character> guessedLetters) {
        this.guessedLetters = guessedLetters;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getGuessedWord() {
        return guessedWord;
    }

    public void setGuessedWord(String guessedWord) {
        this.guessedWord = guessedWord;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = Math.max(tries, 0);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
