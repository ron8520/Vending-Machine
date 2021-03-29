package W14B_G4_Assignment2.model;

import java.util.*;
import java.io.*;

public class Owner extends User{

    /* Since the owner user can can modify the items details, the available changes
    and generate all the reports, he/she can do the job of Seller and Cashier*/

    private Seller sellerJob;
    private Cashier cashierJob;

    public Owner(String username, String password, ArrayList<Item> availableItems) {
        this.username = username;
        this.password = password;
        this.sellerJob = new Seller(username, password, availableItems );
        this.cashierJob = new Cashier(username, password);
    }

    // Does the Seller's Job
    public boolean modifyItemDetails(String itemName, String field, Object newValue) {

        boolean ret = false;

        if(field == "code") {
            ret = sellerJob.modifyCode(itemName, (int)newValue);
        }

        else if(field == "name") {
            ret = sellerJob.modifyName(itemName, (String) newValue);
        }

        else if(field == "category") {
            ret = sellerJob.modifyCategory(itemName, (String) newValue);
        }

        else if(field == "quantity") {
            ret = sellerJob.modifyQuantity(itemName, (int)newValue);
        }

        else if(field == "price") {
            ret = sellerJob.modifyPrice(itemName, (double)newValue);
        }
        return ret;
    }

    public boolean generateSellerReport() {
        sellerJob.generateReport1();

        return true;
    }

    public boolean generateSellerSummary() {
        sellerJob.generateReport2();

        return true;
    }

    // Does the Cashier's Job

    public boolean modifyChange(String field, Object newValue) {
        if (field == "addChange") {
            cashierJob.addChange((String)newValue);
            return true;
        }
        else if(field == "removeChange") {
            cashierJob.removeChange((String)newValue);
            return true;
        }
        /*else if(field == "addTransaction") {
            ;
        }
        else if(field == "removeTransaction") {
            ;
        }*/
        return false;
    }

    // functions to generate cashier's report are needed (waiting for them to be added in Cashier class)

    // Owner-only functions
    /*public void getUserList() {
        ;
    }

    public void getCancelList() {
        ;
    }*/


}
