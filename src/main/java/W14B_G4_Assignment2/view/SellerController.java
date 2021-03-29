package W14B_G4_Assignment2.view;

import W14B_G4_Assignment2.database.ChangesData;
import W14B_G4_Assignment2.database.TransactionData;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.lang.NumberFormatException;
import java.lang.NullPointerException;
import W14B_G4_Assignment2.database.ItemData;


public class SellerController extends Controller{

    @FXML
    private Button btAdd, btReport, btExit;

    @FXML
    private VBox vbItemList;

    private Stage sellerStage;

    @FXML
    private ChoiceBox cbCategory;

    @FXML
    private AnchorPane drink, candy, chip, chocolate;

    @FXML
    private Label lbMessage;

    private String reportSuccessful= "Report Export Successful";
    private String error;
    private List<Node> nodes;
    private URL sellerItemUrl;
    private SellerItemController sellerItemControl;

    @FXML
    private JFXTextField itemCode, itemName, itemPrice, itemQuantity;

    @Override
    public void initialize(URL url, ResourceBundle resources){
        try{
            addItems("Drinks");
            addCategory();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public SellerController(Stage sellerStage) throws IOException{
        this.sellerStage = sellerStage;
        this.sellerItemUrl = new File("src/main/resources/SellerItem.fxml").toURL();
    }

    @FXML
    public void handleMouseEvent(ActionEvent event) throws SQLException, IOException {
        if(event.getSource() == btAdd){

            JFXTextField[] inputFiled = {itemCode, itemName, itemPrice, itemQuantity};

            for(JFXTextField input : inputFiled){
                if(input.getText().equals("")){
                    error = "Field Cannot be Empty";
                    lbMessage.setText(error);
                    lbMessage.setTextFill(Color.web("#e1414e"));
                    return;
                }
            }

            try{
                List<String> items =  ItemData.getAllItems();
                List<String> categoryList = Arrays.asList("Drinks", "Chocolates", "Chips", "Candies");
                List<String> codes = ItemData.getAllItemCode();
                String category = cbCategory.getValue().toString();
                String name = itemName.getText();
                String code = itemCode.getText();
                String price = itemPrice.getText();
                String quantity = itemQuantity.getText();

                if (!quantity.matches("\\d*")) {
                    itemQuantity.setText(quantity.replaceAll("[^\\d]", ""));
                    quantity = itemQuantity.getText();
                }
                if (quantity == null || quantity.length() < 1){
                    return;
                }

                if (!code.matches("\\d*")) {
                    itemCode.setText(code.replaceAll("[^\\d]", ""));
                    code = itemCode.getText();
                }

                if (code == null || code.length()<1){
                    return;
                }

                if (!price.matches("\\d*")) {
                    itemPrice.setText(price.replaceAll("[^\\d.]", ""));
                    price = itemPrice.getText();
                }

                if (price == null || price.length() < 1){
                    return;
                }

                Double priceNumber = ((double) ((int) (Double.parseDouble(price) * 10))) /10;
                itemPrice.setText(priceNumber.toString());
                price = itemPrice.getText();

                if (price == null || price.length() < 1){
                    return;
                }

                int code_num = Integer.parseInt(code);

                if (codes.contains(code)){
                    error = "Code Has Existed";
                    lbMessage.setText(error);
                    lbMessage.setTextFill(Color.web("#e1414e"));
                    return;
                }else if (items.contains(name)){
                    error = "Item Has Existed";
                    lbMessage.setText(error);
                    lbMessage.setTextFill(Color.web("#e1414e"));
                    return;
                }else if ( !categoryList.contains(category)){
                    error = "No such category";
                    lbMessage.setText(error);
                    lbMessage.setTextFill(Color.web("#e1414e"));
                    return;
                }else if (Double.parseDouble(price) < 0 || Integer.parseInt(quantity) < 0 || Integer.parseInt(quantity) > 15){
                    error = "Invalid Number";
                    lbMessage.setText(error);
                    lbMessage.setTextFill(Color.web("#e1414e"));
                    return;
                }
                lbMessage.setText("Add New Item Successfully");
                lbMessage.setTextFill(Color.web("#61d38b"));

            }catch (NumberFormatException e){
                error = "Invalid Former of Number";
                lbMessage.setText(error);
                lbMessage.setTextFill(Color.web("#e1414e"));
                return;

            }catch (NullPointerException e){
                error = "Please Select Category";
                lbMessage.setText(error);
                lbMessage.setTextFill(Color.web("#e1414e"));
                return;
            }

            addNewItem();
            this.vbItemList.getChildren().clear();
            addItems(cbCategory.getValue().toString());

        }

        if(event.getSource() == btExit){
            this.sellerStage.close();
        }

        if(event.getSource() == btReport){

            if(ItemData.generateReport()) {
                lbMessage.setText(reportSuccessful);
                lbMessage.setTextFill(Color.web("#61d38b"));
            } else{
                lbMessage.setText("Report Export Failed");
                lbMessage.setTextFill(Color.web("#e35d68"));
            }

        }
    }

    public void addNewItem() throws SQLException {
        String category = cbCategory.getValue().toString();
        String name = itemName.getText();
        String code = itemCode.getText();
        double price = Double.parseDouble(itemPrice.getText());
        int quantity = Integer.parseInt(itemQuantity.getText());

        ItemData.insertItem(name,category,price, quantity, code);

    }



    public void addItems(String type) throws IOException, SQLException {
        nodes = new ArrayList<>();
        HashMap<String, String> codes = ItemData.getItemCode(type);
        HashMap<String, String> prices = ItemData.getItemPrices(type);
        HashMap<String, String> quantities = ItemData.getItemQuantity(type);

        for(String name: codes.keySet()){
            FXMLLoader itemFXML = new FXMLLoader();
            sellerItemControl = new SellerItemController();
            String code = codes.get(name);
            String price = prices.get(name);
            String quantity = quantities.get(name);

            itemFXML.setLocation(sellerItemUrl);
            itemFXML.setController(sellerItemControl);

            nodes.add(itemFXML.load());
            sellerItemControl.setPromptText(code, name, price, quantity);
            sellerItemControl.setMessage(Integer.parseInt(quantity));
            vbItemList.getChildren().add(nodes.get(nodes.size() - 1));
        }

    }

    //when the mouse click the drink pane
    public void dropDrink() throws IOException, SQLException {
        vbItemList.getChildren().clear();
        addItems("Drinks");
    }

    //when the mouse click the chocolate pane
    public void dropChocolate() throws IOException, SQLException{
        vbItemList.getChildren().clear();
        addItems("Chocolates");
    }

    //when the mouse click candy pane
    public void dropCandy() throws IOException, SQLException{
        vbItemList.getChildren().clear();
        addItems("Candies");
    }

    //when the mouse click chip pane
    public void dropChip() throws IOException, SQLException{
        vbItemList.getChildren().clear();
        addItems("Chips");
    }

    public void addCategory(){
        cbCategory.getItems().add("Drinks");
        cbCategory.getItems().add("Chocolates");
        cbCategory.getItems().add("Candies");
        cbCategory.getItems().add("Chips");
    }
}
