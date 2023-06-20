package backend.model.board;

import java.util.ArrayList;
import java.util.stream.Collectors;

import backend.model.castle.CastleResponse;
import backend.model.unit.UnitResponse;

public class BoardResponse {
    public final ArrayList<UnitResponse> units;

    public final ArrayList<CastleResponse> castles;

    public BoardResponse(Board board) {
        this.units = board.units.values().stream().map(u -> new UnitResponse(u))
                .collect(Collectors.toCollection(ArrayList::new));

        this.castles = board.castles.values().stream().map(c -> new CastleResponse(c))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
