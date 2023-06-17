package backend.model;

public class Turn {
    // Turn tick
    private int tick = 0;

    // Reference to the game board
    private final Board board;

    // Turn state
    private TurnState state;

    public Turn(Board board) {
        this.board = board;
        this.state = new Boosting(board);
    }

    public void nextTick() {
        this.tick++;

        // TODO: Add looping through entities and updating their state, etc.
    }
}
