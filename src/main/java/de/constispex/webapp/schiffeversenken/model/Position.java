package de.constispex.webapp.schiffeversenken.model;

import jakarta.persistence.*;

/**
 * Represents a position on the game board.

 */
@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int x;
    private int y;

    public Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int toInt() {
        return x * 10 + y;
    }

    public Long getId() {
        return id;
    }
}
