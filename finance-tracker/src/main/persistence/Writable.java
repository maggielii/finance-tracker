package persistence;

import org.json.JSONObject;

// CITATION: used this class from https://github.com/stleary/JSON-java.git
// define how to convert to JSON
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}