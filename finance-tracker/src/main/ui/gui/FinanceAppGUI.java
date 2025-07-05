package ui.gui;

import model.Expense;
import model.Saving;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents GUI for application with different sections
public class FinanceAppGUI extends JFrame {
    private static final String JSON_STORE = "./data/finances.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private List<Expense> allExpenseCategories;
    private List<Saving> allSavingsCategories;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ExpensePanel expensePanel;
    private SavingsPanel savingsPanel;
    private JPanel contentPanel;

    // EFFECTS: initializes FinanceAppGUI frame and sets up UI
    public FinanceAppGUI() {
        super("Finance Tracker");
        initializeData();
        setupUI();
        setupFrame();
    }

    // MODIFIES: this
    // EFFECTS: initializes data structures and JSON handlers
    private void initializeData() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        allExpenseCategories = new ArrayList<>();
        allSavingsCategories = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: sets up features of main frame
    private void setupFrame() {
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        addWindowListener(new WindowClose());

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up components of user interface 
    private void setupUI() {
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        contentPanel = new JPanel(cardLayout);
        
        mainPanel.add(new WelcomePanel(), "Welcome");
        mainPanel.add(setupMainAppPanel(), "MainApp");
        
        add(mainPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: sets up the main application panel
    private JPanel setupMainAppPanel() {
        JPanel mainAppPanel = new JPanel(new BorderLayout());
        mainAppPanel.add(setupMenuPanel(), BorderLayout.NORTH);
        mainAppPanel.add(setupContentPanel(), BorderLayout.CENTER);
        return mainAppPanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up menu panel with buttons
    private JPanel setupMenuPanel() {
        JPanel menuPanel = new JPanel();
        JButton expensesButton = createButton("Expenses");
        JButton savingsButton = createButton("Savings");
        JButton saveButton = createButton("Save Data");
        JButton loadButton = createButton("Load Data");
        
        menuPanel.add(expensesButton);
        menuPanel.add(savingsButton);
        menuPanel.add(saveButton);
        menuPanel.add(loadButton);
        
        setupButtonListeners(expensesButton, savingsButton, saveButton, loadButton);
        return menuPanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up the content panel for expenses and savings
    private JPanel setupContentPanel() {
        expensePanel = new ExpensePanel(allExpenseCategories);
        savingsPanel = new SavingsPanel(allSavingsCategories);
        contentPanel.add(expensePanel, "Expenses");
        contentPanel.add(savingsPanel, "Savings");
        return contentPanel;
    }

    // REQUIRES: text is not null or empty
    // EFFECTS: creates and returns button 
    private JButton createButton(String text) {
        return new JButton(text);
    }

    // REQUIRES: buttons are initialized
    // MODIFIES: this
    // EFFECTS: sets up action listeners for navigation and data 
    private void setupButtonListeners(JButton expensesButton, JButton savingsButton, 
            JButton saveButton, JButton loadButton) {
        expensesButton.addActionListener(e -> cardLayout.show(contentPanel, "Expenses"));
        savingsButton.addActionListener(e -> cardLayout.show(contentPanel, "Savings"));
        saveButton.addActionListener(e -> saveData());
        loadButton.addActionListener(e -> loadData());
    }

    // MODIFIES: this
    // EFFECTS: saves financial data to JSON file
    private void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(allExpenseCategories, allSavingsCategories);
            jsonWriter.close();
            showMessage("Data Saved", "Save Confirmation", "images/save.png");
        } catch (FileNotFoundException e) {
            showError("Error Saving", "Save Error");
        }
    }

    // MODIFIES: this
    // EFFECTS: load financial data from JSON file
    private void loadData() {
        allExpenseCategories.clear();
        allSavingsCategories.clear();
        try {
            jsonReader.read(allExpenseCategories, allSavingsCategories);
            refreshPanels();
            showMessage("Data Loaded", "Load Confirmation", "images/load.png");
        } catch (IOException e) {
            showError("Error Loading", "Load Error");
        }
    }

    // MODIFIES: this
    // EFFECTS: refreshes the category lists in expense and savings panels
    private void refreshPanels() {
        expensePanel.refreshList();
        savingsPanel.refreshList();
    }

    // REQUIRES: message, title, and iconPath are not null or empty
    // MODIFIES: this
    // EFFECTS: displays message dialog with icon
    private void showMessage(String message, String title, String iconPath) {
        ImageIcon icon = new ImageIcon(new ImageIcon(iconPath).getImage()
                .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE, icon);
    }

    // REQUIRES: message and title are not null or empty
    // MODIFIES: this
    // EFFECTS: displays error message dialog
    private void showError(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
