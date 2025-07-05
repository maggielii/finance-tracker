package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents a category of savings with name and total saved in that category
public class Saving implements Writable {
    private String name;
    private double amountSaved;

    /* 
     * EFFECTS: creates a new category of savings; 
     *          the name of the category is set to name;
     *          amountSaved is initialized to zero
     */
    public Saving(String name) {
        this.name = name;
        amountSaved = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAmountSaved(double amountSaved) {
        this.amountSaved = amountSaved;
    }

    public double getAmountSaved() {
        return amountSaved;
    }

    // MODIFIES: this
    // EFFECTS: adds more to amountSaved
    public void addToAmount(double amount) {
        amountSaved += amount;
        EventLog.getInstance().logEvent(new Event("Added $" + amount + " to total savings in category"));
    }

    // CITATION: used this toJson from https://github.com/stleary/JSON-java.git
    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("amountSaved", amountSaved);
        return json;
    }
}
