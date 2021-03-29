package W14B_G4_Assignment2.view;

import W14B_G4_Assignment2.database.ItemData;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;

import java.sql.SQLException;

public class SellerItemController extends Controller{

    @FXML
    private JFXTextField lbItemCode, lbItemName, lbItemPrice, lbItemQuantity;

    @FXML
    private Label lbMessage;

    public SellerItemController(){
        lbItemCode = new JFXTextField();
        lbItemName = new JFXTextField();
        lbItemPrice = new JFXTextField();
        lbItemQuantity = new JFXTextField();
        lbMessage = new Label();
    }

    @FXML
    public void updateItem(ActionEvent event) throws SQLException {
        //TODO CHECK VALID INPUT FOR EACH FIELD, IF VALID UPDATE TO SQL DATABASE
        if(event.getSource() == lbItemCode){
            String oldCode = lbItemCode.getPromptText();
            String newCode = lbItemCode.getText();
            String name = lbItemName.getPromptText();

            if (!oldCode.matches("\\d*") || !newCode.matches("\\d*")) {
                lbItemCode.setPromptText(oldCode.replaceAll("[^\\d]", ""));
                lbItemCode.setText(newCode.replaceAll("[^\\d]", ""));
                oldCode = lbItemCode.getPromptText();
                newCode = lbItemCode.getText();
            }

            if (newCode == null || newCode.length()<1){
                return;
            }

            boolean valid = ItemData.verifyCode(newCode);
            if(valid) {
                ItemData.modifyCode(name,newCode);
                lbMessage.setText("done");
                lbMessage.setTextFill(Color.web("#61d38b"));
                lbItemCode.setPromptText(lbItemCode.getText());
            } else {
                ItemData.modifyCode(name,oldCode);
                lbMessage.setText("Duplicate code"); // Notify the seller the code has already existed.
                lbMessage.setTextFill(Color.web("#e35d68"));
                return;
            }
        }

        if(event.getSource() == lbItemName){
            String newName = lbItemName.getText();
            String oldName = lbItemName.getPromptText();
            if(!ItemData.modifyName(oldName, newName)) {
                lbMessage.setText("Invalid name");
                lbMessage.setTextFill(Color.web("#e35d68"));
                return;
            } else {
                lbMessage.setText("done");
                lbMessage.setTextFill(Color.web("#61d38b"));
                lbItemName.setPromptText(lbItemName.getText());
            }
        }

        if(event.getSource() == lbItemPrice){
            String price = lbItemPrice.getText();
            String name = lbItemName.getPromptText();

            if (!price.matches("\\d*")) {
                lbItemPrice.setText(price.replaceAll("[^\\d.]", ""));
                price = lbItemPrice.getText();
            }

            if (price == null || price.length()<1){
                return;
            }

            Double priceNumber = ((double) ((int) (Double.parseDouble(price) * 10))) /10;
            lbItemPrice.setText(priceNumber.toString());
            price = lbItemPrice.getText();

            if (price == null || price.length()<1){
                return;
            }

            if(!ItemData.modifyPrice(name, price)) {
                lbMessage.setText("Invalid price!"); // need change into a promptext to notify the seller!
                lbMessage.setTextFill(Color.web("#e35d68"));
                return;
            } else {
                lbMessage.setText("done");
                lbMessage.setTextFill(Color.web("#61d38b"));
                lbItemPrice.setPromptText(lbItemPrice.getText());
            }
        }

        if(event.getSource() == lbItemQuantity){
            String quantity = lbItemQuantity.getText();
            String name = lbItemName.getPromptText();

            if (!quantity.matches("\\d*")) {
                lbItemQuantity.setText(quantity.replaceAll("[^\\d]", ""));
                quantity = lbItemQuantity.getText();
            }
            if (quantity == null || quantity.length()<1){
                return;
            }
            if(!ItemData.modifyQuantity(name, quantity)) {
                lbMessage.setText("Invalid quantity!"); // need change into a promp text to notify the seller!
                lbMessage.setTextFill(Color.web("#e35d68"));
                return;
            } else {
                lbMessage.setText("done");
                lbMessage.setTextFill(Color.web("#61d38b"));
                lbItemQuantity.setPromptText(lbItemQuantity.getText());
            }
        }

    }

    public void setPromptText(String code, String name, String price, String quantity){
        lbItemCode.setPromptText(code);
        lbItemName.setPromptText(name);
        lbItemPrice.setPromptText(price);
        lbItemQuantity.setPromptText(quantity);
    }

    public void setMessage(int quantity){
        if (quantity <= 0){
            lbMessage.setText("Out of Stock");
            lbMessage.setTextFill(Color.web("#e35d68"));
        }else{
            lbMessage.setText("Available");
            lbMessage.setTextFill(Color.web("#61d38b"));
        }
    }


}
