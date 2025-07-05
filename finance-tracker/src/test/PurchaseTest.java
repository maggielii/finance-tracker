import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Purchase;

public class PurchaseTest {
    Purchase testPurchase;

    @BeforeEach
    void runBefore() {
        testPurchase = new Purchase("Test Purchase", 5, "03/03/25");
    }

    @Test
    void testConstructor() {
        assertEquals("Test Purchase", testPurchase.getName());
        assertEquals(5, testPurchase.getCost());
        assertEquals("03/03/25", testPurchase.getDate());
    }

    @Test
    void testSetName() {
        testPurchase.setName("New Name");
        assertEquals("New Name", testPurchase.getName());
    }

    @Test
    void testSetCost() {
        testPurchase.setCost(50);
        assertEquals(50, testPurchase.getCost());
    }

    @Test
    void testSetDate() {
        testPurchase.setDate("06/06/25");
        assertEquals("06/06/25", testPurchase.getDate());
    }
}
