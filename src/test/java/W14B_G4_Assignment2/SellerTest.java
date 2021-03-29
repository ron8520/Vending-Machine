package W14B_G4_Assignment2;

import java.util.*;

import W14B_G4_Assignment2.model.Item;

import W14B_G4_Assignment2.model.Seller;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;


public class SellerTest {

    private Item item;
    private Seller seller;
    private ArrayList<Item> itemList = new ArrayList<Item>();

    @BeforeEach
    public void ItemSetUp(){
        item = new Item(1001,"Mineral Water", "Drinks", 7, 2.5, 0);
        itemList.add(item);
        seller = new Seller("Sylvia", "12345", itemList);
    }

    @Test
    public void TestConstuctor(){
        assertNotNull(item);
        assertNotNull(seller);
    }

    @Test
    public void TestSelectItem(){
        itemList.remove(item);
        assertNull(seller.selectItem("Mineral Water"));
        itemList.add(item);
    }

    @Test
    public void TestModifyMethod(){
        boolean checkCode = seller.modifyCode("Mineral Water", 1000 );
        boolean checkFalseCode = seller.modifyCode("Mineral water", 1000 );
        boolean checkNoCategory = seller.modifyCategory("Mineral Water", "shit");
        boolean checkFalseCategory = seller.modifyCategory("Mineral water", "shit");
        boolean checkTrueCategory = seller.modifyCategory("Mineral Water", "Chips");
        boolean checkQuantity = seller.modifyQuantity("Mineral Water", 8);
        boolean checkFalseQuantity = seller.modifyQuantity("Mineral water", 8);
        boolean checkQuantityOverflow = seller.modifyQuantity("Mineral Water", 16);
        boolean checkPrice = seller.modifyPrice("Mineral Water", 2.8);
        boolean checkFalsePrice = seller.modifyPrice("Mineral water", 2.8);
        boolean checkInvalidPrice = seller.modifyPrice("Mineral water", -0.2);
        boolean checkName = seller.modifyName("Mineral Water", "Water");
        boolean checkFalseName = seller.modifyName("cola", "Water");

        assertTrue(checkCode);
        assertFalse(checkFalseCode);
        assertFalse(checkNoCategory);
        assertFalse(checkFalseCategory);
        assertTrue(checkTrueCategory);
        assertTrue(checkQuantity);
        assertFalse(checkFalseQuantity);
        assertFalse(checkQuantityOverflow);
        assertTrue(checkPrice);
        assertFalse(checkFalsePrice);
        assertFalse(checkInvalidPrice);
        assertTrue(checkName);
        assertFalse(checkFalseName);
    }

    @Test
    public void TestGenerateReport1(){
        seller.generateReport1();
        try {
            File myReport = new File("src/main/java/W14B_G4_Assignment2/model/SellerReport1.txt");
            Scanner myReader = new Scanner(myReport);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                assertEquals(data, "code\tname\tcategory\tquantity\tprice\n");
                break;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestGenerateReport2() {
        seller.generateReport2();
        try {
            File myReport = new File("src/main/java/W14B_G4_Assignment2/model/SellerSummary.txt");
            Scanner myReader = new Scanner(myReport);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                assertEquals(data,"code\tname\ttotal number of quantity sold\n");
                break;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
