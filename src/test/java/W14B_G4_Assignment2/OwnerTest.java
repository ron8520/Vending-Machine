package W14B_G4_Assignment2;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import W14B_G4_Assignment2.model.*;
import java.util.*;

public class OwnerTest{
    Owner owner;

    @BeforeEach
    public void ownerSetUp(){
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(new Item(1, "mineral water", "drinks", 2, 2, 0));
        this.owner = new Owner("name","password", itemList);
    }

    @Test
    public void TestCustomerConstructor(){
        assertNotNull(this.owner);
    }

    @Test
    public void TestOwnerModifyItemDetails(){
        assertTrue(this.owner.modifyItemDetails("mineral water", "code", 2));
        assertTrue(this.owner.modifyItemDetails("mineral water", "name", "water"));
        assertTrue(this.owner.modifyItemDetails("water", "category", "Drinks"));
        assertTrue(this.owner.modifyItemDetails("water", "quantity", 3));
        assertTrue(this.owner.modifyItemDetails("water", "price", 2.5));
    }

    @Test
    public void TestOwnerGenerateReport(){
        assertTrue(this.owner.generateSellerReport());
    }

    @Test
    public void TestOwnerGenerateSummary(){
        assertTrue(this.owner.generateSellerSummary());
    }

    @Test
    public void TestOwnerModifyChange(){
        assertTrue(this.owner.modifyChange("addChange", "5 dollar"));
        assertTrue(this.owner.modifyChange("removeChange", "5 dollar"));
        assertFalse(this.owner.modifyChange("unknownChange", "5 dollar"));
    }
}
