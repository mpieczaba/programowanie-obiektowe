package backend.model.game;

import java.util.ArrayList;
import java.util.stream.Collectors;

import backend.model.board.BoardResponse;
import backend.model.player.PlayerResponse;

// GameResponse contains public fields returned to the user in the API response
public class GameResponse {
    // Unique game id
    public final String id;

    // Game host
    public final PlayerResponse host;

    // Players
    public final ArrayList<PlayerResponse> players;

    // Board
    public final BoardResponse board;

    public GameResponse(Game game) {
        this.id = game.id;
        this.host = new PlayerResponse(game.host);
        this.players = game.players.stream().map(p -> new PlayerResponse(p))
                .collect(Collectors.toCollection(ArrayList::new));
        this.board = new BoardResponse(game.board);
    }
}
