package backend.model;

// Unit is an abstract representation of a fighting character
public abstract class Unit extends Entity {
    // Represents range of the character
    protected final int range;

    // Damage given by a single hit
    protected final int damage;

    // Ticks between hits
    protected final int attackSpeed;

    // Value represented in ticks that defines time in which unit moves from one
    // tile to another
    protected final int movementSpeed;

    public Unit(Player owner, Point position, int range, int damage, int attackSpeed, int movementSpeed) {
        super(owner, position);

        this.range = range;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.movementSpeed = movementSpeed;
    }

    // Logic under boosting damage for a given unit class
    public abstract void boostDamage();

    // Logic under boosting movement speed for a given unit class
    public abstract void boostMovementSeed();

    // Logic under boosting attack speed for a given unit class
    public abstract void boostAttackSpeed();
}
