package backend.model;

public abstract class Entity {
    // x and y coordinates of the entity
    public Point position;

    // Entity health points
    public int hp;

    // Entity owner
    protected final Player owner;

    public Entity(Player owner, Point position) {
        this.position = position;
        this.owner = owner;
    }

    // Take damage form the opponent
    public void takeDamage(int damage) {
        // TODO: Rewrite taking damage logic
        this.hp -= damage;
    }

    // Return true when entity is alive and false when entity is annihilated
    public boolean isAlive() {
        return this.hp > 0;
    }
}
