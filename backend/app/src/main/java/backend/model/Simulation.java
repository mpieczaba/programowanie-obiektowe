package backend.model;

import java.util.Map;

import backend.model.board.Board;
import backend.model.board.BoardResponse;
import backend.model.turn.TurnState;
import backend.model.unit.UnitResponse;
import backend.model.unit.UnitType;
import io.javalin.websocket.WsContext;

public class Simulation extends TurnState {
    public Simulation(Board board, Map<WsContext, String> playerContexts) {
        super(board, playerContexts);
    }

    public void run(int tick) {
        this.board.units.values().forEach(u -> {
            if (!u.isAlive()) {
                this.playerContexts.keySet().stream().filter(c -> c.session.isOpen())
                        .forEach(c -> c.send(new WsResponse<UnitResponse>("unit_removed", new UnitResponse(u))));

                this.board.units.remove(u.id);
            }

            if (u.type == UnitType.CASTLE)
                return;

            u.findTarget(this.board);

            if (tick % u.attackSpeed == 0)
                u.giveDamage();

            if (tick % u.movementSpeed == 0)
                u.move();
        });

        this.playerContexts.keySet().stream().filter(c -> c.session.isOpen())
                .forEach(c -> c.send(new WsResponse<BoardResponse>("session_tick", new BoardResponse(this.board))));
    }
}
