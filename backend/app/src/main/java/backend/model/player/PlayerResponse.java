package backend.model.player;

public class PlayerResponse {
    public final String id;

    public final String nickname;

    public PlayerResponse(Player player) {
        this.id = player.id;
        this.nickname = player.nickname;
    }
}
