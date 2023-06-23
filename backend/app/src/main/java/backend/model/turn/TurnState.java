package backend.model.turn;

import java.util.Map;

import backend.model.board.Board;
import io.javalin.websocket.WsContext;

public abstract class TurnState {
    // Reference to the game board
    protected final Board board;

    // Reference to player contexts
    protected final Map<WsContext, String> playerContexts;

    public TurnState(Board board, Map<WsContext, String> playerContexts) {
        this.board = board;
        this.playerContexts = playerContexts;
    }

    public abstract void run(int tick);
}
