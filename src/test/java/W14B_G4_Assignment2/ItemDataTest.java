package W14B_G4_Assignment2;

import java.sql.ResultSet;
import java.util.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import W14B_G4_Assignment2.model.*;
import W14B_G4_Assignment2.database.*;
import java.sql.SQLException;

public class ItemDataTest {

    @BeforeEach
    public void setup(){
        DBConnection.connect();
    }

    @Test
    public void getItemQuantityTest() {
        try {
            String sql = "select name, quantity from items where category=?";
            String[] param={"Drinks"};
            ResultSet result = DBConnection.fetch(sql, param);
            HashMap<String, String> items = new HashMap<>();
            while (result.next()){
                String tempName = result.getString("name");
                String tempCode = result.getString("quantity");
                items.put(tempName, tempCode);
            }
            assertEquals(items, ItemData.getItemQuantity("Drinks"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getItemCodeTest() {
        try {
            String sql = "select name, code from items where category=? order by name";
            String[] param = {"Candies"};
            ResultSet result = DBConnection.fetch(sql, param);
            HashMap<String, String> items = new HashMap<>();
            while (result.next()) {
                String tempName = result.getString("name");
                String tempCode = result.getString("code");
                items.put(tempName, tempCode);
            }
            assertEquals(items, ItemData.getItemCode("Candies"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAllItemCodeTest() {
        try {
            String sql = "select code from items order by code";
            String[] param={};
            ResultSet result = DBConnection.fetch(sql, param);
            List<String> codes = new ArrayList<>();
            while (result.next()){
                String tempCode = result.getString("code");
                codes.add(tempCode);
            }
            assertEquals(codes, ItemData.getAllItemCode());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getItemPricesTest() {
        try {
            String sql = "select name, price from items where category=?";
            String[] param = {"Chips"};
            ResultSet result = DBConnection.fetch(sql, param);
            HashMap<String, String> items = new HashMap<>();
            while (result.next()) {
                String tempName = result.getString("name");
                String tempPrice = result.getString("price");
                items.put(tempName, tempPrice);
            }
            assertEquals(items, ItemData.getItemPrices("Chips"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getAllItemsCodesTest(){
        try{
            String sql = "select name from items";
            String[] param={};
            ResultSet result =  DBConnection.fetch(sql, param);
            ArrayList<String> items = new ArrayList<>();
            while (result.next()){
                String temp = result.getString("name");
                items.add(temp);
            }
            assertEquals(items, ItemData.getAllItems());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void countCategoryItemsTest(){
        try{
            assertEquals(3, ItemData.countCategoryItems("Candies"));
            assertEquals(0, ItemData.countCategoryItems("error"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void generateReportTest()throws SQLException{
        try{
            assertTrue(ItemData.generateReport());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void modifyTest() throws SQLException{
        assertTrue(ItemData.modifyCode("Sugar", "21"));
        assertTrue(ItemData.modifyName("Sugar", "Sugar"));
        assertTrue(ItemData.modifyCategory("Sugar", "Candies"));
        assertFalse(ItemData.modifyCategory("Sugar", "error"));
        assertTrue(ItemData.modifyQuantity("Tonic water", "14"));
        assertFalse(ItemData.modifyQuantity("Tonic Water", "100"));
        assertFalse(ItemData.modifyQuantity("Tonic Water", "-1"));
        assertTrue(ItemData.modifyPrice("Sugar", "3.5"));
        assertFalse(ItemData.modifyPrice("Sugar", "-1"));
    }

    @Test
    public void insertItemTest() throws SQLException{
        assertTrue(ItemData.insertItem("a Chocolate", "Chocolates", 1, 1,"100"));
    }

    @Test
    public void verifyCodeTest() throws SQLException{
        assertTrue(ItemData.verifyCode("99"));
        assertFalse(ItemData.verifyCode("1"));
    }
}
