package ui.gui;

import model.Expense;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Represents the Panel with Expense Functions
class ExpensePanel extends JPanel {
    private List<Expense> expenses;
    private DefaultListModel<String> categoryListModel;
    private JList<String> categoryList;
    private DefaultListModel<String> purchaseListModel;
    private JList<String> purchaseList;
    private JTextField newCategoryField;
    private JTextField purchaseNameField;
    private JTextField costField;
    private JTextField dateField;
    private boolean filterEnabled = false;

    // EFFECTS: initializes an expense panel with split pane
    public ExpensePanel(List<Expense> expenses) {
        this.expenses = expenses;
        setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, setupLeftPanel(), setupRightPanel());
        add(splitPane);

        setupEventListeners();
        refreshList();
    }

    // MODIFIES: this
    // EFFECTS: sets up  left panel with category list and controls
    private JPanel setupLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(118, 175, 122));
        categoryListModel = new DefaultListModel<>();
        categoryList = new JList<>(categoryListModel);
        categoryList.setBackground(new Color(196, 224, 198));
        leftPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);

        JPanel categoryControlPanel = new JPanel(new GridLayout(2, 1));
        categoryControlPanel.setBackground(new Color(118, 175, 122));
        categoryControlPanel.add(setupCreatePanel());
        categoryControlPanel.add(setupDeletePanel());
        leftPanel.add(categoryControlPanel, BorderLayout.SOUTH);
        
        return leftPanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up create panel for category 
    private JPanel setupCreatePanel() {
        JPanel createPanel = new JPanel();
        createPanel.setBackground(new Color(118, 175, 122));
        newCategoryField = new JTextField(15);
        JButton createButton = new JButton("Create Category");
        createPanel.add(new JLabel("New Category:"));
        createPanel.add(newCategoryField);
        createPanel.add(createButton);
        createButton.addActionListener(e -> createCategory());
        return createPanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up delete panel for category 
    private JPanel setupDeletePanel() {
        JPanel deletePanel = new JPanel();
        deletePanel.setBackground(new Color(118, 175, 122)); 
        JButton deleteButton = new JButton("Delete Category");
        deleteButton.addActionListener(e -> deleteCategory());
        deletePanel.add(deleteButton);
        return deletePanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up right panel with purchase list and controls
    private JPanel setupRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(118, 175, 122));
        purchaseListModel = new DefaultListModel<>();
        purchaseList = new JList<>(purchaseListModel);
        purchaseList.setBackground(new Color(196, 224, 198));
        rightPanel.add(new JScrollPane(purchaseList), BorderLayout.CENTER);
        
        rightPanel.add(setupPurchaseControlPanel(), BorderLayout.SOUTH);
        return rightPanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up purchase control panel
    private JPanel setupPurchaseControlPanel() {
        JPanel purchaseControlPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        purchaseControlPanel.setBackground(new Color(118, 175, 122));
        purchaseNameField = new JTextField();
        costField = new JTextField();
        dateField = new JTextField();
        JButton addButton = new JButton("Add Purchase");
        JButton deleteButton = new JButton("Delete Purchase");
        JButton totalButton = new JButton("Show Total");
        
        purchaseControlPanel.add(new JLabel("Name:"));
        purchaseControlPanel.add(new JLabel("Cost ($):"));
        purchaseControlPanel.add(new JLabel("Date (dd/mm/yy):"));
        purchaseControlPanel.add(purchaseNameField);
        purchaseControlPanel.add(costField);
        purchaseControlPanel.add(dateField);
        purchaseControlPanel.add(setupFilterButton());
        purchaseControlPanel.add(totalButton);
        purchaseControlPanel.add(addButton);
        purchaseControlPanel.add(deleteButton);
        
        addButton.addActionListener(e -> addPurchase());
        deleteButton.addActionListener(e -> deletePurchase());
        totalButton.addActionListener(e -> showTotal());
        
        return purchaseControlPanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up event listeners for category selection
    private void setupEventListeners() {
        categoryList.addListSelectionListener(e -> updatePurchases());
    }

    // REQUIRES: category name is not empty
    // MODIFIES: this
    // EFFECTS: creates new category and updates list
    private void createCategory() {
        String name = newCategoryField.getText().trim();
        if (!name.isEmpty()) {
            expenses.add(new Expense(name));
            refreshList();
            newCategoryField.setText("");
        }
    }

    // REQUIRES: selected category exists
    // MODIFIES: this
    // EFFECTS: deletes selected category
    private void deleteCategory() {
        int index = categoryList.getSelectedIndex();
        if (index != -1) {
            expenses.remove(index);
            refreshList();
            purchaseListModel.clear();
        }
    }

    // REQUIRES: selected category exists
    // MODIFIES: this
    // EFFECTS: add new purchase to selected category
    private void addPurchase() {
        int index = categoryList.getSelectedIndex();
        if (index != -1) {
            Expense expense = expenses.get(index);
            try {
                String name = purchaseNameField.getText().trim();
                double cost = Double.parseDouble(costField.getText().trim());
                String date = dateField.getText().trim();
                
                if (name.isEmpty() || date.isEmpty()) {
                    throw new IllegalArgumentException();
                }
                
                expense.addPurchase(name, cost, date);
                updatePurchases();
                purchaseNameField.setText("");
                costField.setText("");
                dateField.setText("");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid cost format");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Name and date cannot be empty");
            }
        }
    }

    // REQUIRES: selected purchase exists
    // MODIFIES: this
    // EFFECTS: deletes selected purchase
    private void deletePurchase() {
        int categoryIndex = categoryList.getSelectedIndex();
        String selectedPurchase = purchaseList.getSelectedValue();
    
        if (categoryIndex != -1) {
            Expense expense = expenses.get(categoryIndex);

            String purchaseName = selectedPurchase.split(": \\$")[0]; 
    
            expense.removePurchase(purchaseName);
            updatePurchases();
        }
    }

    // REQUIRES: selected category exists
    // MODIFIES: this
    // EFFECTS: updates the purchase list for the selected category, applying the filter if enabled
    private void updatePurchases() {
        purchaseListModel.clear();
        int index = categoryList.getSelectedIndex();
        if (index != -1) {
            Expense expense = expenses.get(index);
            List<String> purchases;
            
            if (filterEnabled) {
                purchases = expense.getPurchasesFiltered();
            } else {
                purchases = expense.getPurchases();
            }
            
            for (String purchase : purchases) {
                purchaseListModel.addElement(purchase);
            }
        }
    }


    // REQUIRES: selected category exists
    // MODIFIES: this
    // EFFECTS: displays total spending for selected category
    private void showTotal() {
        int index = categoryList.getSelectedIndex();
        if (index != -1) {
            Expense expense = expenses.get(index);

            ImageIcon icon = new ImageIcon(new ImageIcon("images/total.png").getImage()
                    .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            JOptionPane.showMessageDialog(this, 
                    "Total spent for " + expense.getCategoryName() + ": $" + expense.getTotalSpending(),
                    "Total Spent", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up filter button
    private JButton setupFilterButton() {
        JButton filterButton = new JButton("Filter: > $100");
        filterButton.addActionListener(e -> toggleFilter());
        return filterButton;
    }

    // MODIFIES: this
    // EFFECTS: toggles filtering based on total purchase spending > $100
    private void toggleFilter() {
        filterEnabled = !filterEnabled;
        updatePurchases();
    }

    // MODIFIES: this
    // EFFECTS: refreshes category list displayed in UI
    public void refreshList() {
        categoryListModel.clear();
        for (Expense e : expenses) {
            categoryListModel.addElement(e.getCategoryName());
        }
    }
}
