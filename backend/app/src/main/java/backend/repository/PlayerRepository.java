package backend.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import backend.model.player.Player;

// PlayerRepository contains in-memory store of player instances
public class PlayerRepository {
    // Stores player instances with id as a key
    private static final Map<String, Player> players = new ConcurrentHashMap<String, Player>();

    // Get player by id
    public Optional<Player> getById(String id) {
        return Optional.ofNullable(players.get(id));
    }

    // Create a new player
    public Player create(String nickname) {
        Player player = new Player(nickname);

        // Store player in the repository
        players.put(player.id, player);

        return player;
    }
}
