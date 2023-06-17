package backend.model;

import java.util.ArrayList;

public class BoardResponse {
    public final ArrayList<Unit> units;

    public BoardResponse(Board board) {
        this.units = new ArrayList<>(board.units.values());
    }
}
