package backend.model;

import com.github.shamil.Xid;

// Game model
public class Game {
    // Unique user id
    public final String id = Xid.get().toString();
}
