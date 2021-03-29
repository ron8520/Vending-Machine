package W14B_G4_Assignment2.model;

import java.sql.ResultSet;
import java.util.*;
import W14B_G4_Assignment2.database.*;

import java.sql.SQLException;

public class MachineEngine{
    private User user;
    private ArrayList<String> currentPaidCashes;

    public MachineEngine(User user){
        this.user = user;
        this.currentPaidCashes = new ArrayList<String>();
    }

    public void addCash(String cash){
        this.currentPaidCashes.add(cash);
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public boolean containItem(String item){
        return this.user.getCart().getItems().containsKey(item);
    }

    public boolean addItem(String item, int num) throws SQLException{

        if (!ItemData.getAllItems().contains(item)){
            return false;
        }else if (!ItemData.verifyItemQuantity(item, this.user.getCart().getItemNum(item) + num)){
            return false;
        }

        this.user.getCart().addItem(item, num);
        return true;
    }

    public boolean removeItem(String item){
        this.user.getCart().removeItem(item);
        return true;
    }

    public double calculateTotalPrices() throws SQLException{
        double total = 0.0;

        HashMap<String, Double> prices = ItemData.getItemPrices();
        for (String i:this.user.getCart().getItems().keySet()){
            for (String j:prices.keySet()){
                if (i.equals(j)){
                    total += prices.get(j) * this.user.getCart().getItemNum(i);
                    break;
                }
            }
        }

        return total;
    }

    public double calculatePaidCashes(){
        double current = 0.0;
        for (int i = 0;i < this.currentPaidCashes.size();i++){
            if (this.currentPaidCashes.get(i).charAt(0) == '$'){
                current += Double.valueOf(this.currentPaidCashes.get(i).substring(1));
            }else{
                current += Double.valueOf(this.currentPaidCashes.get(i).substring(0,2))/100;
            }
        }
        return current;
    }

    // Available cashes are $1, $2, $5, $10, $20, 10c, 20c, 50c
    public boolean moneyEnough() throws SQLException{
        double total  = calculateTotalPrices();

        double current = calculatePaidCashes();

        if (current >= total){
            return true;
        }

        return false;
    }

    public boolean payByCash(double current, double change) throws SQLException{

        int txnID = TransactionData.getTransactionNum() + 1;
        String newTransactionQuery = "insert into transactions values (" + txnID + "," + "'Cash'," + current + "," + change + "," + "current_timestamp," + "?," + "'Completed')";
        String[] param1 = {this.user.getName()};
        TransactionData.makeTransaction(newTransactionQuery, param1);

        for (String item:this.user.getCart().getItems().keySet()){
            String newTransactionItemQuery = "insert into transaction_sold_items values (" + txnID + "," + "?," + this.user.getCart().getItemNum(item) + ")";
            String[] param2 = {item};
            TransactionData.makeTransaction(newTransactionItemQuery, param2);

            ItemData.deductQuantity(item, this.user.getCart().getItemNum(item));
            ItemData.increaseSold(item, this.user.getCart().getItemNum(item));
        }

        ChangesData.addChanges(this.currentPaidCashes);
        this.currentPaidCashes.clear();
        return true;
    }

    public boolean verifyCard(String name, String number) throws SQLException{
        String sql = "select * from cards where name = ? and number = ?";
        String[] param = {name, number};
        ResultSet result = DBConnection.fetch(sql, param);
        int count = 0;
        while (result.next()){
            count += 1;
        }
        if (count == 0){
            return false;
        }
        return true;
    }

    public boolean payByCreditCard(String name, String number, double current) throws SQLException{
        int txnID = TransactionData.getTransactionNum() + 1;
        String newTransactionQuery = "insert into transactions values (" + txnID + "," + "'Card'," + current + "," + "0" + "," + "current_timestamp," + "?, " + "'Completed')";
        String[] param1 = {this.user.getName()};
        TransactionData.makeTransaction(newTransactionQuery, param1);

        for (String item:this.user.getCart().getItems().keySet()){
            String newTransactionItemQuery = "insert into transaction_sold_items values (" + txnID + "," + "?," + this.user.getCart().getItemNum(item) + ")";
            String[] param2 = {item};
            TransactionData.makeTransaction(newTransactionItemQuery, param2);
            ItemData.deductQuantity(item, this.user.getCart().getItemNum(item));
            ItemData.increaseSold(item, this.user.getCart().getItemNum(item));
        }

        return true;

    }

    public LinkedHashMap<String, Integer> getChange(double money, LinkedHashMap<String, String> available){
        LinkedHashMap change = ChangesData.createHashMap();
        for (String key : available.keySet()) {
            int amount = Integer.parseInt(available.get(key));
            double value = ChangesData.getValue(key);
            int used = 0;
            while(amount > 0){
                if(money < value){
                    break;
                }
                money -= value;
                amount -= 1;
                used += 1;
            }
            change.put(key,used);
        }

        if(money > 0){
            return null;
        }
        return change;
    }

    public void addCard(String username, String cardname) throws SQLException{
        String sql = "select * from user_cards where username=?";
        String[] params={username};
        ResultSet result = DBConnection.fetch(sql, params);
        int count = 0;
        while (result.next()){
            count += 1;
        }
        if (count == 0){
            sql = "insert into user_cards values (?,?)";
            String[] paramsTemp = {username, cardname};
            DBConnection.update(sql, paramsTemp);
        }else{
            sql = "update user_cards set cardname = ? where username=?";
            String[] paramsTemp = {cardname, username};
            DBConnection.update(sql, paramsTemp);
        }
    }

    public void cancelTransaction(double current, double change, String type) throws SQLException{
        int txnID = TransactionData.getTransactionNum() + 1;
        String newTransactionQuery = "insert into transactions values (" + txnID + "," + "'" + type + "'" + "," + current + "," + change + "," + "current_timestamp," + "?," + "'Cancelled')";
        String[] param1 = {this.user.getName()};
        TransactionData.makeTransaction(newTransactionQuery, param1);
    }

}
