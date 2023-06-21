package backend.model.unit;

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
    protected Unit target;

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
    }

    public abstract void findTarget(Board board);

    // Logic under boosting damage for a given unit class
    public abstract void boostDamage();

    // Logic under boosting movement speed for a given unit class
    public abstract void boostMovementSpeed();

    // Logic under boosting attack speed for a given unit class
    public abstract void boostAttackSpeed();

    public void giveDamage() {
        // TODO: check range
        this.target.takeDamage(this.damage);
    }

    
    public void move() {
    	System.out.println("chuj dupa japa pipa");
    	int cur0 = position.getValue0(), cur1 = position.getValue1();
    	int tar0 = target.position.getValue0(), tar1 = target.position.getValue1();
    	
    	// move in whatever manner that brings you closer to target (horiz/vert/diag)
    	if(cur0 > tar0) ++cur0;
    	if(cur0 < tar0) --cur0;
    	if(cur1 > tar1) ++cur1;
    	if(cur1 < tar1) --cur1;
    	
    	if(cur0 == tar0 && cur1 == tar0) {
    		// moving would "step onto target". Do nothing
    	} else {
    		// zaktualizuj fielda pos i przenieś w hashmapie.
    	}
    }
}
//position = position.setValue0(cur0).setValue1(cur1);
