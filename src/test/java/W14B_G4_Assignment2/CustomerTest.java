package W14B_G4_Assignment2;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import W14B_G4_Assignment2.model.Customer;
import java.util.HashMap;

public class CustomerTest{
    Customer customer;

    @BeforeEach
    public void customerSetUp(){
        this.customer = new Customer("name","password");
    }

    @Test
    public void TestCustomerConstructor(){
        assertNotNull(this.customer);
    }

    @Test
    public void TestCustomerGetMethod(){
        String name = this.customer.getName();
        String password = this.customer.getPassword();
        assertEquals(name, "name");
        assertEquals(password, "password");
    }

    @Test
    public void TestCustomerSelectItems(){
        this.customer.getCart().addItem("Mars",3);
        this.customer.getCart().addItem("Mineral Water",2);
        this.customer.getCart().addItem("Mars",2);
        this.customer.getCart().addItem("Juice",1);
        this.customer.getCart().addItem("M&M", 0);
        this.customer.getCart().removeItem("Juice");
        this.customer.getCart().removeItem("Kettle");

        HashMap<String, Integer> items = this.customer.getCart().getItems();
        assertNotNull(items);
        assertEquals(items.size(), 2);
        assertEquals(items.get("Mars"), 5);
        assertEquals(items.get("Mineral Water"), 2);
        assertEquals(items.get("Juice"), null);
        assertEquals(items.get("M&M"), null);

        this.customer.getCart().cancel();
        items = this.customer.getCart().getItems();
        assertEquals(items.size(), 0);

    }
}
