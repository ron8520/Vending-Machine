package W14B_G4_Assignment2.database;

import W14B_G4_Assignment2.model.Item;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import java.util.*;

public class ItemData {

    public static HashMap<String, String> getItemQuantity(String type) throws SQLException{
        String sql = "select name, quantity from items where category=?";
        String[] param={type};
        ResultSet result = DBConnection.fetch(sql, param);

        if (result == null){
            return null;
        }

        HashMap<String, String> items = new HashMap<>();
        while (result.next()){
            String tempName = result.getString("name");
            String tempCode = result.getString("quantity");
            items.put(tempName, tempCode);
        }
        return items;
    }

    public static HashMap<String, String> getItemCode(String type) throws SQLException{
        String sql = "select name, code from items where category=? order by name";
        String[] param={type};
        ResultSet result = DBConnection.fetch(sql, param);

        if (result == null){
            return null;
        }

        HashMap<String, String> items = new HashMap<>();
        while (result.next()){
            String tempName = result.getString("name");
            String tempCode = result.getString("code");
            items.put(tempName, tempCode);
        }
        return items;
    }

    public static List<String> getAllItemCode() throws SQLException{
        String sql = "select code from items order by code";
        String[] param={};
        ResultSet result = DBConnection.fetch(sql, param);

        if (result == null){
            return null;
        }

        List<String> codes = new ArrayList<>();
        while (result.next()){
            String tempCode = result.getString("code");
            codes.add(tempCode);
        }
        return codes;
    }

    public static ArrayList<String> getAllItems() throws SQLException{
        String sql = "select name from items";
        String[] param={};
        ResultSet result =  DBConnection.fetch(sql, param);

        if (result == null){
            return null;
        }

        ArrayList<String> items = new ArrayList<>();
        while (result.next()){
            String temp = result.getString("name");
            items.add(temp);
        }
        return items;
    }

    public static boolean verifyItemQuantity(String item, int num) throws SQLException{
        String sql = "select name, quantity from items";
        ResultSet result =  DBConnection.fetch(sql);

        if (result == null){
            return false;
        }

        while (result.next()){
            String nameTemp = result.getString("name");
            int qTemp = result.getInt("quantity");

            if (item.equals(nameTemp)){
                if (num > qTemp){
                    return false;
                }
            }
        }
        return true;
    }

    public static HashMap<String, Double> getItemPrices() throws SQLException{
        String sql = "select name, price from items";
        ResultSet result =  DBConnection.fetch(sql);

        if (result == null){
            return null;
        }

        HashMap<String, Double> itemPrices = new HashMap<>();
        while (result.next()){
            String nameTemp = result.getString("name");
            double priceTemp = Double.valueOf(result.getString("price"));

            itemPrices.put(nameTemp, priceTemp);
        }

        return itemPrices;
    }

    public static HashMap<String, String> getItemPrices(String type) throws SQLException{
        String sql = "select name, price from items where category=?";
        String[] param = {type};
        ResultSet result =  DBConnection.fetch(sql, param);

        if (result == null){
            return null;
        }

        HashMap<String, String> itemPrices = new HashMap<>();
        while (result.next()){
            String nameTemp = result.getString("name");
            String priceTemp = result.getString("price");

            itemPrices.put(nameTemp, priceTemp);
        }

        return itemPrices;
    }

    public static int countCategoryItems(String category) throws SQLException{
        String sql = "select count(name) from items where category = ?";
        String[] param={category};
        ResultSet result =  DBConnection.fetch(sql, param);

        if (result == null){
            return 0;
        }

        if (result.next()){
            int count = result.getInt("count");
            return count;
        } else {
            return 0;
        }
    }

    public static boolean modifyCode(String name, String newCode) throws SQLException {
        String sql = "update items set code  = ? where name = ? ";
        String[] param = {newCode,name};
        DBConnection.update(sql,param);
        return true;
    }

    public static boolean modifyName(String name, String newName) throws SQLException {
        String sql = "update items set name  = ? where name = ? ";
        String[] param = {newName,name};
        DBConnection.update(sql,param);
        return true;
    }

    public static boolean modifyCategory(String name, String newCategory) throws SQLException {
        List<String> categoryList = Arrays.asList("Drinks", "Chocolates", "Chips", "Candies");
        if(!categoryList.contains(newCategory)) {
            return false; // no such category
        }
        String sql = "update items set category  = ? where name = ? ";
        String[] param = {newCategory,name};
        DBConnection.update(sql,param);

        return true;
    }

    public static boolean modifyQuantity(String name, String newQuantity) throws SQLException {
        int newQ = Integer.valueOf(newQuantity);

        if(newQ > 15 || newQ < 0) {
            return false; // reach the limit
        }
        String sql = "update items set quantity = " + newQ + " where name = ? ";
        String[] param = {name};
        DBConnection.update(sql,param);

        return true;
    }

    public static boolean deductQuantity(String name, int quantity) throws SQLException{
        String sql = "update items set quantity = quantity - " + quantity + " where name = ? ";
        String[] param = {name};
        DBConnection.update(sql, param);

        return true;
    }

    public static boolean increaseSold(String name, int quantity) throws SQLException{
        String sql = "update items set sold = sold + " + quantity + " where name = ? ";
        String[] param = {name};
        DBConnection.update(sql, param);

        return true;
    }

    public static boolean modifyPrice(String name, String newPrice) throws SQLException {
        if(Double.valueOf(newPrice) <= 0) {
            return false;
        }

        String sql = "update items set price  = " + Double.valueOf(newPrice) + " where name = ? ";
        String[] param = {name};
        DBConnection.update(sql,param);

        return true;
    }

    public static boolean generateReport() throws SQLException, IOException {
        String sql = "select * from items";
        ResultSet result = DBConnection.fetch(sql);

        if (result == null){
            return false;
        }

        HashMap<String, String[]> itemList = new HashMap<>();
        while (result.next()){
            String nameTemp = result.getString("name");
            String categoryTemp = result.getString("category");
            String priceTemp = result.getString("price");
            String quantityTemp = String.valueOf(result.getInt("quantity"));
            String soldTemp = String.valueOf(result.getInt("sold"));
            String codeTemp = result.getString("code");
            String[] arr = {nameTemp, categoryTemp, priceTemp, quantityTemp, soldTemp, codeTemp};

            itemList.put(nameTemp, arr);
        }

        // generate report without sold columns(report 1)

        FileWriter writer1 = new FileWriter("SellerReport1.txt");
        writer1.write("username\t\tcategory\t\tprice\tquantity\tcode\n");
        for(String i : itemList.keySet()) {
            writer1.write(itemList.get(i)[0] + "\t" + "\t" + "\t");
            writer1.write(itemList.get(i)[1] + "\t" + "\t" + "\t");
            writer1.write(itemList.get(i)[2] + "\t" + "\t");
            writer1.write(itemList.get(i)[3] + "\t" + "\t");
            writer1.write(itemList.get(i)[5] + "\n");
        }
        writer1.close();

        // generate report wit sold columns(report 2)
        FileWriter writer2 = new FileWriter("SellerSummary.txt");
        writer2.write("code\tname\t\ttotal number of quantity sold\n");
        for(String i : itemList.keySet()) {
            writer2.write(itemList.get(i)[5] + "\t" + "\t");
            writer2.write(itemList.get(i)[0] + "\t" + "\t" + "\t");
            writer2.write(itemList.get(i)[4] + "\n");
        }
        writer2.close();

        return true;
    }

    public static boolean insertItem(String name,String category, double price, int quantity, String code) throws SQLException{
        String sql = "insert into items values (?,?," + price + ", " + quantity + ", " + 0 + ",?)";
        String[] param = {name, category, code};
        DBConnection.update(sql,param);
        return true;
    }

    public static boolean verifyCode(String code) throws SQLException {
        String sql = "select count(*) from items where code = ?";
        String[] param = {code};
        ResultSet result = DBConnection.fetch(sql, param);
        if (result == null) {
            return false;
        } if (result.next()){
            int count = result.getInt("count");

            if(count == 0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

}
