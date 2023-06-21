package backend;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.ws;

import backend.controller.GameController;
import backend.controller.PlayerController;
import backend.repository.Repository;

// Router handles routing and setting new endpoints
public class Router {
    private final static Repository repository = new Repository();

    private static final void playerRoutes() {
        final PlayerController playerController = new PlayerController(repository);

        // Get /players/:id
        get("{player_id}", ctx -> playerController.getById(ctx, ctx.pathParam("player_id")));
    }

    private static final void gameRoutes() {
        final GameController gameController = new GameController(repository);

        path("{game_id}", () -> {
            // GET /games/:id
            get(ctx -> gameController.getById(ctx, ctx.pathParam("game_id")));

            // POST /games/:id/join
            post("join", ctx -> gameController.join(ctx, ctx.pathParam("game_id")));

            // POST /games/:id/start
            post("start", ctx -> gameController.start(ctx, ctx.pathParam("game_id")));

            // POST /games/:id/pause
            post("pause", ctx -> gameController.pause(ctx, ctx.pathParam("game_id")));

            path("units", () -> {
                // POST /games/:id/units/place
                post("place", ctx -> gameController.placeUnitOnTheMap(ctx, ctx.pathParam("game_id")));

                path("boost", () -> {
                    // POST /games/:id/units/boost/damage
                    post("damage", ctx -> gameController.boostUnitDamage(ctx, ctx.pathParam("game_id")));
                });
            });

            ws(ws -> {
                ws.onConnect(ctx -> gameController.wsConnect(ctx, ctx.pathParam("game_id")));
            });
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
