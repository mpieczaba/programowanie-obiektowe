package backend.util;

// APIError is a wrapper around APIResult that provides simple error objects
public class APIError extends APIResult {
    // Error message
    public final String message;

    public APIError(String message) {
        super(null);

        this.success = false;
        this.message = message;
    }
}
