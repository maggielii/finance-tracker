package ui.gui;

import model.Saving;
import javax.swing.*;
import java.awt.*;
import java.util.List;

// Represents the Panel with Savings Functions
class SavingsPanel extends JPanel {
    private List<Saving> savings;
    private DefaultListModel<String> categoryListModel;
    private JList<String> categoryList;
    private DefaultListModel<String> contributionListModel;
    private JList<String> contributionList;
    private JTextField newCategoryField;
    private JTextField amountField;
    private boolean filterEnabled = false;

    // REQUIRES: savings list is initialized
    // EFFECTS: constructs SavingsPanel and initializes UI components
    public SavingsPanel(List<Saving> savings) {
        
        this.savings = savings;
        setLayout(new BorderLayout());
        setBackground(new Color(118, 175, 122));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, setupLeftPanel(), setupRightPanel());
        add(splitPane);

        refreshList();
    }

    // MODIFIES: this
    // EFFECTS: sets up left panel containing category list and controls
    private JPanel setupLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        categoryListModel = new DefaultListModel<>();
        categoryList = new JList<>(categoryListModel);
        leftPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);

        JPanel categoryControlPanel = new JPanel(new GridLayout(2, 1));
        categoryControlPanel.add(setupCreatePanel());
        categoryControlPanel.add(setupDeletePanel());
        leftPanel.add(categoryControlPanel, BorderLayout.SOUTH);

        leftPanel.setBackground(new Color(118, 175, 122));
        categoryControlPanel.setBackground(new Color(118, 175, 122));
        categoryList.setBackground(new Color(196, 224, 198));
        newCategoryField.setBackground(Color.WHITE);

        return leftPanel;
    }
    
    // MODIFIES: this
    // EFFECTS: sets up create category panel
    private JPanel setupCreatePanel() {
        JPanel createPanel = new JPanel();
        newCategoryField = new JTextField(15);
        JButton createButton = new JButton("Create Category");
        createPanel.add(new JLabel("New Category:"));
        createPanel.add(newCategoryField);
        createPanel.add(createButton);

        createButton.addActionListener(e -> createCategory());

        createPanel.setBackground(new Color(118, 175, 122));

        return createPanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up delete category panel
    private JPanel setupDeletePanel() {
        JPanel deletePanel = new JPanel();
        JButton deleteButton = new JButton("Delete Category");
        deleteButton.addActionListener(e -> deleteCategory());
        deletePanel.add(deleteButton);

        deletePanel.setBackground(new Color(118, 175, 122));

        return deletePanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up right panel containing contribution list and controls
    private JPanel setupRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(118, 175, 122));
        contributionListModel = new DefaultListModel<>();
        contributionList = new JList<>(contributionListModel);
        contributionList.setBackground(new Color(196, 224, 198));
        rightPanel.add(new JScrollPane(contributionList), BorderLayout.CENTER);

        amountField = new JTextField();
        amountField.setBackground(Color.WHITE);
        JButton addButton = new JButton("Add Contribution");
        JButton totalButton = new JButton("Show Total");

        JPanel contributionControlPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        contributionControlPanel.setBackground(new Color(118, 175, 122));
        contributionControlPanel.add(new JLabel("Amount ($):"));
        contributionControlPanel.add(amountField);
        contributionControlPanel.add(addButton);
        contributionControlPanel.add(totalButton);
        contributionControlPanel.add(setupFilterButton());
        rightPanel.add(contributionControlPanel, BorderLayout.SOUTH);  

        addButton.addActionListener(e -> addContribution());
        totalButton.addActionListener(e -> showTotal());
        categoryList.addListSelectionListener(e -> updateContributions());

        return rightPanel;
    }  

    // REQUIRES: category name is not empty
    // MODIFIES: this
    // EFFECTS: creates a new savings category and updates list
    private void createCategory() {
        String name = newCategoryField.getText().trim();
        if (!name.isEmpty()) {
            savings.add(new Saving(name));
            refreshList();
            newCategoryField.setText("");
        }
    }

    // REQUIRES: selected category exists
    // MODIFIES: this
    // EFFECTS: deletes selected savings category
    private void deleteCategory() {
        int index = categoryList.getSelectedIndex();
        if (index != -1) {
            savings.remove(index);
            refreshList();
            contributionListModel.clear();
        }
    }

    // REQUIRES: selected category exists, amount must be a number
    // MODIFIES: this
    // EFFECTS: adds a contribution to selected savings category
    private void addContribution() {
        int index = categoryList.getSelectedIndex();
        if (index != -1) {
            try {
                double amount = Double.parseDouble(amountField.getText().trim());
                savings.get(index).addToAmount(amount);
                updateContributions();
                amountField.setText("");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount");
            }
        }
    }

    // REQUIRES: selected category exists
    // MODIFIES: this
    // EFFECTS: updates contribution list for selected category
    private void updateContributions() {
        contributionListModel.clear();
        int index = categoryList.getSelectedIndex();
        if (index != -1) {
            contributionListModel.addElement("Total: $" + savings.get(index).getAmountSaved());
        }
    }

    // REQUIRES: selected category exists
    // MODIFIES: this
    // EFFECTS: displays the total saved for selected category
    private void showTotal() {
        int index = categoryList.getSelectedIndex();
        if (index != -1) {
            ImageIcon icon = new ImageIcon(new ImageIcon("images/total.png").getImage()
                    .getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            JOptionPane.showMessageDialog(this, "Total: $" + savings.get(index).getAmountSaved(), 
                    "Total Amount Saved", JOptionPane.INFORMATION_MESSAGE, icon);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up filter button
    private JPanel setupFilterButton() {
        JPanel filterPanel = new JPanel();
        JButton filterButton = new JButton("Filter: > $100");
        filterButton.addActionListener(e -> toggleFilter());
        filterPanel.add(filterButton);
        filterPanel.setBackground(new Color(118, 175, 122));
        return filterPanel;
    }

    // MODIFIES: this
    // EFFECTS: toggles filtering bsed on savings > $100
    private void toggleFilter() {
        filterEnabled = !filterEnabled;
        refreshList();
    }

    // MODIFIES: this
    // EFFECTS: refreshes category list displayed in UI, filtering based on conditions 
    public void refreshList() {
        categoryListModel.clear();
        for (Saving s : savings) {
            if (!filterEnabled || s.getAmountSaved() > 100) {
                categoryListModel.addElement(s.getName());
            }
        }
    }
}