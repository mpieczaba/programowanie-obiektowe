package backend.controller;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.ForbiddenResponse;

import java.util.Optional;

import org.javatuples.Pair;

import backend.model.Game;
import backend.model.Player;
import backend.model.api.GameInput;
import backend.model.api.GameResponse;
import backend.model.api.PlayerInput;
import backend.repository.Repository;

// GameController handles and processes requests from /games endpoints 
public class GameController extends Controller {
    public GameController(Repository repository) {
        super(repository);
    }

    // Get game by id
    public void getById(Context ctx, String id) {
        Optional<Game> game = this.repository.games.getById(id);

        game.map(g -> ctx.json(new GameResponse(g))).orElseThrow(() -> {
            throw new NotFoundResponse("Game not found");
        });
    }

    // Create a new game
    public void create(Context ctx) {
        GameInput input = ctx.bodyValidator(GameInput.class)
                .check(g -> g.host.nickname.length() > 3, "Nickname should contain at least four characters!")
                .get();

        Player host = this.repository.players.create(input.host.nickname);
        Game game = this.repository.games.create(host);

        ctx.json(new GameResponse(game)).status(HttpStatus.CREATED);
    }

    // Join game with id
    public void join(Context ctx, String id) {
        PlayerInput input = ctx.bodyValidator(PlayerInput.class)
                .check(p -> p.nickname.length() > 3, "Nickname should contain at least four characters!")
                .get();

        Optional<Game> game = this.repository.games.getById(id);

        game.ifPresentOrElse(g -> {
            g.addPlayer(new Player(input.nickname));

            ctx.json(new GameResponse(g)).status(HttpStatus.CREATED);
        }, () -> {
            throw new NotFoundResponse("Game not found");
        });
    }

    // Start game
    public void start(Context ctx, String id) {
        Optional<Game> game = this.repository.games.getById(id);

        game.ifPresentOrElse(g -> {
            try {
                g.start();
            } catch (Exception e) {
                throw new ForbiddenResponse("Cannot start the game");
            }
        }, () -> {
            throw new NotFoundResponse("Game not found");
        });
    }

    // Pause the game
    public void pause(Context ctx, String id) {
        Optional<Game> game = this.repository.games.getById(id);

        game.ifPresentOrElse(g -> {
            try {
                g.pause();
            } catch (Exception e) {
                throw new ForbiddenResponse("Cannot pause the game");
            }
        }, () -> {
            throw new NotFoundResponse("Game not found");
        });
    }

    public void boostUnitDamage(Context ctx, String id) {
        Optional<Game> game = this.repository.games.getById(id);

        game.ifPresentOrElse(g -> {
            try {
                g.board.getUnitByPosition(new Pair<Integer, Integer>(0, 0))
                        .ifPresentOrElse(u -> u.boostDamage(), () -> {
                            throw new NotFoundResponse("Unit not found");
                        });
            } catch (Exception e) {
                throw new NotFoundResponse("Entity not found");
            }
        }, () -> {
            throw new NotFoundResponse("Game not found");
        });
    }
}
