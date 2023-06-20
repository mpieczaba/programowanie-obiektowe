package backend.model.unit;

import org.javatuples.Pair;

import backend.model.board.Board;
import backend.model.entity.Entity;
import backend.model.player.Player;

// Unit is an abstract representation of a fighting character
public abstract class Unit extends Entity {
    // Represents range of the character
    protected final int range;

    // Damage given by a single hit
    public int damage;

    // Ticks between hits
    public final int attackSpeed;

    // Value represented in ticks that defines time in which unit moves from one
    // tile to another
    public final int movementSpeed;

    // Unit's target
    protected Unit target;

    public Unit(Player owner, Pair<Integer, Integer> position, int range, int damage, int attackSpeed,
            int movementSpeed, Unit target) {
        super(owner, position);

        this.range = range;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.movementSpeed = movementSpeed;
        this.target = target;
    }
    
    public abstract void findTarget(Board board);

    // Logic under boosting damage for a given unit class
    public abstract void boostDamage();

    // Logic under boosting movement speed for a given unit class
    public abstract void boostMovementSpeed();

    // Logic under boosting attack speed for a given unit class
    public abstract void boostAttackSpeed();

    public void giveDamage() {
        this.target.takeDamage(this.damage);
    }

    public void move() {

    }
}
