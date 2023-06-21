package backend.model;

public class WsResponse<T> {
    public final String event;
    public final T data;

    public WsResponse(String event, T data) {
        this.event = event;
        this.data = data;
    }
}
