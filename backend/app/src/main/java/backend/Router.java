package backend;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.path;

import backend.controller.GameController;
import backend.controller.PlayerController;
import backend.repository.Repository;

// Router handles routing and setting new endpoints
public class Router {
    private final static Repository repository = new Repository();

    private static final void playerRoutes() {
        final PlayerController playerController = new PlayerController(repository);

        // Get /players/:id
        get("{id}", ctx -> playerController.getById(ctx, ctx.pathParam("id")));
    }

    private static final void gameRoutes() {
        final GameController gameController = new GameController(repository);

        path("{id}", () -> {
            // GET /games/:id
            get(ctx -> gameController.getById(ctx, ctx.pathParam("id")));

            // POST /games/:id/join
            post("join", ctx -> gameController.join(ctx, ctx.pathParam("id")));
        });

        // POST /games
        post(ctx -> gameController.create(ctx));
    }

    public static final void getRoutes() {
        // Set player endpoints
        path("players", Router::playerRoutes);

        // Set game endpoints
        path("games", Router::gameRoutes);
    }
}
