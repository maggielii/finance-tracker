package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a new purchase with its details
public class Purchase implements Writable {
    private double cost;
    private String name;
    private String date;

    /* 
     * EFFECTS: cost of purchase is set to cost;
     *          name of the purchase is set to name;
     *          date purchase was made is set to date (dd,mm,yy)
    */ 
    public Purchase(String name, double cost, String date) {
        this.name = name;
        this.cost = cost;
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    // CITATION: used this toJson from https://github.com/stleary/JSON-java.git
    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("cost", cost);
        json.put("date", date);
        return json;
    }
}