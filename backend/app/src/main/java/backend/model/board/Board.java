package backend.model.board;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.javatuples.Pair;

import backend.model.unit.Unit;

public class Board {
    // Board dimension
    private final static Pair<Integer, Integer> dimension = Pair.with(9, 12);

    // Units on the board
    public final ConcurrentHashMap<String, Unit> units = new ConcurrentHashMap<>();

    // Place a new unit on the board
    public void placeNewUnit(Unit unit) throws Exception {
        if (unit.position.getValue0() >= dimension.getValue0()
                || unit.position.getValue1() >= dimension.getValue1())
            throw new Exception("Position is outside of the board!");

        if (this.units.putIfAbsent(unit.id, unit) != null)
            throw new Exception("Unit already exists!");
    }

    // Get unit by position
    public Optional<Unit> getUnitByPosition(Pair<Integer, Integer> position) {
        return units.values().stream().filter(u -> u.position.equals(position)).findFirst();
    }
}
