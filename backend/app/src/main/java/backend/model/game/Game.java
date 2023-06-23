package backend.model.game;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.javatuples.Pair;

import com.github.shamil.Xid;

import backend.model.Castle;
import backend.model.WsResponse;
import backend.model.board.Board;
import backend.model.player.Player;
import backend.model.turn.Turn;

import io.javalin.websocket.WsContext;

// Game model
public class Game {
    // Unique game id
    public final String id = Xid.get().toString();

    // Game host
    public final Player host;

    // Game opponent
    public Player opponent;

    // Player contexts
    public final Map<WsContext, String> playerContexts = new ConcurrentHashMap<>();

    // Game board
    public final Board board = new Board();

    // Current game turn
    private Turn currentTurn = new Turn(board, this.playerContexts);

    // Game state
    private GameState state = GameState.INITIATED;

    // Ticks per second
    private static final int TPS = 30;

    public Game(Player host) {
        // Set player as a host
        this.host = host;

        // Put host's castle on the board
        try {
            this.board.placeNewUnit(new Castle(host, Pair.with(4, 0)));
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

                this.playerContexts.keySet().stream().filter(c -> c.session.isOpen())
                        .forEach(c -> c.send(new WsResponse<>("game_started")));

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

                this.playerContexts.keySet().stream().filter(c -> c.session.isOpen())
                        .forEach(c -> c.send(new WsResponse<>("game_paused")));
                break;

            default:
                throw new Exception("Cannot pause the game!");
        }
    }

    // Adds opponent player to the game
    public Castle addOpponent(Player opponent) throws Exception {
        if (this.opponent != null)
            throw new Exception("Opponent is not null");

        this.opponent = opponent;

        Castle opponentCastle = new Castle(opponent, Pair.with(4, 11));

        this.board.placeNewUnit(opponentCastle);

        return opponentCastle;
    }

    public void addPlayerWithContext(WsContext ctx, String id) {
        this.playerContexts.put(ctx, id);
    }
}
