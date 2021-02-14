package persistance;

import org.json.JSONObject;

// Interface Writable

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
