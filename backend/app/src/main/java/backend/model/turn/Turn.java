package backend.model.turn;

import java.util.Map;

import backend.model.Boosting;
import backend.model.Simulation;
import backend.model.WsResponse;
import backend.model.board.Board;
import io.javalin.websocket.WsContext;

public class Turn {
    // Turn tick
    private int tick = 0;

    // Reference to the game board
    private final Board board;

    // Turn state
    private TurnState state;

    // Player contexts
    private final Map<WsContext, String> playerContexts;

    public Turn(Board board, Map<WsContext, String> playerContexts) {
        this.board = board;
        this.playerContexts = playerContexts;
    }

    public void nextTick() {
        if (this.tick == 0) {
            this.state = new Boosting(board, this.playerContexts);

            this.playerContexts.keySet().stream().filter(c -> c.session.isOpen())
                    .forEach(c -> c.send(new WsResponse<>("boosting")));
        }

        if (this.tick == 500) {
            this.state = new Simulation(board, this.playerContexts);

            this.playerContexts.keySet().stream().filter(c -> c.session.isOpen())
                    .forEach(c -> c.send(new WsResponse<>("simulation")));
        }

        this.state.run(tick);

        this.tick = ++tick % 1000;
    }
}
