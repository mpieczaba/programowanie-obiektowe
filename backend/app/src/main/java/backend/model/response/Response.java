package backend.model.response;

public abstract class Response<T> {
    public boolean success = true;

    public T data;

    public Response(T data) {
        this.data = data;
    }
}
