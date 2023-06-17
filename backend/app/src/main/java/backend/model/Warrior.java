package backend.model;

public class Warrior extends Unit {
    public Warrior(Player owner, Point position, Unit target) {
        super(owner, position, 1, 10, 10, 30, target);
    }

    @Override
    public void boostDamage() {
        // TODO: Implement boosting damage logic
    }

    @Override
    public void boostMovementSpeed() {
        // TODO: Implement boosting movement speed logic

    }

    @Override
    public void boostAttackSpeed() {
        // TODO: Implement boosting attack speed logic
    }
}
