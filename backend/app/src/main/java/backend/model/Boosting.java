package backend.model;

import java.util.Map;

import backend.model.board.Board;
import backend.model.turn.TurnState;
import io.javalin.websocket.WsContext;

public class Boosting extends TurnState {
    public Boosting(Board board, Map<WsContext, String> playerContexts) {
        super(board, playerContexts);
    }

    public void run(int tick) {
        // TODO: Implement boosting part of the turn
    }
}
