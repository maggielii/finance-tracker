package persistence;

import org.json.JSONObject;
import org.json.JSONArray;
import model.Expense;
import model.Saving;
import java.io.*;
import java.util.List;

// CITATION: used this class from https://github.com/stleary/JSON-java.git
// Represents a writer that writes JSON representation of finances to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of expenses and savings to file
    public void write(List<Expense> expenses, List<Saving> savings) {
        JSONObject jsonObject = new JSONObject();
        JSONArray expensesArray = new JSONArray();
        JSONArray savingsArray = new JSONArray();

        for (Expense e : expenses) {
            expensesArray.put(e.toJson());
        }

        for (Saving s : savings) {
            savingsArray.put(s.toJson());
        }

        jsonObject.put("expenses", expensesArray);
        jsonObject.put("savings", savingsArray);

        saveToFile(jsonObject.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

