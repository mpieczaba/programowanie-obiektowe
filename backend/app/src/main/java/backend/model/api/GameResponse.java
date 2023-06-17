package backend.model;

import java.util.ArrayList;

// GameResponse contains public fields returned to the user in the API response
public class GameResponse {
    // Unique game id
    public final String id;

    // Game host
    public final Player host;

    // Players
    public final ArrayList<Player> players;

    // Board
    public final BoardResponse board;

    public GameResponse(Game game) {
        this.id = game.id;
        this.host = game.host;
        this.players = game.players;
        this.board = new BoardResponse(game.board);
    }
}
