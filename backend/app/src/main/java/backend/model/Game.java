package backend.model;

import com.github.shamil.Xid;

// Game model
public class Game {
    // Unique game id
    public final String id = Xid.get().toString();

    // Game master
    public final Player gameMaster;

    public Game(Player gameMaster) {
        this.gameMaster = gameMaster;
    }
}
