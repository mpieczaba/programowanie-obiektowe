package backend.model.turn;

import backend.model.Boosting;
import backend.model.Simulation;
import backend.model.board.Board;

public class Turn {
    // Turn tick
    private int tick = 0;

    // Reference to the game board
    private final Board board;

    // Turn state
    private TurnState state;

    public Turn(Board board) {
        this.board = board;
    }

    public void nextTick() {
        // System.out.println(this.tick);
    	
        if (this.tick == 0)
            this.state = new Boosting(board);
        if (this.tick == 500)
            this.state = new Simulation(board);

        this.state.run(tick);

        this.tick = ++tick % 1000;
    }
}
