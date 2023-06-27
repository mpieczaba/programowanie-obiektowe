package backend.model.player;

import com.github.shamil.Xid;

// Player represents each player of the game
public class Player {
    // Unique player id
    public final String id = Xid.get().toString();

    // Player's nickname
    protected final String nickname;

    public Player(String nickname) {
        this.nickname = nickname;
    }
}
