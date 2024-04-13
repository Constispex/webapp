package de.constispex.webapp.hangman.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Hangman {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean isWon = false;
    private String correctWord;

    private GuessResponse guessResponse;

    public Hangman() {
    }

    public Hangman(String correctWord) {
        this.correctWord = correctWord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrectWord() {
        return correctWord;
    }

    public GuessResponse getGuessResponse() {
        return guessResponse;
    }

    public void setGuessResponse(GuessResponse guessResponse) {
        this.guessResponse = guessResponse;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }

}
