package backend.model;

import java.util.concurrent.ConcurrentHashMap;

public class Board {
    private final Point dimension = new Point(11, 11);

    // Represents entities located on a 2D plane of the board
    // NOTE: Hashmap currently doesn't work for Point - it compares Points by address (TODO)
    // NOTE2: do we really need thread-safety here?
    private final ConcurrentHashMap<Point, Entity> tiles = new ConcurrentHashMap<>();

    // Place a new entity on the board
    public void placeNewEntity(Point position, Entity entity) throws Exception {
        if (position.x > this.dimension.x || position.y > this.dimension.y)
            throw new Exception("Position is outside of the board!");

        if (this.tiles.putIfAbsent(position, entity) != null)
            throw new Exception("Tile is not empty!");
       tiles.put(position, entity);
    }

    // Get entity by position
    public Entity getEntityByPosition(Point position) {
        return this.tiles.get(position);
    }
}
