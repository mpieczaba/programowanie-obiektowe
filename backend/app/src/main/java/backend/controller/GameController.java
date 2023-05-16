package backend.controller;

import io.javalin.http.Context;

import java.util.Optional;

import backend.model.Game;
import backend.util.APIError;
import backend.util.APIResult;
import backend.repository.GameRepository;

// GameController handles and process request from /games endpoints 
public class GameController {
    private final GameRepository games = new GameRepository();

    // Get game by id
    public void getById(Context ctx, String id) {
        Optional<Game> game = games.getById(id);

        game.ifPresentOrElse(g -> ctx.json(new APIResult(g)), () -> {
            ctx.json(new APIError("Game not found!")).status(404);
        });
    }
}
