package backend;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import backend.model.game.Game;
import backend.model.player.Player;

// Repository contains in-memory store of game instances
public class Repository {
    // Stores game instances with id as a key
    private static final Map<String, Game> games = new ConcurrentHashMap<String, Game>();

    // Get game by id
    public Optional<Game> getGameById(String id) {
        return Optional.ofNullable(games.get(id));
    }

    // Create a new game
    public Game createGame(Player host) {
        Game game = new Game(host);

        // Store game in the repository
        games.put(game.id, game);

        return game;
    }
}
