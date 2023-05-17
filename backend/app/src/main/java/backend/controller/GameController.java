package backend.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

import java.util.Optional;

import backend.model.Game;
import backend.model.GameInput;
import backend.repository.GameRepository;

// GameController handles and process requests from /games endpoints 
public class GameController {
    private final GameRepository games = new GameRepository();

    // Get game by id
    public void getById(Context ctx, String id) {
        Optional<Game> game = this.games.getById(id);

        game.map(ctx::json).orElseThrow(() -> {
            throw new NotFoundResponse("Game not found");
        });
    }

    // Create a new game
    public void create(Context ctx) {
        // TODO: Add body validation
        GameInput input = ctx.bodyValidator(GameInput.class)
                // .check(g -> g.test.equals("123"), "'test' is not equal to '123'")
                .get();

        Game game = this.games.create();

        ctx.json(game).status(HttpStatus.CREATED);
    }
}
