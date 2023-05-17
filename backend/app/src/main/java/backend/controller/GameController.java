package backend.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

import java.util.Optional;

import backend.model.Game;
import backend.model.GameInput;
import backend.model.Player;
import backend.repository.Repository;

// GameController handles and process requests from /games endpoints 
public class GameController {
    private final Repository repository;

    public GameController(Repository repository) {
        this.repository = repository;
    }

    // Get game by id
    public void getById(Context ctx, String id) {
        Optional<Game> game = this.repository.games.getById(id);

        game.map(ctx::json).orElseThrow(() -> {
            throw new NotFoundResponse("Game not found");
        });
    }

    // Create a new game
    public void create(Context ctx) {
        GameInput input = ctx.bodyValidator(GameInput.class)
                .check(g -> g.gameMaster.nickname.length() > 3, "Nickname should contain at least four characters")
                .get();

        Player gameMaster = this.repository.players.create(input.gameMaster.nickname);
        Game game = this.repository.games.create(gameMaster);

        ctx.json(game).status(HttpStatus.CREATED);
    }
}
