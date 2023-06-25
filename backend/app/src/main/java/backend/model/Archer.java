package backend.model;

import org.javatuples.Pair;

import backend.model.player.Player;
import backend.model.unit.Unit;
import backend.model.unit.UnitType;

public class Archer extends Unit {
    public Archer(Player owner, Pair<Integer, Integer> position, Unit target) {
        super(owner, position, 4, 1, 20, 30, target, UnitType.ARCHER);
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
}
