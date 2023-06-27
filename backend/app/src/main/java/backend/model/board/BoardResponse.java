package backend.model.board;

import java.util.ArrayList;
import java.util.stream.Collectors;

import backend.model.unit.UnitResponse;

public class BoardResponse {
    public final ArrayList<UnitResponse> units;

    public BoardResponse(Board board) {
        this.units = board.units.values().stream().map(u -> new UnitResponse(u))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
