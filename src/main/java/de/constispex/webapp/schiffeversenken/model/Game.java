package de.constispex.webapp.schiffeversenken.model;

import de.constispex.webapp.schiffeversenken.model.player.Player;
import de.constispex.webapp.schiffeversenken.model.state.FieldState;
import de.constispex.webapp.schiffeversenken.model.state.GameState;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Player player1;
    @OneToOne
    private Player player2;
    private GameState state;
    @OneToOne
    private Player currPlayer;

    @ElementCollection
    private final List<FieldState> board1 = new ArrayList<>();
    @ElementCollection
    private final List<FieldState> board2 = new ArrayList<>();

    public Game() {

    }

    public long getId() {
        return id;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public boolean isFull() {
        return player1 != null && player2 != null;
    }

    private void switchTurns() {
        if (currPlayer == player1) {
            currPlayer = player2;
        } else {
            currPlayer = player1;
        }
    }

    public void start() {
        if (state == GameState.WAITING_FOR_PLAYERS) {
            state = GameState.WAITING_FOR_SHIPS;
        }
        currPlayer = player1;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public void join(Player player) {
        if (player1 == null) {
            player1 = player;
        } else if (player2 == null) {
            player2 = player;
        }
    }

   public void placeShip(Player player, Position position) {
        if (state != GameState.WAITING_FOR_SHIPS) {
            throw new IllegalStateException("Game not in ship placement phase");
        }
       if(player.isPlayer1()) {
           board1.set(position.toInt(), FieldState.SHIP);
       } else {
              board2.set(position.toInt(), FieldState.SHIP);
         }
    }

    private void saveToBoard(Ship ship, Position position, List<FieldState> board) {
        if (ship.isVertical()) {
               for (int i = 0; i < ship.getSize(); i++) {
                    board.set(position.toInt() + i, FieldState.SHIP);
                }
        } else {
            for (int i = 0; i < ship.getSize(); i++) {
                board.set(position.toInt() + i * 10, FieldState.SHIP);
            }
        }
    }

    public AttackResult attack(int playerId, Position position) {
        if (state != GameState.WAITING_FOR_MOVE) {
            throw new IllegalStateException("Game not in attack phase");
        }
        if (currPlayer.getName().equals(getPlayer(playerId).getName())) {
            if (currPlayer.equals(player1)) {
                switchTurns();
                return getAttackResult(position, board2);
            } else if (currPlayer.equals(player2)) {
                switchTurns();
                return getAttackResult(position, board1);
            } else {
                AttackResult res = new AttackResult();
                res.setHit(false);
                res.setMessage("currPlayer not found");
                return res;
            }
        } else {
            AttackResult res = new AttackResult();
            res.setHit(false);
            res.setMessage("Not your turn");
            return res;
        }
    }

    private AttackResult getAttackResult(Position position, List<FieldState> board1) {
        AttackResult res = new AttackResult();
        if (board1.get(position.toInt()) == FieldState.SHIP) {
            res.setHit(true);
            res.setMessage("Hit");
            board1.set(position.toInt(), FieldState.HIT);
        } else {
            res.setHit(false);
            res.setMessage("Miss");
            board1.set(position.toInt(), FieldState.MISS);
        }
        return res;
    }

    public int getPlayerId(String playerName) {
        if (player1.getName().equals(playerName)) {
            return 1;
        } else if (player2.getName().equals(playerName)) {
            return 2;
        } else {
            throw new IllegalArgumentException("Invalid player name");
        }
    }

    public Player getPlayer(int playerId) {
        if (playerId == 1) {
            return player1;
        } else if (playerId == 2) {
            return player2;
        } else {
            throw new IllegalArgumentException("Invalid player id");
        }
    }

    public boolean canStart() {
        return player1 != null && player2 != null && player1.isReady() && player2.isReady();
    }

    public List<FieldState> getBoard(int playerId) {
        return getPlayer1().isPlayer1() ? board1 : board2;
    }
}
