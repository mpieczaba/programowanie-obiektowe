package backend.model;

public class WsResponse<T> {
    public final String event;
    public T data;

    public WsResponse(String event) {
        this.event = event;
    }

    public WsResponse(String event, T data) {
        this.event = event;
        this.data = data;
    }
}
