package backend.model.unit;

import backend.model.PairResponse;
import backend.model.player.PlayerResponse;

public class UnitResponse {
    public final String id;
    public PairResponse position;
    public int hp;
    public PlayerResponse owner;
    public int damage;
    public int range;

    public UnitResponse(Unit unit) {
        this.id = unit.id;
        this.position = new PairResponse(unit.position);
        this.hp = unit.hp;
        this.owner = new PlayerResponse(unit.owner);
        this.damage = unit.damage;
        this.range = unit.range;
    }
}
