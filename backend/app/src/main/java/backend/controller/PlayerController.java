package backend.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.util.Optional;

import backend.model.Player;
import backend.repository.Repository;

// PlayerController handles and processes requests from /players endpoints 
public class PlayerController extends Controller {
    public PlayerController(Repository repository) {
        super(repository);
    }

    // Get player by id
    public void getById(Context ctx, String id) {
        Optional<Player> player = this.repository.players.getById(id);

        player.map(ctx::json).orElseThrow(() -> {
            throw new NotFoundResponse("Player not found");
        });
    }
}
