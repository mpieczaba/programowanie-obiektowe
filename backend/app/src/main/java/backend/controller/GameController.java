package backend.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

import java.util.Optional;

import backend.model.Game;
import backend.model.GameInput;
import backend.model.Player;
import backend.repository.Repository;

// GameController handles and processes requests from /games endpoints 
public class GameController extends Controller {
    public GameController(Repository repository) {
        super(repository);
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
                .check(g -> g.host.nickname.length() > 3, "Nickname should contain at least four characters")
                .get();

        Player host = this.repository.players.create(input.host.nickname);
        Game game = this.repository.games.create(host);

        ctx.json(game).status(HttpStatus.CREATED);
    }

    // Join the game with id
    public void join(Context ctx, String id) {
        Game game = this.repository.games.getById(id).get();

        game.addPlayer(new Player("ddd"));
    }
}
