package backend.model.board;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.javatuples.Pair;

import backend.model.castle.Castle;
import backend.model.unit.Unit;

public class Board {
    private final Pair<Integer, Integer> dimension = new Pair<Integer, Integer>(9, 12);

    // unitId:unit
    public final ConcurrentHashMap<String, Unit> units = new ConcurrentHashMap<>();

    // Represents entities located on a 2D plane of the board with their id
    private final ConcurrentHashMap<Pair<Integer, Integer>, String> tiles = new ConcurrentHashMap<>();

    // Place a new unit on the board
    public void placeNewUnit(Pair<Integer, Integer> position, Unit unit) throws Exception {
    	if (position.getValue0() >= this.dimension.getValue0() || position.getValue1() >= this.dimension.getValue1())
            throw new Exception("Position is outside of the board!");

        if (this.tiles.putIfAbsent(position, unit.id) != null)
            throw new Exception("Tile is not empty!");

        if (this.units.putIfAbsent(unit.id, unit) != null)
            throw new Exception("Unit already exists!");
    }

    // Get unit by position
    public Optional<Unit> getUnitByPosition(Pair<Integer, Integer> position) throws Exception {
    	if (!this.tiles.containsKey(position))
            throw new Exception("Entity is not on the board");

        return Optional.ofNullable(this.units.get(this.tiles.get(position)));
    }
}
