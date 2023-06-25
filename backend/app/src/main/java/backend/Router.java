package backend;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.ws;

// Router handles routing and setting new endpoints
public class Router {
    private final static Repository repository = new Repository();

    private static final void gameRoutes() {
        final Controller controller = new Controller(repository);

        path("{game_id}", () -> {
            // GET /games/:id
            get(ctx -> controller.getById(ctx, ctx.pathParam("game_id")));

            // POST /games/:id/join
            post("join", ctx -> controller.join(ctx, ctx.pathParam("game_id")));

            // POST /games/:id/start
            post("start", ctx -> controller.start(ctx, ctx.pathParam("game_id")));

            // POST /games/:id/pause
            post("pause", ctx -> controller.pause(ctx, ctx.pathParam("game_id")));

            path("units", () -> {
                path("{unit_id}", () -> {
                    // GET /games:id/units/:id
                    get(ctx -> controller.getUnitById(ctx, ctx.pathParam("game_id"), ctx.pathParam("unit_id")));

                    path("boost", () -> {
                        // POST /games:id/units/:id/boost/damage
                        post("damage", ctx -> controller.boostUnitDamage(ctx, ctx.pathParam("game_id"),
                                ctx.pathParam("unit_id")));

                        // POST /games:id/units/:id/boost/damage
                        post("attack_speed", ctx -> controller.boostUnitAttackSpeed(ctx, ctx.pathParam("game_id"),
                                ctx.pathParam("unit_id")));

                        // POST /games:id/units/:id/boost/damage
                        post("movement_speed", ctx -> controller.boostUnitMovementSpeed(ctx, ctx.pathParam("game_id"),
                                ctx.pathParam("unit_id")));
                    });
                });

                // POST /games/:id/units/place
                post("place", ctx -> controller.placeUnitOnTheMap(ctx, ctx.pathParam("game_id")));
            });

            ws(ws -> {
                ws.onConnect(ctx -> controller.wsConnect(ctx, ctx.pathParam("game_id")));
            });
        });

        // POST /games
        post(ctx -> controller.create(ctx));
    }

    public static final void getRoutes() {
        // Set game endpoints
        path("games", Router::gameRoutes);
    }
}
