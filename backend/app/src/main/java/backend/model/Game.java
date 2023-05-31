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

    // Game board
    public final Board board = new Board();

    public Game(Player host) {
        // Set player as a host and add it to the players list
        this.host = host;
        this.players.add(host);

        // Put host's castle on the board
        try {
        	// NOTE: storing position both in map and in entity feels kinda weird to me
            this.board.placeNewEntity(new Point(6, 11), new Castle(host, new Point(6, 11)));
        } catch (Exception e) {
            System.out.println("Something went terribly wrong when putting host's castle on the board!");
            System.exit(-1);
        }
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
