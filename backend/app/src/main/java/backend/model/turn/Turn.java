package backend.model.turn;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import backend.model.Boosting;
import backend.model.Simulation;
import backend.model.WsResponse;
import backend.model.board.Board;
import backend.model.board.BoardResponse;
import io.javalin.websocket.WsContext;

public class Turn {
    // Turn tick
    private int tick = 0;

    // Reference to the game board
    private final Board board;

    // Turn state
    private TurnState state;

    public Turn(Board board) {
        this.board = board;
    }

    public void nextTick(Map<WsContext, String> playerContexts) {
        // System.out.println(this.tick);

        if (this.tick == 0) {
            this.state = new Boosting(board);

            playerContexts.keySet().forEach(c -> {
                if (c.session.isOpen())
                    c.send(new WsResponse<Object>("boosting", null));
            });
        }

        if (this.tick == 500) {
            this.state = new Simulation(board);

            playerContexts.keySet().forEach(c -> {
                if (c.session.isOpen())
                    c.send(new WsResponse<Object>("simulation", null));
            });
        }

        this.state.run(tick);

        if (this.tick >= 500)
            playerContexts.keySet().forEach(c -> {
                if (c.session.isOpen()) {
                    c.send(new WsResponse<BoardResponse>("session_tick", new BoardResponse(this.board)));
                }
            });

        this.tick = ++tick % 1000;
    }
}
