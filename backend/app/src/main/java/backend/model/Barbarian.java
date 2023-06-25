package backend.model;

import org.javatuples.Pair;

import backend.model.board.Board;
import backend.model.player.Player;
import backend.model.unit.Unit;
import backend.model.unit.UnitType;

public class Barbarian extends Unit {
    static int startRange = 1;
    static int startDamage = 3;
    static int startAttackSpeed = 30;
    static int startMovementSpeed = 30;
   
    public Barbarian(Player owner, Pair<Integer, Integer> position, Unit target) {
        super(owner, position, startRange, startDamage, startAttackSpeed, startMovementSpeed, target, UnitType.BARBARIAN);
    }

    // try to attack opponent units located at supplied offest from current position
    private void tryAttack(Board board, int offset0, int offset1) {
        var unit = board.getUnitByPosition(Pair.with(position.getValue0()+offset0, position.getValue1()+offset1));
        if(unit.isPresent() && unit.get().owner != this.owner)
            unit.get().takeDamage(this.damage);
    }

    @Override
    public void giveDamage(Board board) {
        tryAttack(board, 1, 1);
        tryAttack(board, 1, 0);
        tryAttack(board, 1, -1);

        tryAttack(board, 0, 1);
        tryAttack(board, 0, -1);

        tryAttack(board, -1, 1);
        tryAttack(board, -1, 0);
        tryAttack(board, -1, -1);
    }

    @Override
    public void boostDamage() {
        this.damage += 10;
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
