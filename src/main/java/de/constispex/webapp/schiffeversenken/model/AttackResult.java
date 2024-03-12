package de.constispex.webapp.schiffeversenken.model;

public class AttackResult {
    private final boolean hit;
    private final String message;

    public AttackResult(boolean hit, String message) {
        this.hit = hit;
        this.message = message;
    }
}
