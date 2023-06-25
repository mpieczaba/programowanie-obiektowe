package backend.model.unit;

import backend.model.pair.PairResponse;
import backend.model.player.PlayerResponse;

public class UnitResponse {
    public final String id;
    public PairResponse position;
    public int hp;
    public PlayerResponse owner;
    public int damage;
    public int attackSpeed;
    public int movementSpeed;
    public int range;
    public UnitType type;

    public UnitResponse(Unit unit) {
        this.id = unit.id;
        this.position = new PairResponse(unit.position);
        this.hp = unit.hp;
        this.owner = new PlayerResponse(unit.owner);
        this.damage = unit.damage;
        this.attackSpeed = unit.attackSpeed;
        this.movementSpeed = unit.movementSpeed;
        this.range = unit.range;
        this.type = unit.type;
    }
}
