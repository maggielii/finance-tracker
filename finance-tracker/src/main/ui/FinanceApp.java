package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import model.EventLog;
import model.Expense;
import model.Saving;
import model.exception.LogException;
import persistence.JsonReader;
import persistence.JsonWriter;

// Finance tracker application
public class FinanceApp {
    
    private static final String JSON_STORE = "./data/finances.json";
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private List<Expense> allExpenseCategories = new ArrayList<Expense>();
    private List<Saving> allSavingsCategories = new ArrayList<Saving>();

    // EFFECTS: constructs finances and runs application
    public FinanceApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runFinance(true);
    }

    // MODIFIES: this
    // EFFECTS: runs or quits based on user input. Only display welcome if first time is true.
    private void runFinance(Boolean firstTime) {
        boolean keepGoing = true;

        while (keepGoing) {

            if (firstTime) {
                System.out.println("Welcome to your budget tracker!");
                firstTime = false;
            }

            mainMenu();
            String menuInput = input.next();

            if (menuInput.equals("quit")) {
                ConsolePrinter cp = new ConsolePrinter();
                
                try {
                    cp.printLog(EventLog.getInstance());
                } catch (LogException e) {
                    System.out.println(e.getMessage() + "System Error");
                }
                keepGoing = false;
            } else {
                processMainMenuSelection(menuInput);
            }
        }

        System.out.println("Session Ended");
    }

    // EFFECTS: display main menu to user
    private void mainMenu() {
        System.out.println("-------------------------------------------------");
        System.out.println("| Please type your selection:                   |");                  
        System.out.println("| \"v\": View expenses categories                 |");
        System.out.println("| \"c\": Create new expense category              |");
        System.out.println("| \"s\": Go to savings categories                 |");
        System.out.println("| \"save\": Save data to file                     |");
        System.out.println("| \"load\": Load data from file                   |");        
        System.out.println("| \"quit\": End current session                   |");
        System.out.println("-------------------------------------------------");
    }

    // MODIFIES: this
    // EFFECTS: processes the command of the user
    private void processMainMenuSelection(String menuInput) {
        if (menuInput.equals("v")) {
            viewExpenseCategories();
        } else if (menuInput.equals("c")) {
            createExpenseCategory();
        } else if (menuInput.equals("s")) {
            processSavingCategorySelection();
        } else if (menuInput.equals("save")) {
            saveData();
        } else if (menuInput.equals("load")) {
            loadData();
        } else {
            System.out.println("Invalid input, try again");
        }
    }

    // EFFECTS: display list of expense categories to user and make selection to
    private void viewExpenseCategories() {
        for (int x = 0; x < allExpenseCategories.size(); x++) {
            System.out.print(allExpenseCategories.get(x).getCategoryName() + " | ");
        }
        System.out.println("");
        System.out.println("Type the name of the category you want to view: ");
        String category = input.next();

        boolean exists = false; 
        for (Expense e : allExpenseCategories) {
            if (e.getCategoryName().equals(category)) {
                exists = true; 
            }
        }
        if (exists) {
            processExpenseCategorySelection(category);
        } else {
            System.out.println("This category does not exist");
            System.out.println("\"t\": try again with new name");
            System.out.println("\"b\": go back");
            String selection = input.next();

            processViewExpenseCategories(selection);
        }
    }

    // EFFECTS: processes expense category selection of user
    public void processViewExpenseCategories(String selection) {
        if (selection.equals("t")) {
            viewExpenseCategories();
        } else if (selection.equals("b")) {
            runFinance(false);
        } else {
            System.out.println("Invalid input, try again");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes expense category selection of user
    private void processExpenseCategorySelection(String category) {
        System.out.println("Do you want to view purchases or add a new purchase? ");
        System.out.println("\"v\": view purchases");
        System.out.println("\"a\": add new purchase");
        System.out.println("\"r\": remove a purchase");
        String selection = input.next();

        if (selection.equals("v")) {
            viewPurchases(category);
        } else if (selection.equals("a")) {
            addPurchase(category); 
        } else if (selection.equals("r")) {
            removePurchase(category);
        } else {
            System.out.println("Invalid input, try again");
        }
    }

    // EFFECTS: displays names of purchases in category to user
    private void viewPurchases(String category) {
        for (Expense e : allExpenseCategories) {
            if (e.getCategoryName().equals(category)) {
                for (int x = 0; x < e.getPurchases().size(); x++) {
                    System.out.print(" | " + e.getPurchases().get(x));
                }
            }
        }
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: allows user to add purchase to category
    private void addPurchase(String category) {
        System.out.print("Input the name of the purchase: ");
        String name = input.next();
        System.out.print("Input the cost of the purchase ($): ");
        int cost = input.nextInt();
        System.out.println("Input the date the purchase was made (dd/mm/yy): ");
        String date = input.next();

        for (Expense e : allExpenseCategories) {
            if (e.getCategoryName().equals(category)) {
                e.addPurchase(name, cost, date);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to remove purchase from a category
    private void removePurchase(String category) {
        System.out.print("Input the name of the purchase: ");
        String name = input.next();
        boolean exists = false; 

        for (Expense e : allExpenseCategories) {
            if (e.getCategoryName().equals(category)) {
                for (String p : e.getPurchasesNames()) {
                    if (p.equals(name)) {
                        exists = true; 
                    }
                    System.out.println(p);
                }
            }
        }

        if (exists) {
            for (Expense e : allExpenseCategories) {
                if (e.getCategoryName().equals(category)) {
                    e.removePurchase(name);
                }
            }
        } else {
            processDoesNotExist();
        }
    }

    // EFFECTS: process if selection of removePurchase does not exist 
    public void processDoesNotExist() {
        System.out.println("This purchase does not exist");
        System.out.println("\"t\": try again with new name");
        System.out.println("\"b\": go back");
        String selection = input.next();
        processRemovePurchase(selection);
    }

    // EFFECTS: process remove purchase selction of user
    public void processRemovePurchase(String selection) {
        if (selection.equals("t")) {
            removePurchase(selection);
        } else if (selection.equals("b")) {
            runFinance(false);
        } else {
            System.out.println("Invalid input, try again");
        }
    }
    
    // MODIFIES: this
    // EFFECTS: creates a new category of expenses
    private void createExpenseCategory() {
        System.out.print("What do you want to name this category? ");
        String name = input.next();
        Expense newCategory = new Expense(name);
        allExpenseCategories.add(newCategory);
        System.out.println("New category called " + name + " has been created");
    }

    // MODIFIES: this
    // EFFECTS: processes saving category selection of user
    private void processSavingCategorySelection() {
        System.out.println("");
        System.out.println("Do you want to view all savings categories, add a new one, or contribute to a category? ");
        System.out.println("\"v\": view all categories");
        System.out.println("\"a\": add new one");
        System.out.println("\"c\": contibute to a category");
        String selection = input.next();

        if (selection.equals("v")) {
            viewSaving();
        } else if (selection.equals("c")) {
            System.out.print("Input the category to contribute to: ");
            String name = input.next();
            addSavingToCategory(name);
        } else if (selection.equals("a")) {
            System.out.print("Input the new category name: ");
            String name = input.next();
            Saving newSaving = new Saving(name);
            allSavingsCategories.add(newSaving);
        } else {
            System.out.println("Invalid input, try again");
        }
    }

     // EFFECTS: displays names of savings categories to user
    private void viewSaving() {
        for (Saving s : allSavingsCategories) {
            System.out.print(" | " + s.getName() + ": $" + s.getAmountSaved());
        }
        System.out.println("");
    }

    // MODIFIES: this
    // EFFECTS: allows user to add amount to category
    private void addSavingToCategory(String category) {
        System.out.print("Input the amount to contribute to the category ($): ");
        int amount = input.nextInt();

        for (Saving s : allSavingsCategories) {
            if (s.getName().equals(category)) {
                s.addToAmount(amount);
            }
        }
    }

    // CITATION: used this save from https://github.com/stleary/JSON-java.git
    // EFFECTS: saves the finances to file
    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(allExpenseCategories, allSavingsCategories);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // CITATION: used this load from https://github.com/stleary/JSON-java.git
    // MODIFIES: this
    // EFFECTS: loads finances from file
    private void loadData() {
        try {
            File file = new File(JSON_STORE);;
            if (file.length() == 0) {
                System.out.println("Nothing to load");
                return;
            }

            allExpenseCategories.clear();
            allSavingsCategories.clear();
            jsonReader.read(allExpenseCategories, allSavingsCategories);
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}