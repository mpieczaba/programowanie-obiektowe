package backend.model;

import org.javatuples.Pair;

import backend.model.player.Player;
import backend.model.unit.Unit;
import backend.model.unit.UnitType;

public class Warrior extends Unit {
    public Warrior(Player owner, Pair<Integer, Integer> position, Unit target) {
        super(owner, position, 1, 5, 20, 30, target, UnitType.WARRIOR);
    }

    @Override
    public void boostDamage() {
        this.damage += 10;
    }

    @Override
    public void boostMovementSpeed() {
        this.movementSpeed += 5;
    }

    @Override
    public void boostAttackSpeed() {
        this.attackSpeed += 5;
    }
}
