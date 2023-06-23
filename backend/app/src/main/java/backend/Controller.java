package backend;

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

import backend.model.Castle;
import backend.model.Warrior;
import backend.model.WsResponse;
import backend.model.game.Game;
import backend.model.game.GameInput;
import backend.model.game.GameResponse;
import backend.model.player.Player;
import backend.model.player.PlayerInput;
import backend.model.response.ResponseError;
import backend.model.unit.Unit;
import backend.model.unit.UnitInput;
import backend.model.unit.UnitResponse;
import backend.model.unit.UnitType;

// Controller handles and processes requests  
public class Controller {
    private final Repository repository;

    public Controller(Repository repository) {
        this.repository = repository;
    }

    // Get game by id
    public void getById(Context ctx, String id) {
        Optional<Game> game = this.repository.getGameById(id);

        game.map(g -> ctx.json(new GameResponse(g))).orElseThrow(() -> {
            throw new NotFoundResponse("Game not found");
        });
    }

    // Create a new game
    public void create(Context ctx) {
        GameInput input = ctx.bodyValidator(GameInput.class)
                .check(g -> g.host.nickname.length() > 3, "Nickname should contain at least four characters!")
                .get();

        Player host = new Player(input.host.nickname);
        Game game = this.repository.createGame(host);

        ctx.json(new GameResponse(game)).status(HttpStatus.CREATED);
    }

    // Join game with id
    public void join(Context ctx, String id) {
        PlayerInput input = ctx.bodyValidator(PlayerInput.class)
                .check(p -> p.nickname.length() > 3, "Nickname should contain at least four characters!")
                .get();

        Optional<Game> game = this.repository.getGameById(id);

        game.ifPresentOrElse(g -> {
            try {
                Player opponent = new Player(input.nickname);
                Castle opponentCastle = g.addOpponent(opponent);
                g.playerContexts.keySet().forEach(c -> {
                    if (c.session.isOpen())
                        c.send(new WsResponse<UnitResponse>("user_joined", new UnitResponse(opponentCastle)));
                });

                ctx.json(new GameResponse(g)).status(HttpStatus.CREATED);
            } catch (Exception e) {
                throw new BadRequestResponse("Opponent is already set!");
            }
        }, () -> {
            throw new NotFoundResponse("Game not found");
        });
    }

    // Start the game
    public void start(Context ctx, String id) {
        Optional<Game> game = this.repository.getGameById(id);

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
        Optional<Game> game = this.repository.getGameById(id);

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

    // Place a new unit on the map
    public void placeUnitOnTheMap(Context ctx, String id) {
        Optional<Game> game = this.repository.getGameById(id);

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

            Unit unit = switch (input.type) {
                case WARRIOR -> new Warrior(owner, new Pair<>(input.position.x, input.position.y), target);
                default -> null;
            };

            try {
                g.board.placeNewUnit(unit);

                g.playerContexts.keySet().forEach(c -> {
                    if (c.session.isOpen())
                        c.send(new WsResponse<UnitResponse>("unit_placed", new UnitResponse(unit)));
                });

                ctx.status(HttpStatus.CREATED);
            } catch (Exception e) {
                throw new BadRequestResponse("Cannot place new unit on the map");
            }
        }, () -> {
            throw new NotFoundResponse("Game not found");
        });
    }

    public void getUnitById(Context ctx, String id, String unitId) {
        Optional<Game> game = this.repository.getGameById(id);

        game.ifPresentOrElse(g -> {
            Optional.ofNullable(g.board.units.get(unitId)).ifPresentOrElse(
                    u -> ctx.status(200).json(new UnitResponse(u)),
                    () -> {
                        throw new NotFoundResponse("Unit not found");
                    });
        }, () -> {
            throw new NotFoundResponse("Game not found");
        });
    }

    public void boostUnitDamage(Context ctx, String id) {
        Optional<Game> game = this.repository.getGameById(id);

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
        Optional<Game> game = this.repository.getGameById(id);

        game.ifPresentOrElse(g -> {
            g.addPlayerWithContext(ctx, id);

            g.playerContexts.keySet().stream().filter(c -> c.session.isOpen()).forEach(session -> {
                session.send(new WsResponse<Object>("user_made_conn", null));
            });
        }, () -> {
            ctx.send(new ResponseError("Game not found"));
            ctx.closeSession(StatusCode.BAD_DATA, "Game not found");
        });
    }
}
