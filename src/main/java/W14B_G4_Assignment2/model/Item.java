package W14B_G4_Assignment2.model;

import java.util.*;

public class Item {

    private int code;
    private String name;
    private String category;
    private int quantity;
    private double price;
    private int sold; // the total number of sold for each item

    public Item(int code, String name, String category, int quantity, double price, int sold) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.sold = sold;
    }

    // getters

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getPrice() {
        return this.price;
    }

    public int getSold() {
        return this.sold;
    }

    //setters

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
