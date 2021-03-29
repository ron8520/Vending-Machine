package W14B_G4_Assignment2.view;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import W14B_G4_Assignment2.database.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ItemController extends Controller{

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemId;

    @FXML
    private Label itemName;

    @FXML
    private Label itemPrice;

    @FXML
    private Label itemStatus;

    @FXML
    private JFXTextField itemQuantity;

    @FXML
    private Button btAdd;

    @FXML
    private Image drink, chocolate, chip, candy;

    private String error_status = "Not Available";

    private ShoppingCartController cartController;

    public ItemController(ShoppingCartController cartController) throws IOException{
        loadingImage();
        this.cartController = cartController;
    }


    @FXML
    public void handleAction(ActionEvent event) throws IOException, SQLException {
        String quantity = itemQuantity.getText();

        if (!quantity.matches("\\d*")) {
            itemQuantity.setText(quantity.replaceAll("[^\\d]", ""));
            quantity = itemQuantity.getText();
        }

        if (quantity == null || quantity.length() < 1){
            return;
        }

        int q = Integer.valueOf(itemQuantity.getText());

        if(q < 0){
            itemStatus.setText(error_status);
            itemStatus.setTextFill(Color.web("#e35d68"));
        }else{

            if(ItemData.verifyItemQuantity(itemName.getText(), q)){
                boolean result = cartController.addItems(itemName.getText(), q, Double.valueOf(itemPrice.getText().substring(2)));
                if (result == false){
                    itemStatus.setText("Added");
                    itemStatus.setTextFill(Color.web("#ce7c5b"));
                }else{
                    itemStatus.setText("Available");
                    itemStatus.setTextFill(Color.web("#6cca84"));
                }
            }else{
                itemStatus.setText("Out of Stock");
                itemStatus.setTextFill(Color.web("#ce7c5b"));
            }
        }


    }

    public void setText(String type, String id, String name, String price){
        switch (type){
            case "Drinks":
                this.itemImage.setImage(drink);
                break;
            case "Chocolates":
                this.itemImage.setImage(chocolate);
                break;
            case "Candies":
                this.itemImage.setImage(candy);
                break;
            case "Chips":
                this.itemImage.setImage(chip);
                break;
        }
        this.itemId.setText(id);
        this.itemName.setText(name);
        this.itemPrice.setText("$ " + price);
    }

    public void setStatus(int quantity){
        if (quantity <= 0){
            itemStatus.setText("Out of Stock");
            itemStatus.setTextFill(Color.web("#e35d68"));
        }else{
            itemStatus.setText("Available");
            itemStatus.setTextFill(Color.web("#61d38b"));
        }
    }

    public String getId() { return itemId.getText(); }
    public String getName() { return itemName.getText(); }
    public String getPrice() { return itemPrice.getText(); }
    public String getStatus() { return itemStatus.getText(); }
    public String getQuantity() { return itemQuantity.getText(); }

    public void loadingImage() throws IOException{
        drink = new Image(new FileInputStream("src/main/resources/noun_Drink_3583231.png"));
        chocolate = new Image(new FileInputStream("src/main/resources/noun_Chocolate_1564420.png"));
        chip = new Image(new FileInputStream("src/main/resources/noun_snack_1134821.png"));
        candy = new Image(new FileInputStream("src/main/resources/noun_candy_3582024.png"));
    }

}
