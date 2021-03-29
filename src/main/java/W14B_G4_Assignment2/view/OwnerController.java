package W14B_G4_Assignment2.view;

import W14B_G4_Assignment2.database.ChangesData;
import W14B_G4_Assignment2.database.ItemData;
import W14B_G4_Assignment2.database.TransactionData;
import W14B_G4_Assignment2.database.UserData;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class OwnerController extends Controller{

    @FXML
    private Button btExit, btReport, btAdd, btConfirm;

    @FXML
    private VBox vbItemList, vbCashList, vbNameList;

    @FXML
    private JFXTextField lbItemCode, lbItemName, lbItemPrice, lbItemQuantity;


    @FXML
    private Label lbMessage;

    @FXML
    private AnchorPane drink, candy, chip, chocolate;

    @FXML
    private ChoiceBox cbCategory;

    private Stage ownerStage;
    private URL itemUrl, cashUrl, nameUrl;
    private CashierItemController cashierItemControl;
    private String error = "Field Cannot be Empty";
    private List<CashierItemController> controllersList;

    @Override
    public void initialize(URL url, ResourceBundle resources){
        try{
            addItems("Drinks");
            addCash();
            addName();
            addCategory();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public OwnerController(Stage ownerStage) throws IOException{
        this.ownerStage = ownerStage;
        setURL();
    }

    public void setURL() throws IOException{
        itemUrl = new File("src/main/resources/SellerItem.fxml").toURL();
        cashUrl = new File("src/main/resources/CashierItem.fxml").toURL();
        nameUrl = new File("src/main/resources/UserItem.fxml").toURL();
    }

    @FXML
    public void handleMouseEvent(ActionEvent event) throws SQLException, IOException{
        if(event.getSource() == btExit){
            this.ownerStage.close();
        }

        //TODO: Export all the reports;
        if(event.getSource() == btReport){

            if(ItemData.generateReport() && ChangesData.generateReport() &&
                    UserData.generateUserReport() && TransactionData.generateCancelledTrans()) {
                lbMessage.setText("Report Export Successful");
                lbMessage.setTextFill(Color.web("#61d38b"));

            }else{
                lbMessage.setText("Report Export Failed");
                lbMessage.setTextFill(Color.web("#e35d68"));
            }
        }

        if(event.getSource() == btAdd){

            JFXTextField[] inputs = {lbItemCode, lbItemName, lbItemPrice, lbItemQuantity};

            //TODO: check valid input
            for(JFXTextField input : inputs){
                if(input.getText().equals("") || input.getText() == null){
                    lbMessage.setText(error);
                    lbMessage.setTextFill(Color.web("#e35d68"));
                    return;
                }
            }

            try{
                List<String> items =  ItemData.getAllItems();
                List<String> categoryList = Arrays.asList("Drinks", "Chocolates", "Chips", "Candies");
                List<String> codes = ItemData.getAllItemCode();
                String name = lbItemName.getText();
                String category = cbCategory.getValue().toString();
                String code = lbItemCode.getText();
                String quantity = lbItemQuantity.getText();
                String price = lbItemPrice.getText();

                if (!quantity.matches("\\d*")) {
                    lbItemQuantity.setText(quantity.replaceAll("[^\\d]", ""));
                    quantity = lbItemQuantity.getText();
                }
                if (quantity == null || quantity.length() < 1){
                    return;
                }

                if (!code.matches("\\d*")) {
                    lbItemCode.setText(code.replaceAll("[^\\d]", ""));
                    code = lbItemCode.getText();
                }

                if (code == null || code.length()<1){
                    return;
                }

                if (!price.matches("\\d*")) {
                    lbItemPrice.setText(price.replaceAll("[^\\d.]", ""));
                    price = lbItemPrice.getText();
                }

                if (price == null || price.length() < 1){
                    return;
                }

                Double priceNumber = ((double) ((int) (Double.parseDouble(price) * 10))) /10;
                lbItemPrice.setText(priceNumber.toString());
                price = lbItemPrice.getText();

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

                }else if (!categoryList.contains(category)){
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

        if(event.getSource() == btConfirm){
            if(event.getSource() == btConfirm){

                for(CashierItemController cii : controllersList) {
                    String changeName = cii.getLbNote();
                    String newQuantity = cii.getLbAmount();
                    if(!ChangesData.modifyChange(changeName, newQuantity)) {
                        return;
                    }
                }
                lbMessage.setText("Cash Change Successful");
                lbMessage.setTextFill(Color.web("#61d38b"));
                this.vbCashList.getChildren().clear();
                addCash();
            }
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

    public void addItems(String type) throws IOException, SQLException{
        List<Node> items = new ArrayList<>();
        HashMap<String, String> codes = ItemData.getItemCode(type);
        HashMap<String, String> prices = ItemData.getItemPrices(type);
        HashMap<String, String> quantities = ItemData.getItemQuantity(type);

        for(String name: codes.keySet()) {
            FXMLLoader itemFXML = new FXMLLoader();
            SellerItemController itemC = new SellerItemController();
            String code = codes.get(name);
            String price = prices.get(name);
            String quantity = quantities.get(name);

            itemFXML.setLocation(itemUrl);
            itemFXML.setController(itemC);

            items.add(itemFXML.load());
            itemC.setPromptText(code, name, price, quantity);
            itemC.setMessage(Integer.parseInt(quantity));

            vbItemList.getChildren().add(items.get(items.size() - 1));
        }
    }


    //TODO
    public void addCash() throws IOException, SQLException{
        //TODO
        List<Node> cashes = new ArrayList<>();
        Node cash;
        controllersList = new ArrayList<>();

        LinkedHashMap<String, String> changes = ChangesData.getChanges();
        for(String changeName: changes.keySet()) {
            FXMLLoader cashFXML = new FXMLLoader();
            cashierItemControl = new CashierItemController(lbMessage);

            cashFXML.setLocation(cashUrl);
            cashFXML.setController(cashierItemControl);

            cash = cashFXML.load();
            cashierItemControl.setNode(cash);
            cashierItemControl.setAllInfo( changeName, changes.get(changeName) );
            controllersList.add(cashierItemControl);

            cashes.add(cash);
            vbCashList.getChildren().add(cashes.get(cashes.size() - 1));
        }
    }

    //TODO
    public void addName() throws IOException, SQLException{
        vbNameList.getChildren().clear();
        List<Node> names = new ArrayList<>();
        //TODO get the information from query;
        HashMap<String, String> users = UserData.getUser();

        for(String username: users.keySet()){
            FXMLLoader nameFXML = new FXMLLoader();
            OwnerItemController nameC = new OwnerItemController(lbMessage, this);

            nameFXML.setLocation(nameUrl);
            nameFXML.setController(nameC);

            names.add(nameFXML.load());
            nameC.setText(username, users.get(username));
            vbNameList.getChildren().add(names.get(names.size() - 1));
        }

    }

    public void addCategory(){
        cbCategory.getItems().add("Drinks");
        cbCategory.getItems().add("Chocolates");
        cbCategory.getItems().add("Candies");
        cbCategory.getItems().add("Chips");
    }

    public void addNewItem() throws SQLException {
        String category = cbCategory.getValue().toString();
        String name = lbItemName.getText();
        String code = lbItemCode.getText();
        double price = Double.parseDouble(lbItemPrice.getText());
        int quantity = Integer.parseInt(lbItemQuantity.getText());

        ItemData.insertItem(name,category,price, quantity, code);
    }

}
