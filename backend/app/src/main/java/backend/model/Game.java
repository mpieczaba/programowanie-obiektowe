package backend.model;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.javatuples.Pair;

import com.github.shamil.Xid;

// Game model
public class Game {
    // Unique game id
    public final String id = Xid.get().toString();

    // Game host
    public final Player host;

    // Players
    public final ArrayList<Player> players = new ArrayList<Player>();

    // Game board
    public final Board board = new Board();

    // Current game turn
    private Turn currentTurn = new Turn(board);

    // Game state
    private GameState state = GameState.INITIATED;

    // Ticks per second
    private static final int TPS = 30;

    public Game(Player host) {
        // Set player as a host and add it to the players list
        this.host = host;
        this.players.add(host);

        // Put host's castle on the board
        try {
            // NOTE: storing position both in map and in entity feels kinda weird to me
            this.board.placeNewCastle(new Pair<Integer, Integer>(6, 11),
                    new Castle(host, new Pair<Integer, Integer>(6, 11)));
        } catch (Exception e) {
            System.out.println("Something went terribly wrong when putting host's castle on the board!");
            System.exit(-1);
        }
    }

    // LoopTask defines the game loop task
    private class LoopTask extends TimerTask {
        // Reference to the game
        private Game game;

        public LoopTask(Game game) {
            this.game = game;
        }

        @Override
        public void run() {
            switch (this.game.state) {
                case FINISHED:
                case PAUSED:
                    this.cancel();
                    break;

                default:
                    this.game.currentTurn.nextTick();
            }
        }
    }

    // Starts the game loop
    private void loop() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new LoopTask(this), 0, 1000 / Game.TPS);
    }

    // Starts the game
    public void start() throws Exception {
        switch (this.state) {
            case INITIATED:
            case PAUSED:
                this.state = GameState.RUNNING;

                // Start game loop
                this.loop();
                break;

            default:
                throw new Exception("Cannot start the game!");
        }
    }

    // Pauses the game
    public void pause() throws Exception {
        switch (this.state) {
            case RUNNING:
                this.state = GameState.PAUSED;
                break;

            default:
                throw new Exception("Cannot pause the game!");
        }
    }

    // Adds player to the game
    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
