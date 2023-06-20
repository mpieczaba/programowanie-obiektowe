package backend.model.castle;

import org.javatuples.Pair;

import backend.model.entity.Entity;
import backend.model.player.Player;

// Castle represents player's castle
public class Castle extends Entity {
    public Castle(Player owner, Pair<Integer, Integer> position) {
        super(owner, position);
    }
}
