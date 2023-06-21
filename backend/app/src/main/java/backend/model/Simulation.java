package backend.model;

import java.util.Map.Entry;

import backend.model.board.Board;
import backend.model.turn.TurnState;
import backend.model.unit.Unit;
import backend.model.unit.UnitType;

public class Simulation extends TurnState {
    public Simulation(Board board) {
        super(board);
    }

    public void run(int tick) {
        for (Entry<String, Unit> e : board.units.entrySet()) {
            var unit = e.getValue();
            if (unit.type == UnitType.CASTLE)
                continue;

            unit.findTarget(board);
            if (tick % unit.attackSpeed == 0) {
                unit.giveDamage();
            }
            if (tick % unit.movementSpeed == 0) {
                unit.move();
            }

            if (!unit.target.isAlive())
                board.units.remove(unit.target.id);
        }
    }
}
