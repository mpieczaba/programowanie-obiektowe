package backend.model;

import java.util.ArrayList;

import com.github.shamil.Xid;

// Game model
public class Game {
    // Unique game id
    public final String id = Xid.get().toString();

    // Game master
    public final Player gameMaster;

    // Players
    public final ArrayList<Player> players = new ArrayList<Player>();

    public Game(Player gameMaster) {
        this.gameMaster = gameMaster;
        this.players.add(gameMaster);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
