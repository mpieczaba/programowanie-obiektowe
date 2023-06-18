package backend.model.entity;

import backend.model.PairResponse;

public abstract class EntityResponse {
    public final String id;

    public PairResponse position;

    public int hp;

    public EntityResponse(Entity entity) {
        this.id = entity.id;
        this.position = new PairResponse(entity.position);
        this.hp = entity.hp;
    }
}
