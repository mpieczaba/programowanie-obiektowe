package backend.model.unit;

import java.util.Map.Entry;

import org.javatuples.Pair;

import com.github.shamil.Xid;

import backend.model.board.Board;
import backend.model.player.Player;

// Unit is an abstract representation of a fighting character
public abstract class Unit {
    // Unique game id
    public final String id = Xid.get().toString();

    // x and y coordinates of the entity
    public Pair<Integer, Integer> position;

    // Entity health points
    protected int hp;

    // Entity owner
    public final Player owner;

    // Represents range of the character
    protected final int range;

    // Damage given by a single hit
    public int damage;

    // Ticks between hits
    public final int attackSpeed;

    // Value represented in ticks that defines time in which unit moves from one
    // tile to another
    public final int movementSpeed;

    public final UnitType type;

    // Take damage form the opponent
    public void takeDamage(int damage) {
        // TODO: Rewrite taking damage logic
        this.hp -= damage;
    }

    // Return true when entity is alive and false when entity is annihilated
    public boolean isAlive() {
        return this.hp > 0;
    }

    // Unit's target
    public Unit target;

    public Unit(Player owner, Pair<Integer, Integer> position, int range, int damage, int attackSpeed,
            int movementSpeed, Unit target, UnitType type) {
        this.position = position;
        this.owner = owner;
        this.range = range;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.movementSpeed = movementSpeed;
        this.target = target;
        this.type = type;
        this.hp = 100;
    }

    // Logic under boosting damage for a given unit class
    public abstract void boostDamage();

    // Logic under boosting movement speed for a given unit class
    public abstract void boostMovementSpeed();

    // Logic under boosting attack speed for a given unit class
    public abstract void boostAttackSpeed();

    public void giveDamage() {
        if (Math.abs(this.position.getValue0() - this.target.position.getValue0()) <= this.range
                && Math.abs(this.position.getValue1() - this.target.position.getValue1()) <= this.range)
            this.target.takeDamage(this.damage);
    }

    // simple targeting scheme used by most units.
    // units that move differently (like castle that doesn't move at all) should
    // override it
    public void findTarget(Board board) {
        int minDist = Integer.MAX_VALUE;
        // TODO: cycle through castles
        for (Entry<String, Unit> entry : board.units.entrySet()) {
            var unit = entry.getValue();
            if (this.owner == unit.owner)
                continue;
            int xdist = Math.abs(this.position.getValue0() - unit.position.getValue0());
            int ydist = Math.abs(this.position.getValue1() - unit.position.getValue1());
            int dist = Math.max(xdist, ydist);// https://en.wikipedia.org/wiki/Chebyshev_distance
            if (dist < minDist) {
                minDist = dist;
                this.target = unit;
            }
        }
    }

    // simple moving scheme used by most units
    // units that move differently (like castle that doesn't move at all) should
    // override it
    public void move() {
        if (this.target == null)
            return;
        int cur0 = this.position.getValue0(), cur1 = this.position.getValue1();
        int tar0 = this.target.position.getValue0(), tar1 = this.target.position.getValue1();

        // move in whatever manner that brings you closer to target (horiz/vert/diag)
        if (tar0 > cur0)
            ++cur0;
        if (tar0 < cur0)
            --cur0;
        if (tar1 > cur1)
            ++cur1;
        if (tar1 < cur1)
            --cur1;

        if (cur0 == tar0 && cur1 == tar0) {
            // moving would "step onto target". Do nothing
        } else {
            this.position = new Pair<Integer, Integer>(cur0, cur1);
        }
    }
}
