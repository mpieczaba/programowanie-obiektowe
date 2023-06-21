package backend.controller;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;
import io.javalin.websocket.WsConnectContext;
import io.javalin.http.ForbiddenResponse;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.jetty.websocket.api.StatusCode;
import org.javatuples.Pair;

import backend.model.Warrior;
import backend.model.board.BoardResponse;
import backend.model.game.Game;
import backend.model.game.GameInput;
import backend.model.game.GameResponse;
import backend.model.player.Player;
import backend.model.player.PlayerInput;
import backend.model.response.ResponseError;
import backend.model.unit.Unit;
import backend.model.unit.UnitInput;
import backend.model.unit.UnitType;
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
            try {
                g.addOpponent(new Player(input.nickname));

                ctx.json(new GameResponse(g)).status(HttpStatus.CREATED);
            } catch (Exception e) {
                throw new BadRequestResponse("Opponent is already set!");
            }
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

    public void placeUnitOnTheMap(Context ctx, String id) {
        Optional<Game> game = this.repository.games.getById(id);

        game.ifPresentOrElse(g -> {
            if (g.opponent == null)
                throw new BadRequestResponse("Wait for the opponent to join the game!");

            UnitInput input = ctx.bodyValidator(UnitInput.class)
                    .check(u -> (u.ownerId.equals(g.host.id) || (u.ownerId.equals(g.opponent.id))),
                            "Invalid owner id!")
                    .get();

            Player owner = g.host.id.equals(input.ownerId) ? g.host
                    : g.opponent.id.equals(input.ownerId) ? g.opponent : null;
            if (owner == null)
                throw new BadRequestResponse("Player not found!");

            Player opponent = g.host.id.equals(input.ownerId) ? g.opponent : g.host;

            Unit target = g.board.units.values().stream()
                    .filter(u -> (u.owner.id.equals(opponent.id)) && (u.type.equals(UnitType.CASTLE)))
                    .collect(Collectors.toCollection(ArrayList::new)).get(0);

            Unit unit = null;

            switch (input.type) {
                case WARRIOR:
                    unit = new Warrior(owner, new Pair<>(input.position.x, input.position.y),
                            target);
                    break;

                default:
                    break;
            }

            try {
                g.board.placeNewUnit(new Pair<>(input.position.x, input.position.y), unit);

                ctx.json(new BoardResponse(g.board)).status(HttpStatus.CREATED);
            } catch (Exception e) {
                throw new BadRequestResponse("Cannot place new unit on the map");
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
                throw new NotFoundResponse("Unit not found");
            }
        }, () -> {
            throw new NotFoundResponse("Game not found");
        });
    }

    public void wsConnect(WsConnectContext ctx, String id) throws Exception {
        Optional<Game> game = this.repository.games.getById(id);

        game.ifPresentOrElse(g -> {
            g.addPlayerWithContext(ctx, id);

            g.playerContexts.keySet().stream().filter(c -> c.session.isOpen()).forEach(session -> {
                session.send("{\"message\": \"user joined the game\"}");
            });
        }, () -> {
            ctx.send(new ResponseError("Game not found"));
            ctx.closeSession(StatusCode.BAD_DATA, "Game not found");
        });
    }
}
