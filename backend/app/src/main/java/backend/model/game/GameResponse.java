package backend.model.game;

import backend.model.board.BoardResponse;
import backend.model.player.PlayerResponse;

// GameResponse contains public fields returned to the user in the API response
public class GameResponse {
    // Unique game id
    public final String id;

    // Game host
    public PlayerResponse host;

    // Game opponent
    public PlayerResponse opponent;

    // Board
    public final BoardResponse board;

    public GameResponse(Game game) {
        this.id = game.id;
        this.host = new PlayerResponse(game.host);
        if (game.opponent != null)
            this.opponent = new PlayerResponse(game.opponent);
        this.board = new BoardResponse(game.board);
    }
}
