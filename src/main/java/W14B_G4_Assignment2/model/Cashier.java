package W14B_G4_Assignment2.model;
import java.util.*;

public class Cashier extends User{

    private List<String> changes = new ArrayList<>();
    //private List<Transaction> transactions = new ArrayList<>();

    public Cashier(String username, String password){
        this.username = username;
        this.password = password;
    }


    public List<String> getChanges(){
        return this.changes;
    }

    /*public List<Transaction> getTransactions(){
        return this.transactions;
    }*/

    public void addChange(String change){
        this.changes.add(change);
    }

    public void removeChange(String change){
        this.changes.remove(change);
    }

    /*public void addTranstion(Transaction transaction){
        this.transactions.add(transaction);
    }*/

    /*public void removeTranstion(Transaction transaction){
        this.transactions.remove(transaction);
    }*/

}
