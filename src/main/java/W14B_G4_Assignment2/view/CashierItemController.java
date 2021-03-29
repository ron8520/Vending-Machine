package W14B_G4_Assignment2.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class CashierItemController extends Controller{

    @FXML
    private Label lbNote, lbAmount;

    @FXML
    private Button btAdd, btRemove;

    @FXML
    private Label message;

    private int amount;
    private Node node;


    public CashierItemController(Label message){
        this.message = message;
    }

    @FXML
    public void handleAction(ActionEvent event){

        amount = Integer.parseInt(lbAmount.getText());

        if(event.getSource() == btAdd){
            amount += 1;
            lbAmount.setText(String.valueOf(amount));
            if(amount >= 5) {
                message.setText("");
            }
        }

        if(event.getSource() == btRemove){
            amount -= 1;

            if(amount <= 5){
                amount = 5;
                message.setText("The amount cannot be less than 5");
                message.setTextFill(Color.web("#e35d68"));

            } else {
                message.setText("");
            }
            lbAmount.setText(String.valueOf(amount));

        }

    }


    public void setAllInfo(String changeName, String quantity) {
        this.lbNote.setText(changeName);
        this.lbAmount.setText(quantity);
    }

    public void setNode(Node node){
        this.node = node;
    }

    public String getLbNote() {
        return this.lbNote.getText();
    }

    public String getLbAmount() {
        return this.lbAmount.getText();
    }

}
