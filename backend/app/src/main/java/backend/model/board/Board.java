package backend.model.board;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.javatuples.Pair;

import backend.model.castle.Castle;
import backend.model.entity.Entity;
import backend.model.unit.Unit;

public class Board {
    private final Pair<Integer, Integer> dimension = new Pair<Integer, Integer>(11, 11);

    // Castles on the board
    public final ConcurrentHashMap<String, Castle> castles = new ConcurrentHashMap<>();

    // Units on the board
    public final ConcurrentHashMap<String, Unit> units = new ConcurrentHashMap<>();

    // Represents entities located on a 2D plane of the board with their id
    private final ConcurrentHashMap<Pair<Integer, Integer>, String> tiles = new ConcurrentHashMap<>();

    // Place a new entity on the board
    private void placeNewEntity(Pair<Integer, Integer> position, Entity entity) throws Exception {
        if (position.getValue0() > this.dimension.getValue0() || position.getValue1() > this.dimension.getValue1())
            throw new Exception("Position is outside of the board!");

        if (this.tiles.putIfAbsent(position, entity.id) != null)
            throw new Exception("Tile is not empty!");
    }

    // Get entity by position
    private String getEntityByPosition(Pair<Integer, Integer> position) throws Exception {
        if (!this.tiles.containsKey(position))
            throw new Exception("Entity is not on the board");

        return this.tiles.get(position);
    }

    // Place a new castle on the board
    public void placeNewCastle(Pair<Integer, Integer> position, Castle castle) throws Exception {
        this.placeNewEntity(position, castle);

        if (this.castles.putIfAbsent(castle.id, castle) != null)
            throw new Exception("Castle already exists!");
    }

    // Get castle by position
    public Optional<Castle> getCastleByPosition(Pair<Integer, Integer> position) throws Exception {
        return Optional.ofNullable(this.castles.get(this.getEntityByPosition(position)));
    }

    // Place a new unit on the board
    public void placeNewUnit(Pair<Integer, Integer> position, Unit unit) throws Exception {
        this.placeNewEntity(position, unit);

        if (this.units.putIfAbsent(unit.id, unit) != null)
            throw new Exception("Unit already exists!");
    }

    // Get unit by position
    public Optional<Unit> getUnitByPosition(Pair<Integer, Integer> position) throws Exception {
        return Optional.ofNullable(this.units.get(this.getEntityByPosition(position)));
    }
}
