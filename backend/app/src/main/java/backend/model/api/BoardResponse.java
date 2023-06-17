package backend.model.api;

import java.util.ArrayList;

import backend.model.Board;
import backend.model.Unit;

public class BoardResponse {
    public final ArrayList<Unit> units;

    public BoardResponse(Board board) {
        this.units = new ArrayList<>(board.units.values());
    }
}
