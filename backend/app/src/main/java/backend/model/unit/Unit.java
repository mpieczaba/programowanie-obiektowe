package backend.model.unit;

import org.javatuples.Pair;

import backend.model.entity.Entity;
import backend.model.player.Player;

// Unit is an abstract representation of a fighting character
public abstract class Unit extends Entity {
    // Represents range of the character
    protected final int range;

    // Damage given by a single hit
    public int damage;

    // Ticks between hits
    protected final int attackSpeed;

    // Value represented in ticks that defines time in which unit moves from one
    // tile to another
    protected final int movementSpeed;

    // Unit's target
    private Unit target;

    public Unit(Player owner, Pair<Integer, Integer> position, int range, int damage, int attackSpeed,
            int movementSpeed, Unit target) {
        super(owner, position);

        this.range = range;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.movementSpeed = movementSpeed;
        this.target = target;
    }

    // Logic under boosting damage for a given unit class
    public abstract void boostDamage();

    // Logic under boosting movement speed for a given unit class
    public abstract void boostMovementSpeed();

    // Logic under boosting attack speed for a given unit class
    public abstract void boostAttackSpeed();

    private void giveDamage() {
        this.target.takeDamage(this.damage);
    }

    public void nextMove() {

    }
}
