package backend.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import backend.model.Game;

// GameRepository contains in-memory store of game instances
public class GameRepository {
    // Stores game instances with id as a key
    private static final Map<String, Game> games = new ConcurrentHashMap<String, Game>();

    // Get user by id
    public Optional<Game> getById(String id) {
        return Optional.ofNullable(games.get(id));
    }

    // Create a new game
    public Game create() {
        Game game = new Game();

        // Store game in the repository
        games.put(game.id, game);

        return game;
    }
}
