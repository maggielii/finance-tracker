import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: used this class from https://github.com/stleary/JSON-java.git
class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/illegal\0FileName.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            // Pass
        }
    }

    @Test
    void testWriterEmptyFile() {
        try {
            List<Expense> expenses = new ArrayList<>();
            List<Saving> savings = new ArrayList<>();
            JsonWriter writer = new JsonWriter("./data/testWriterEmpty.json");
            writer.open();
            writer.write(expenses, savings);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmpty.json");
            expenses.clear();
            savings.clear();
            reader.read(expenses, savings);
            assertEquals(0, expenses.size());
            assertEquals(0, savings.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @SuppressWarnings("methodlength") //kept length to keep as one cohesive test 
    @Test
    void testWriterGeneralFile() {
        try {
            List<Expense> expenses = new ArrayList<>();
            Expense foodExpense = new Expense("Food");
            foodExpense.addPurchase("Coffee", 5, "01/08/23");
            foodExpense.addPurchase("Sandwich", 10, "01/08/23");
            expenses.add(foodExpense);

            List<Saving> savings = new ArrayList<>();
            savings.add(new Saving("Vacation"));
            savings.get(0).addToAmount(200);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(expenses, savings);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
            List<Expense> loadedExpenses = new ArrayList<>();
            List<Saving> loadedSavings = new ArrayList<>();
            reader.read(loadedExpenses, loadedSavings);

            assertEquals(1, loadedExpenses.size());
            checkExpense("Food", 15, loadedExpenses.get(0));
            assertEquals(2, loadedExpenses.get(0).getPurchases().size());

            assertEquals(1, loadedSavings.size());
            checkSaving("Vacation", 200, loadedSavings.get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}