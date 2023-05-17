package backend.repository;

// Repository contains in-memory store
public class Repository {
    public final GameRepository games;
    public final PlayerRepository players;

    public Repository() {
        this.games = new GameRepository();
        this.players = new PlayerRepository();
    }
}
