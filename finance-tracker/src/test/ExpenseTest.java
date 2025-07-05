import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Expense;

public class ExpenseTest {
    private Expense testExpense;
    private List<String> testList = new ArrayList<String>();

    @BeforeEach
    void runBefore() {
        testExpense = new Expense("Test Category");
    }

    @Test
    void testConstructor() {
        assertEquals("Test Category", testExpense.getCategoryName());
        assertEquals(0, testExpense.getTotalSpending());
    }

    @Test
    void testAddPurchase() {
        assertEquals(new ArrayList<String>(), testExpense.getPurchases());
        testExpense.addPurchase("Test Name", 10, "01,01,25");
        testList.add("Test Name: $10.0");
        assertEquals(testList, testExpense.getPurchases());
        testExpense.addPurchase("Test Name2", 20, "02,02,25");
        testList.add("Test Name2: $20.0");
        assertEquals(testList, testExpense.getPurchases());
    }

    @Test
    void testRemovePurchase() {
        testExpense.addPurchase("Test Name", 10, "01,01,25");
        testExpense.addPurchase("Test Name2", 20, "02,02,25");
        testList.add("Test Name: $10.0");
        testExpense.removePurchase("Test Name2"); 
        assertEquals(testList, testExpense.getPurchases());
        testExpense.removePurchase("Test Name");
        assertEquals(new ArrayList<String>(), testExpense.getPurchases());
    }

    @Test
    void testGetPurchases() {
        testExpense.addPurchase("Test Name", 10, "01,01,25");
        testExpense.addPurchase("Test Name2", 20, "02,02,25");
        testList.add("Test Name: $10.0");
        testList.add("Test Name2: $20.0");
        assertEquals(testList, testExpense.getPurchases());
    }

    @Test
    void testGetPurchasesFiltered() {
        testExpense.addPurchase("Test Name", 99, "01,01,25");
        testExpense.addPurchase("Test Name2", 100, "02,02,25");
        testExpense.addPurchase("Test Name3", 101, "03,03,25");
        testList.add("Test Name3: $101.0");
        assertEquals(testList, testExpense.getPurchasesFiltered());
    }

    @Test
    void testGetPurchasesNames() {
        testExpense.addPurchase("Test Name", 10, "01,01,25");
        testExpense.addPurchase("Test Name2", 20, "02,02,25");
        testList.add("Test Name");
        testList.add("Test Name2");
        assertEquals(testList, testExpense.getPurchasesNames());
    }

    @Test
    void testAddPurchasesFromJson() {
        Expense expense = new Expense("House");
        JSONArray purchases = new JSONArray();

        purchases.put(new JSONObject().put("name", "Chair").put("cost", 100). put("date", "01/01/25"));

        expense.addPurchasesFromJson(purchases);
        assertEquals(1, expense.getPurchases().size());
    }
}
