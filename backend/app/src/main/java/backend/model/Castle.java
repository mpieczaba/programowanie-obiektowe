package backend.model;

import org.javatuples.Pair;

// Castle represents player's castle
public class Castle extends Entity {
    public Castle(Player owner, Pair<Integer, Integer> position) {
        super(owner, position);
    }
}
