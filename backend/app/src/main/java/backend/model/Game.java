package backend.model;

import java.util.ArrayList;

import com.github.shamil.Xid;

// Game model
public class Game {
    // Unique game id
    public final String id = Xid.get().toString();

    // Game host
    public final Player host;

    // Players
    public final ArrayList<Player> players = new ArrayList<Player>();

    public Game(Player host) {
        this.host = host;
        this.players.add(host);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
