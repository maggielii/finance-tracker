import model.*;
import persistence.JsonReader;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// CITATION: used this class from https://github.com/stleary/JSON-java.git
class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read(new ArrayList<>(), new ArrayList<>());
            fail("IOException expected");
        } catch (IOException e) {
            // Pass
        }
    }

    @Test
    void testReaderEmptyFile() {
        JsonReader reader = new JsonReader("./data/testReaderEmpty.json");
        try {
            List<Expense> expenses = new ArrayList<>();
            List<Saving> savings = new ArrayList<>();
            reader.read(expenses, savings);
            assertEquals(0, expenses.size());
            assertEquals(0, savings.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralFile() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            List<Expense> expenses = new ArrayList<>();
            List<Saving> savings = new ArrayList<>();
            reader.read(expenses, savings);

            assertEquals(1, expenses.size());
            checkExpense("Food", 15, expenses.get(0));
            assertEquals(2, expenses.get(0).getPurchases().size());

            assertEquals(1, savings.size());
            checkSaving("Vacation", 200, savings.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}