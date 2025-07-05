import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Saving;

public class SavingTest {
    Saving testSaving;

    @BeforeEach
    void runBefore() {
        testSaving = new Saving("Test Saving");
    }

    @Test
    void testConstructor() {
        assertEquals("Test Saving", testSaving.getName());
        assertEquals(0, testSaving.getAmountSaved());
    }

    @Test
    void testSetName() {
        testSaving.setName("New Name");
        assertEquals("New Name", testSaving.getName());
    }

    @Test
    void testSetAmountSaved() {
        testSaving.setAmountSaved(50);
        assertEquals(50, testSaving.getAmountSaved());
    }

    @Test
    void testAddToAmount() {
        testSaving.setAmountSaved(10);
        testSaving.addToAmount(100);
        assertEquals(110, testSaving.getAmountSaved());
    }  
}




