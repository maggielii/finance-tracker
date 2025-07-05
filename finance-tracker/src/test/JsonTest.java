import static org.junit.jupiter.api.Assertions.*;

import model.Expense;
import model.Saving;

// CITATION: used this class from https://github.com/stleary/JSON-java.git
public class JsonTest {
    protected void checkExpense(String name, int total, Expense expense) {
        assertEquals(name, expense.getCategoryName());
        assertEquals(total, expense.getTotalSpending());
    }

    protected void checkSaving(String name, int amount, Saving saving) {
        assertEquals(name, saving.getName());
        assertEquals(amount, saving.getAmountSaved());
    }
}