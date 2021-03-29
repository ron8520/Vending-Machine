package W14B_G4_Assignment2.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import W14B_G4_Assignment2.model.*;


public class ShoppingCartController extends Controller{

    @FXML
    private Button btCard, btCash, btCheckOut;

    @FXML
    private Label itemAmount, totalCost, methodSelect, goBack;

    @FXML
    private VBox items;
    private Stage shoppingStage, paymentStage;
    private URL cardUrl, cashUrl, itemUrl;;
    private FXMLLoader cardLoader, cashLoader;
    private CardController cardControl;
    private CashController cashControl;
    private Parent cardParent, cashParent;
    private Scene cardScene, cashScene;
    private Node node;
    private double total;
    private String username;
    private MachineEngine model;
    private DefaultController defaultControl;

    public ShoppingCartController(DefaultController defaultControl) throws IOException{
        this.username = defaultControl.getName();
        this.total = 0;
        this.shoppingStage = defaultControl.getStage();
        this.model = new MachineEngine(new Customer(username, "1"));
        this.defaultControl = defaultControl;
        setStage();
        setFXMLLoader();
    }

    @FXML
    public void handleMouseEvent(ActionEvent event) throws SQLException, IOException{
        if(event.getSource() == btCard){
            methodSelect.setText("Card");
        }

        if(event.getSource() == btCash){
            methodSelect.setText("Cash");
        }

        if(event.getSource() == btCheckOut){
            if(methodSelect.getText().equals("Card")){
                if(this.cardParent == null){
                    this.cardParent = this.cardLoader.load();
                    this.cardScene = new Scene(this.cardParent);
                }
                this.cardControl.updateTotal(total);
                this.paymentStage.setScene(this.cardScene);
                this.paymentStage.show();
                this.cardControl.restart();
            }

            if(methodSelect.getText().equals("Cash")){
                if (this.cashParent == null) {
                    this.cashParent = this.cashLoader.load();
                    this.cashScene = new Scene(this.cashParent);
                }
                this.cashControl.updateTotal(total);
                this.paymentStage.setScene(this.cashScene);
                this.paymentStage.show();
                this.cashControl.restart();

            }
        }

    }

    public void setFXMLLoader() throws IOException {
        this.cardUrl = new File("src/main/resources/card.fxml").toURL();
        this.cardLoader = new FXMLLoader();
        this.cardControl = new CardController(this);
        this.cardLoader.setLocation(this.cardUrl);
        this.cardLoader.setController(this.cardControl);

        this.cashUrl = new File("src/main/resources/cash.fxml").toURL();
        this.cashLoader = new FXMLLoader();
        this.cashControl = new CashController(this);
        this.cashLoader.setLocation(this.cashUrl);
        this.cashLoader.setController(this.cashControl);

        this.itemUrl = new File("src/main/resources/shoppingItem.fxml").toURL();
    }

    public void close(){
        this.shoppingStage.close();
    }

    public void setStage(){
        paymentStage = new Stage();
        paymentStage.setTitle("Payment");

        paymentStage.initStyle(StageStyle.UNDECORATED);
    }

    public boolean addItems(String name, int quantity, double price) throws IOException, SQLException {
        if (this.model.containItem(name)) return false;

        FXMLLoader itemL = new FXMLLoader();
        itemL.setLocation(itemUrl);
        ShoppingItemController itemC = new ShoppingItemController(quantity, price, this, model);
        itemL.setController(itemC);
        node = itemL.load();
        itemC.setNode(node);
        itemC.setAllInfo(name, quantity, price);
        items.getChildren().add(node);
        total += quantity * price;
        totalCost.setText(String.format("%.2f", total));
        itemAmount.setText(Integer.toString(Integer.valueOf(items.getChildren().size())));
        this.model.addItem(name, quantity);
        return true;
    }

    public void removeTotal(double subTotal){
        total -= subTotal;
        totalCost.setText(String.format("%.2f", total));
    }

    public void addTotal(double subTotal){
        total += subTotal;
        totalCost.setText(String.format("%.2f", total));
    }

    public void removeItems(Node node){
        items.getChildren().remove(node);
        itemAmount.setText(Integer.toString(Integer.valueOf(items.getChildren().size())));
    }

    public MachineEngine getEngine(){
        return this.model;
    }

    public DefaultController getDefault(){
        return this.defaultControl;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String name){
        this.username = name;
        this.model = new MachineEngine(new Customer(name, "1"));
    }

    public Stage getPaymentStage(){
        return this.paymentStage;
    }

    public void refresh() throws IOException, SQLException{
        this.shoppingStage.close();
        this.defaultControl.dropDrink();
        this.items.getChildren().clear();
        itemAmount.setText(Integer.toString(Integer.valueOf(items.getChildren().size())));;
        this.username = defaultControl.getName();
        this.total = 0;
        totalCost.setText(String.format("%.2f", total));
        this.shoppingStage = defaultControl.getStage();
        this.model = new MachineEngine(new Customer(username, "1"));
    }

}
