package W14B_G4_Assignment2.view;


import W14B_G4_Assignment2.model.ShoppingCart;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.sql.SQLException;

import W14B_G4_Assignment2.database.*;
import W14B_G4_Assignment2.model.*;

public class ShoppingItemController extends Controller{

    private double subTotal;
    private int amount;
    private double price;
    private ShoppingCartController shoppingControl;
    private Node node;
    private MachineEngine model;

    @FXML
    private Label itemName;

    @FXML
    private Label itemAmount;

    @FXML
    private Label itemTotal;

    @FXML
    private Button btRemove;

    @FXML
    private Button btAdd;

    @FXML
    private Label itemPrice;

    @FXML
    private Button btMinus;

    public ShoppingItemController(int quantity, double price, ShoppingCartController scc, MachineEngine model){
        this.price = price;
        this.amount = quantity;
        this.subTotal = this.price * this.amount;
        this.shoppingControl = scc;
        this.model = model;
    }

    @FXML
    public void handleAction(ActionEvent event) throws SQLException{
        if(event.getSource() == btAdd && ItemData.verifyItemQuantity(itemName.getText(), amount + 1)){
            amount += 1;
            subTotal = amount * price;
            shoppingControl.addTotal(price);
            model.addItem(itemName.getText(), 1);
        }

        if(event.getSource() == btMinus && amount > 0){
            amount -= 1;
            subTotal = amount * price;
            shoppingControl.removeTotal(price);
            model.removeItem(itemName.getText());
            model.addItem(itemName.getText(), amount);
        }

        if(event.getSource() == btRemove){
            shoppingControl.removeTotal(subTotal);
            shoppingControl.removeItems(node);
            model.removeItem(itemName.getText());
        }

        itemAmount.setText(Integer.toString(amount));
        itemTotal.setText(String.format("%.2f", subTotal));
    }

    public void setAllInfo(String name, int quantity, double price){
        this.itemName.setText(name);
        this.itemAmount.setText(Integer.toString(quantity));
        this.itemPrice.setText(String.format("%.2f", price));
        this.itemTotal.setText(String.format("%.2f", price*quantity));
    }

    public void setNode(Node node){
        this.node = node;
    }

}
