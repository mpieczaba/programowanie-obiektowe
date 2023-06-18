package backend.model.entity;

import org.javatuples.Pair;

import com.github.shamil.Xid;

import backend.model.player.Player;

public abstract class Entity {
    // Unique game id
    public final String id = Xid.get().toString();

    // x and y coordinates of the entity
    protected Pair<Integer, Integer> position;

    // Entity health points
    protected int hp;

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
