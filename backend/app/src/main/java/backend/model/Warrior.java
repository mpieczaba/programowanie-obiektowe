package backend.model;

import java.util.Map.Entry;

import org.javatuples.Pair;

import backend.model.board.Board;
import backend.model.player.Player;
import backend.model.unit.Unit;

public class Warrior extends Unit {
    public Warrior(Player owner, Pair<Integer, Integer> position, Unit target) {
        super(owner, position, 1, 10, 10, 30, target);
    }

    @Override
    public void boostDamage() {
        this.damage += 10;
    }

    @Override
    public void boostMovementSpeed() {
        // TODO: Implement boosting movement speed logic

    }

    @Override
    public void boostAttackSpeed() {
        // TODO: Implement boosting attack speed logic
    }
    @Override
    public void findTarget(Board board) {
    	int minDist = Integer.MAX_VALUE;
    	// TODO: cycle through castles
    	for (Entry<String, Unit> entry : board.units.entrySet()) {
    		var unit = entry.getValue();
    		if(this.owner == unit.owner) continue;
    		int xdist = Math.abs(this.position.getValue0() - unit.position.getValue0());
    		int ydist = Math.abs(this.position.getValue1() - unit.position.getValue1());
    		int dist = Math.max(xdist,ydist);// https://en.wikipedia.org/wiki/Chebyshev_distance
    		if(dist < minDist) {
    			minDist = dist;
    			target = unit;
    		}
    	}
    }
}
