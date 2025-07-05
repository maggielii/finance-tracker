package model;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a category of expenses with name and total spending
public class Expense implements Writable {
    private List<Purchase> expenseList;
    private String categoryName;
    private double totalSpending;

    /* 
     * EFFECTS: creates a new catagory of expenses in a list; 
     *          the name of the category is set to categoryName;
     *          totalSpending is initialized to zero
     */
    public Expense(String categoryName) {
        this.categoryName = categoryName;
        totalSpending = 0;
        expenseList = new ArrayList<Purchase>();
    }

    // EFFECTS: returns the totalSpending
    public double getTotalSpending() {
        return totalSpending;
    }

    // EFFECTS: returns the categoryName
    public String getCategoryName() {
        return categoryName;
    }

    // MODIFIES: this
    // EFFECTS: adds a new purchase to expense category 
    public void addPurchase(String name, double cost, String date) {
        Purchase purchase = new Purchase(name, cost, date);
        expenseList.add(purchase);
        totalSpending += purchase.getCost();
        EventLog.getInstance().logEvent(new Event("Added purchase to expense category: " + name));
    }

    // MODIFIES: this
    // EFFECTS: removes a purchase from expense category 
    public void removePurchase(String name) {
        for (int x = 0; x < expenseList.size(); x++) {
            if (expenseList.get(x).getName().equals(name)) {
                totalSpending -= expenseList.get(x).getCost();
                expenseList.remove(x);
                x--; 
                EventLog.getInstance().logEvent(new Event("Removed purchase from expense category: " + name));
            }
        }
    }

    // EFFECTS: returns list of purchases and their costs 
    public List<String> getPurchases() {

        List<String> temp = new ArrayList<String>();

        for (Purchase p : expenseList) {
            temp.add(p.getName() + ": $" + p.getCost());
        }

        return temp;
    }

    // EFFECTS: returns list of purchase's names
    public List<String> getPurchasesNames() {

        List<String> temp = new ArrayList<String>();

        for (Purchase p : expenseList) {
            temp.add(p.getName());
        }

        return temp;
    }

    // EFFECTS: returns list of purchase's names and cost > 100
    public List<String> getPurchasesFiltered() {
        List<String> temp = new ArrayList<String>();

        for (Purchase p : expenseList) {
            if (p.getCost() > 100) {
                temp.add(p.getName() + ": $" + p.getCost());
            }
        }

        EventLog.getInstance().logEvent(new Event("Toggled filter for purchases"));
        return temp;
    }

    // CITATION: used this toJson from https://github.com/stleary/JSON-java.git
    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("categoryName", categoryName);
        json.put("totalSpending", totalSpending);

        JSONArray purchasesJson = new JSONArray();
        for (Purchase p : expenseList) {
            purchasesJson.put(p.toJson());
        }
        json.put("purchases", purchasesJson);
        return json;
    }

    // CITATION: used this addPurchasesFromJson from https://github.com/stleary/JSON-java.git
    // MODIFIES: this
    // EFFECTS: adds purchases from JSON array to expense categories
    public void addPurchasesFromJson(JSONArray purchasesJson) {
        for (Object o : purchasesJson) {
            JSONObject json = (JSONObject) o;
            addPurchase(
                    json.getString("name"),
                    json.getInt("cost"),
                    json.getString("date")
            );
        }
    }
    
}
