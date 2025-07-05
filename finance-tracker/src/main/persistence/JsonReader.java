package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.json.*;

// CITATION: used this class from https://github.com/stleary/JSON-java.git
// Represents a reader that reads finances from JSON data stores in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads expenses and savings from file and adds to lists;
    // throws IOException if an error occurs reading data from file
    public void read(List<Expense> expenses, List<Saving> savings) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        parseExpensesAndSavings(jsonObject, expenses, savings);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parse expense and saving data into objects
    private void parseExpensesAndSavings(JSONObject jsonObject, List<Expense> expenses, List<Saving> savings) {
        JSONArray expensesArray = jsonObject.getJSONArray("expenses");
        JSONArray savingsArray = jsonObject.getJSONArray("savings");
        
        for (Object o : expensesArray) {
            JSONObject expenseJson = (JSONObject) o;
            expenses.add(parseExpense(expenseJson));
        }

        for (Object o : savingsArray) {
            JSONObject savingJson = (JSONObject) o;
            savings.add(parseSaving(savingJson));
        }
    }

    // EFFECTS: Parses JSON into Expense object
    private Expense parseExpense(JSONObject json) {
        String categoryName = json.getString("categoryName");
        Expense expense = new Expense(categoryName);
        
        JSONArray purchases = json.getJSONArray("purchases");
        for (Object o : purchases) {
            JSONObject purchaseJson = (JSONObject) o;
            expense.addPurchase(
                    purchaseJson.getString("name"),
                    purchaseJson.getInt("cost"),
                    purchaseJson.getString("date")
            );
        }
        return expense;
    }

    // EFFECTS: Parses JSON into Saving object
    private Saving parseSaving(JSONObject json) {
        Saving saving = new Saving(json.getString("name"));
        saving.setAmountSaved(json.getInt("amountSaved"));
        return saving;
    }
}
