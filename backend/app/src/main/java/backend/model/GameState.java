package backend.model;

// GameState represents inner state of the game  
public enum GameState {
    // A new game is created by the host but it hasn't been started
    INITIATED,
    // The game is started by the host
    RUNNING,
    // The game is paused by the host
    PAUSED,
    // The game is finished
    FINISHED
}
