package W14B_G4_Assignment2;

import java.sql.ResultSet;
import java.util.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import W14B_G4_Assignment2.model.*;
import W14B_G4_Assignment2.database.*;
import java.sql.SQLException;

public class EngineTest{
    MachineEngine engine;
    User user;

    @BeforeEach
    public void engineSetup(){
        user = new Customer("first","first");
        engine = new MachineEngine(user);
        DBConnection.connect();
    }
    @Test
    public void testAddItem() throws SQLException{
        assertEquals(0,user.getCart().getSize());
        assertTrue(engine.addItem("Tonic Water", 1));
        assertEquals(1,user.getCart().getSize());
    }

    @Test
    public void testCalculateTotal() throws SQLException{
        assertEquals(0.0,engine.calculateTotalPrices());
        assertTrue(engine.addItem("Tonic Water", 1));
        assertEquals(4.5,engine.calculateTotalPrices());
        assertTrue(engine.removeItem("Tonic Water"));
        assertEquals(0.0,engine.calculateTotalPrices());
        assertFalse(engine.addItem("error water", 0));
        assertFalse(engine.addItem("Tonic Water", 1000));

    }

    @Test
    public void testUserGetAndSet(){
        assertEquals(user, engine.getUser());
        User new_user = new Customer("second","second");
        engine.setUser(new_user);
        assertEquals(new_user, engine.getUser());
    }

    @Test
    public void testCalculatePaidCashes() throws SQLException{
        assertEquals(0.0,engine.calculatePaidCashes());
        engine.addCash("$5");
        engine.addCash("50c");
        assertEquals(5.5,engine.calculatePaidCashes());
    }

    /*@Test
    public void testPaidByCard() throws SQLException{
        assertTrue(engine.payByCreditCard("Blake", "14138"));
    }*/

    @Test
   public void testEnoughMoney() throws SQLException{
       engine.addItem("Tonic Water", 1);
       engine.addCash("50c");
       assertFalse(engine.moneyEnough());
       engine.addCash("$5");
       assertTrue(engine.moneyEnough());
   }

    @Test
    public void testPayByCash() throws SQLException{
        engine.addItem("Tonic Water", 1);
        engine.addCash("$5");
        engine.addCash("50c");
        assertTrue(engine.payByCash(6,0.5));
    }


    @Test
    public void testPayByCreditCard() throws SQLException{
        engine.addItem("Tonic Water", 1);
        engine.addCash("$5");
        engine.addCash("50c");
        assertTrue(engine.payByCreditCard("Charles","40691",5.5));
    }

    @Test
    public void testGetChange() throws SQLException{
        LinkedHashMap<String, String> available = ChangesData.getChanges();
        double money = 7.0;
        LinkedHashMap<String, Integer> changes = engine.getChange(money, available);

        assertNotNull(changes);
        assertEquals(changes.size(), 8);
        assertEquals(changes.get("$20"), 0);
        assertEquals(changes.get("$10"), 0);
        assertEquals(changes.get("$5"), 1);
        assertEquals(changes.get("$2"), 1);
        assertEquals(changes.get("$1"), 0);
        assertEquals(changes.get("50c"), 0);
        assertEquals(changes.get("20c"), 0);
        assertEquals(changes.get("10c"), 0);

    }


    @Test
    public void testAddCard()throws SQLException{
        String user1 = "username";
        String card1 = "cardname";
        String card2 = "newcardname";
        engine.addCard(user1, card1);
        engine.addCard(user1, card2);
    }

    @Test
    public void testVerifyCard()throws SQLException{
        String user1 = "John";
        String card1 = "90669";
        assertTrue(engine.verifyCard(user1, card1));
        String user2 = "error";
        String card2 = "error";
        assertFalse(engine.verifyCard(user2, card2));

    }

    @Test
    public void testCancelTransaction()throws SQLException{
        engine.cancelTransaction(5.0, 1.0, "Card");
    }

}
