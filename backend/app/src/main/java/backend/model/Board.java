package backend.model;

import java.util.concurrent.ConcurrentHashMap;

import org.javatuples.Pair;

public class Board {
    private final Pair<Integer, Integer> dimension = new Pair<Integer, Integer>(11, 11);

    // Represents entities located on a 2D plane of the board
    // NOTE: Hashmap currently doesn't work for Point - it compares Points by
    // address (TODO)
    // NOTE2: do we really need thread-safety here?
    private final ConcurrentHashMap<Pair<Integer, Integer>, Entity> tiles = new ConcurrentHashMap<>();

    // Place a new entity on the board
    public void placeNewEntity(Pair<Integer, Integer> position, Entity entity) throws Exception {
        if (position.getValue0() > this.dimension.getValue0() || position.getValue1() > this.dimension.getValue1())
            throw new Exception("Position is outside of the board!");

        if (this.tiles.putIfAbsent(position, entity) != null)
            throw new Exception("Tile is not empty!");
        tiles.put(position, entity);
    }

    // Get entity by position
    public Entity getEntityByPosition(Pair<Integer, Integer> position) {
        return this.tiles.get(position);
    }
}
