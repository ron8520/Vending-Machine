package W14B_G4_Assignment2.view;

import W14B_G4_Assignment2.database.*;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.transitions.hamburger.HamburgerNextArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.sql.SQLException;

public class DefaultController extends Controller{

    @FXML
    private Button btExit, btUser, btSetting, btShopping;
    private LoginController loginControl;
    protected static HistoryController historyControl;
    private ShoppingCartController shoppingControl;
    private SellerController sellerControl;
    private CashierController cashierControl;
    private OwnerController ownerControl;
    private URL loginUrl, historyUrl, itemUrl, shoppingUrl, sellerUrl, cashierUrl, ownerUrl;
    private FXMLLoader loginLoader, historyLoader, shoppingLoader, sellerLoader, cashierLoader, ownerLoader;
    private Scene loginScene, shoppingScene, sellerScene, cashierScene, ownerScene;
    private Parent loginParent, shoppingParent, sellerParent, cashierParent, ownerParent;
    private Stage stage;
    private List<Node> nodes;

    @FXML
    private VBox items;

    @FXML
    private AnchorPane basePane, drink, candy, chip, chocolate, current;

    @FXML
    private Label name, type;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @Override
    public void initialize(URL url, ResourceBundle resources){
        try{
            setStage();
            setFXMLLoader();

            //loading history context into sidePane
            VBox box = this.historyLoader.load();
            historyControl.updateHistory(name);
            drawer.open();
            drawer.setSidePane(box);

            //set up bubble effect for each category pane
            setRippler();

            //set the ActionEvent for icon hamburger
            setHamburgerEvent();

          //adding node into scroll pane VBox
            addItems("Drinks");

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public DefaultController(){

    }

    public void setFXMLLoader() throws IOException, SQLException {
        this.loginUrl = new File("src/main/resources/login.fxml").toURL();
        this.historyUrl = new File("src/main/resources/history.fxml").toURL();
        this.itemUrl = new File("src/main/resources/tableItem.fxml").toURL();
        this.shoppingUrl = new File("src/main/resources/shoppingCart.fxml").toURL();
        this.sellerUrl = new File("src/main/resources/Seller.fxml").toURL();
        this.cashierUrl = new File("src/main/resources/Cashier.fxml").toURL();
        this.ownerUrl = new File("src/main/resources/Owner.fxml").toURL();

        this.historyLoader = new FXMLLoader();
        this.loginLoader = new FXMLLoader();
        this.shoppingLoader = new FXMLLoader();
        this.sellerLoader = new FXMLLoader();
        this.cashierLoader = new FXMLLoader();
        this.ownerLoader = new FXMLLoader();


        this.loginControl = new LoginController(stage, name, type, this);
        this.historyControl = new HistoryController(stage);
        this.shoppingControl = new ShoppingCartController(this);
        this.sellerControl = new SellerController(stage);
        this.cashierControl = new CashierController(stage);
        this.ownerControl = new OwnerController(stage);

        this.loginLoader.setLocation(this.loginUrl);
        this.loginLoader.setController(this.loginControl);
        this.historyLoader.setLocation((this.historyUrl));
        this.historyLoader.setController(this.historyControl);
        this.shoppingLoader.setLocation(this.shoppingUrl);
        this.shoppingLoader.setController(this.shoppingControl);
        this.sellerLoader.setLocation(this.sellerUrl);
        this.sellerLoader.setController(this.sellerControl);
        this.cashierLoader.setLocation(this.cashierUrl);
        this.cashierLoader.setController(this.cashierControl);
        this.ownerLoader.setLocation(this.ownerUrl);
        this.ownerLoader.setController(this.ownerControl);

        this.loginControl.addHistory(this.historyControl,this);
        this.loginParent = this.loginLoader.load();

        this.loginScene = new Scene(loginParent);

        this.shoppingParent = this.shoppingLoader.load();
        this.shoppingScene = new Scene(shoppingParent);

        this.sellerParent = this.sellerLoader.load();
        this.sellerScene = new Scene(sellerParent);

        this.cashierParent = this.cashierLoader.load();
        this.cashierScene = new Scene(cashierParent);

        this.ownerParent = this.ownerLoader.load();
        this.ownerScene = new Scene(ownerParent);

    }

    @FXML
    public void handleMouseEvent(ActionEvent event) throws IOException, SQLException {

        if(event.getSource() == this.btExit){
            System.exit(0);
        }

        if(event.getSource() == this.btUser){
            stage.setScene(this.loginScene);
            stage.show();
        }

        if(event.getSource() == this.btShopping &&
                UserData.getUserRole(name.getText()).toLowerCase().equals("customer")){
            stage.setScene(this.shoppingScene);
            stage.show();
        }

        if(event.getSource() == this.btSetting){
            if( UserData.getUserRole(name.getText()).toLowerCase().equals("seller") ) {
                stage.setScene(this.sellerScene);
                stage.show();
            } else if( UserData.getUserRole(name.getText()).toLowerCase().equals("cashier") ) {
                stage.setScene(this.cashierScene);
                stage.show();
            } else if( UserData.getUserRole(name.getText()).toLowerCase().equals("owner") ) {
                stage.setScene(this.ownerScene);
                stage.show();
            }
        }

    }

    public void setStage(){
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
    }

    public void setRippler(){
        double xPos = 90;
        double yPos = 150;
        for(int i = 0; i < 4; i++){
            String color = "";
            switch (i) {
                case 0:
                    current = drink;
                    color = "#2b57f3";
                    break;
                case 1:
                    current = chocolate;
                    color = "#5620f0";
                    break;
                case 2:
                    current = candy;
                    color = "#e03b48";
                    break;
                case 3:
                    current = chip;
                    color = "#2ea25b";
                    break;
            }

            JFXRippler rippler = new JFXRippler(current);
            rippler.setRipplerFill(Color.web(color));
            rippler.setMaskType(JFXRippler.RipplerMask.RECT);
            rippler.setLayoutX(xPos);
            rippler.setLayoutY(yPos);
            basePane.getChildren().add(rippler);

            xPos += 150;
        }
    }

    public void setHamburgerEvent(){
        HamburgerNextArrowBasicTransition task2 = new HamburgerNextArrowBasicTransition(this.hamburger);
        task2.setRate(-1);

        this.hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            task2.setRate(task2.getRate() * -1);
            task2.play();

            if(this.drawer.isOpened()){
                this.drawer.close();
            }else{
                this.drawer.open();
            }
        });
    }

    //when the mouse click the drink pane
    public void dropDrink() throws IOException, SQLException{
        this.items.getChildren().clear();
        addItems("Drinks");
    }

    //when the mouse click the chocolate pane
    public void dropChocolate() throws IOException, SQLException{
        this.items.getChildren().clear();
        addItems("Chocolates");
    }

    //when the mouse click candy pane
    public void dropCandy() throws IOException, SQLException{
        this.items.getChildren().clear();
        addItems("Candies");
    }

    //when the mouse click chip pane
    public void dropChip() throws IOException, SQLException{
        this.items.getChildren().clear();
        addItems("Chips");
    }

    public void addItems(String type) throws IOException, SQLException{
        nodes = new ArrayList<>();

        HashMap<String, String> codes = ItemData.getItemCode(type);
        HashMap<String, String> prices = ItemData.getItemPrices(type);
        HashMap<String, String> quantities = ItemData.getItemQuantity(type);

        for(String name:codes.keySet()){
            FXMLLoader itemL = new FXMLLoader();
            itemL.setLocation(itemUrl);
            ItemController itemC = new ItemController(shoppingControl);
            itemL.setController(itemC);
            nodes.add(itemL.load());
            itemC.setText(type, codes.get(name), name, prices.get(name));
            itemC.setStatus(Integer.parseInt(quantities.get(name)));
            items.getChildren().add(nodes.get(nodes.size() - 1));
        }
    }

    @FXML
    public void logout() throws SQLException,IOException {
        if(name.getText().equals("Anonymous")){
            return;
        }else {
            name.setText("Anonymous");
            historyControl.updateHistory(name);
            shoppingControl.refresh();
            type.setText("C");
        }

    }

    public void historyUpdate() throws SQLException{
        historyControl.updateHistory(name);
    }

    public ShoppingCartController getShoppingControl(){
        return this.shoppingControl;
    }

    public Stage getStage(){
        return this.stage;
    }

    public String getName(){
        return this.name.getText();
    }

    public void updateShoppingCartUserName(){
        this.shoppingControl.setUsername(this.name.getText());
    }

}
