package de.constispex.webapp.schiffeversenken.model;

import jakarta.persistence.*;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int size;
    private boolean vertical;

    public Ship() {
    }

    public Ship(int size, boolean vertical) {
        this.size = size;
        this.vertical = vertical;
    }

    public int getSize() {
        return size;
    }

    public boolean isVertical() {
        return vertical;
    }

    public Long getId() {
        return id;
    }
}
