package de.constispex.webapp.schiffeversenken.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class AttackResult {
    boolean hit;
    String message;

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
