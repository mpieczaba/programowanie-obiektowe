package backend.model.response;

public class ResponseError extends Response<Object> {
    public final String message;

    public ResponseError(String message) {
        super(null);

        this.success = false;
        this.message = message;
    }
}
