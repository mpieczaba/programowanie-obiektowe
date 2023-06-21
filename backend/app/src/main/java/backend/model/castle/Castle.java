package backend.model.castle;

import org.javatuples.Pair;

import backend.model.unit.Unit;
import backend.model.unit.UnitType;
import backend.model.board.Board;
import backend.model.player.Player;

// Castle represents player's castle
public class Castle extends Unit {
	public Castle(Player owner, Pair<Integer, Integer> position) {
		super(owner, position, 0, 0, 0, 0, null, UnitType.CASTLE);
	}

	@Override
	public void findTarget(Board board) {
	}

	@Override
	public void boostDamage() {
	}

	@Override
	public void boostMovementSpeed() {
	}

	@Override
	public void boostAttackSpeed() {
	}
}
