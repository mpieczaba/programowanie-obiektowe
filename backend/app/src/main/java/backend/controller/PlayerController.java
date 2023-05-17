package backend.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.util.Optional;

import backend.model.Player;
import backend.repository.Repository;

// PlayerController handles and process requests from /players endpoints 
public class PlayerController {
    private final Repository repository;

    public PlayerController(Repository repository) {
        this.repository = repository;
    }

    // Get player by id
    public void getById(Context ctx, String id) {
        Optional<Player> player = this.repository.players.getById(id);

        player.map(ctx::json).orElseThrow(() -> {
            throw new NotFoundResponse("Player not found");
        });
    }
}
