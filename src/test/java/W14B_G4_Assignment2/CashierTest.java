package W14B_G4_Assignment2;
import java.util.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import W14B_G4_Assignment2.model.Cashier;

public class CashierTest{
    Cashier cashier;
    List<String> items;
    String item;
    Date date;

    @BeforeEach
    public void CashierSetUp(){
        cashier = new Cashier("name", "password");
    }

    @Test
    public void TestCashierConstructor(){
        assertNotNull(cashier);
    }

    @Test
    public void TestCashierGetMethod(){
        String name = this.cashier.getName();
        String password = this.cashier.getPassword();
    }

    @Test
    public void TestCashierHandleChanges(){
        String change = "5 dollar";
        cashier.addChange(change);
        assertEquals(cashier.getChanges().size(), 1);
        assertEquals(cashier.getChanges().get(0), change);
        cashier.removeChange(change);
        assertEquals(cashier.getChanges().size(), 0);

    }
}
