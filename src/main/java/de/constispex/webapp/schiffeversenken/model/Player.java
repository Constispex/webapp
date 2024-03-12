package de.constispex.webapp.schiffeversenken.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    String name;
    int score;
    List<Ship> ships;
    List<Position> attacks;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.ships = new ArrayList<>();
        this.attacks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
}
