package backend.util;

// APIResults is a class that represents serialized in the future JSON API response
public class APIResult {
    // Determines whether response is a success or not
    public boolean success = true;

    // Response data
    public final Object data;

    public APIResult(Object data) {
        this.data = data;
    }
}
