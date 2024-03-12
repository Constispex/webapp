package de.constispex.webapp.schiffeversenken.model;

public class Game {
    private final int id;
    private Player player1;
    private Player player2;
    private GameState state;
    private Player currPlayer;

    private FieldState[][] board1;
    private FieldState[][] board2;

    public Game(int id) {
        this.id = id;
        state = GameState.WAITING_FOR_PLAYERS;
        board1 = new FieldState[10][10];
        board2 = new FieldState[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board1[i][j] = FieldState.EMPTY;
                board2[i][j] = FieldState.EMPTY;
            }
        }

    }

    public Game(int id, Player player1, Player player2, GameState state) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.state = state;
    }

    public int getId() {
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
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public void addPlayer(Player player) {
        if (player1 == null) {
            player1 = player;
        } else if (player2 == null) {
            player2 = player;
        }
    }

    public void placeShip(Player player, Ship ship, Position position) {
        if (state != GameState.WAITING_FOR_SHIPS) {
            throw new IllegalStateException("Game has already started");
        }
        if (player.equals(player1)) {
            saveToBoard(ship, position, board1);
        } else if (player.equals(player2)) {
            saveToBoard(ship, position, board2);
        }
    }

    private void saveToBoard(Ship ship, Position position, FieldState[][] board) {
        if (ship.isVertical()) {
            for (int i = 0; i < ship.getSize(); i++) {
                board[position.getX()][position.getY() + i] = FieldState.SHIP;
            }
        } else {
            for (int i = 0; i < ship.getSize(); i++) {
                board[position.getX() + i][position.getY()] = FieldState.SHIP;
            }
        }
    }

    public GameState getGameState() {
        return state;
    }

    public AttackResult attack(Position position) {
        if (state != GameState.WAITING_FOR_MOVE) {
            throw new IllegalStateException("Game not in attack phase");
        }
        if (currPlayer.equals(player1)) {
            return getAttackResult(position, board2);
        } else if (currPlayer.equals(player2)) {
            return getAttackResult(position, board1);
        } else {
            return new AttackResult(false, "Fehler");
        }
    }

    private AttackResult getAttackResult(Position position, FieldState[][] board1) {
        if (board1[position.getX()][position.getY()] == FieldState.SHIP) {
            board1[position.getX()][position.getY()] = FieldState.HIT;
            return new AttackResult(true, "Getroffen");
        } else {
            board1[position.getX()][position.getY()] = FieldState.MISS;
            return new AttackResult(false, "Nicht Getroffen");
        }
    }
}
