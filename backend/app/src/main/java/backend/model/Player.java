package backend.model;

import com.github.shamil.Xid;

// Player represents each player of the game
public class Player {
    // Unique player id
    public final String id = Xid.get().toString();

    // Player's nickname
    public final String nickname;

    public Player(String nickname) {
        this.nickname = nickname;
    }
}
