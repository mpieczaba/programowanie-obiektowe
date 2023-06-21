package backend.model.board;

import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.javatuples.Pair;

import backend.model.unit.Unit;

public class Board {
    private final Pair<Integer, Integer> dimension = new Pair<Integer, Integer>(9, 12);

    // unitId:unit
    public final ConcurrentHashMap<String, Unit> units = new ConcurrentHashMap<>();

    // Place a new unit on the board
    public void placeNewUnit(Unit unit) throws Exception {
        if (unit.position.getValue0() >= this.dimension.getValue0() || unit.position.getValue1() >= this.dimension.getValue1())
            throw new Exception("Position is outside of the board!");

        if (this.units.putIfAbsent(unit.id, unit) != null)
            throw new Exception("Unit already exists!");
    }

    // Get unit by position
    public Optional<Unit> getUnitByPosition(Pair<Integer, Integer> position) throws Exception {
    	for (Entry<String, Unit> e : units.entrySet()) {
    		if(e.getValue().position.equals(position)) return Optional.of(e.getValue()); 
    	}
    	return Optional.empty();
    }
}
