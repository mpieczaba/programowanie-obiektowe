package backend.model;

import org.javatuples.Pair;

import com.github.shamil.Xid;

public abstract class Entity {
    // Unique game id
    public final String id = Xid.get().toString();

    // x and y coordinates of the entity
    public Pair<Integer, Integer> position;

    // Entity health points
    public int hp;

    // Entity owner
    protected final Player owner;

    public Entity(Player owner, Pair<Integer, Integer> position) {
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
