package backend.model.unit;

import backend.model.entity.EntityResponse;

public class UnitResponse extends EntityResponse {
    public int damage;

    public UnitResponse(Unit unit) {
        super(unit);

        this.damage = unit.damage;
    }
}
