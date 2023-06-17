package backend.model;

public abstract class TurnState {
    // Reference to the game board
    protected final Board board;

    public TurnState(Board board) {
        this.board = board;
    }

    public abstract void run();
}
