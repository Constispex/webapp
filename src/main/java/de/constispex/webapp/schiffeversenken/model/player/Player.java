package de.constispex.webapp.schiffeversenken.model.player;

import de.constispex.webapp.schiffeversenken.model.Position;
import de.constispex.webapp.schiffeversenken.model.Ship;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String name;
    boolean isReady;
    boolean isPlayer1;
    int score;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "player_id")
    private List<Ship> ships = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "player_id")
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

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public List<Position> getAttacks() {
        return attacks;
    }

    public void setAttacks(List<Position> attacks) {
        this.attacks = attacks;
    }

    public void setPlayer1(boolean player1) {
        isPlayer1 = player1;
    }

    public boolean isPlayer1() {
        return isPlayer1;
    }
}
