package backend;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.path;

import backend.controller.GameController;

// Router handles routing and setting new endpoints
public class Router {
    private static final void gameRoutes() {
        final GameController gameController = new GameController();

        // GET /games/:id
        get("{id}", ctx -> gameController.getById(ctx, ctx.pathParam("id")));

        // POST /games
        post(ctx -> gameController.create(ctx));

        // TODO: POST /games/:id/join etc.
    }

    public static final void getRoutes() {
        // Set game endpoints
        path("games", Router::gameRoutes);
    }
}
