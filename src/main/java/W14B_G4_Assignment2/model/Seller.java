package W14B_G4_Assignment2.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.io.*;

public class Seller extends User{

    private final int quantityLimit = 15;

    private ArrayList<Item> itemList;
    private List<String> categoryList = Arrays.asList("Drinks", "Chocolates", "Chips", "Candies");
    // Do we store the total number of quantity sold for each item inside item database?
    // private ArratList<Transaction> transactionList = new ArratList<Transaction>();


    public Seller(String username, String password, ArrayList<Item> availableItems) {
        this.username = username;
        this.password = password;
        this.itemList = availableItems;
    }

    public Item selectItem(String itemName) {
        for(Item i : itemList) {
            if(i.getName() == itemName) {
                return i;
            }
        }
        return null;  // the item needs to be added
    }

    public boolean modifyCode(String name, int newCode) {
        Item itemChosen = this.selectItem(name);
        if(itemChosen != null) {
            itemChosen.setCode(newCode);
            return true;
        }
        return false;
    }

    public boolean modifyName(String name, String newName) {
        Item itemChosen = this.selectItem(name);
        if(itemChosen != null) {
            itemChosen.setName(newName);
            return true;
        }
        return false;
    }

    public boolean modifyCategory(String name, String newCategory) {
        Item itemChosen = this.selectItem(name);
        if(itemChosen != null) {
            if(categoryList.contains(newCategory)) {
                itemChosen.setCategory(newCategory);
                return true;
            }

        }
        return false;
    }

    public boolean modifyQuantity(String name, int newQuantity) {
        Item itemChosen = this.selectItem(name);
        if(itemChosen != null) {
            if(newQuantity <= quantityLimit) {
                itemChosen.setQuantity(newQuantity);
                return true;
            }
        }
        return false;
    }

    public boolean modifyPrice(String name, Double newPrice) {
        Item itemChosen = this.selectItem(name);
        if(itemChosen != null) {
            if(newPrice > 0) {
                itemChosen.setPrice(newPrice);
                return true;
            }
        }
        return false;
    }

    public void generateReport1() { // generate a txt file containing items with details
        try {
            FileWriter writer = new FileWriter("SellerReport1.txt");
            writer.write("code\tname\tcategory\tquantity\tprice\n");
            for(Item i : itemList) {
                writer.write(String.valueOf(i.getCode()) + "\t");
                writer.write(i.getName() + "\t");
                writer.write(i.getCategory() + "\t");
                writer.write(String.valueOf(i.getQuantity()) + "\t");
                writer.write(String.valueOf(i.getPrice()) + "\t" + "\n");
            }
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void generateReport2() { // generate a txt file containing items with its sales details(Summary)
        try {
            FileWriter writer = new FileWriter("SellerSummary.txt");
            writer.write("code\tname\ttotal number of quantity sold\n");
            for(Item i : itemList) {
                writer.write(String.valueOf(i.getCode()) + "\t");
                writer.write(i.getName() + "\t");
                writer.write(String.valueOf(i.getSold()) + "\t");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
