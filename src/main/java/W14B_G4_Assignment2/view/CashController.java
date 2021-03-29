package W14B_G4_Assignment2.view;

import java.io.File;
import java.util.*;

import W14B_G4_Assignment2.database.ChangesData;
import javafx.application.Platform;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import java.util.*;
import java.sql.SQLException;
import java.io.IOException;

import W14B_G4_Assignment2.model.*;
import javafx.stage.StageStyle;

public class CashController extends Controller{
    private double total = 0.0;
    private double insert;
    private double change;
    private Stage cashStage;
    private Boolean enoughChange;
    private Timer timer;
    private TimerTask task;
    private ShoppingCartController cart;

    @FXML
    private JFXButton btTwentyCent;

    @FXML
    private JFXButton btFiveDollar;

    @FXML
    private Label lbTotalCost;

    @FXML
    private JFXButton btFiftyCent;

    @FXML
    private JFXButton btTenDollar;

    @FXML
    private JFXButton btCancel;

    @FXML
    private JFXButton btConfirm;

    @FXML
    private Label timeMin;

    @FXML
    private JFXButton btTwentyDollar;

    @FXML
    private Label lbClean;

    @FXML
    private Label lbMessage;

    @FXML
    private Label timeSecond;

    @FXML
    private JFXButton btOneDollar;

    @FXML
    private JFXButton btTwoDollar;

    @FXML
    private Label lbInsert;

    @FXML
    private Label lbChange;

    @FXML
    private JFXButton btTenCent;

    private LinkedHashMap<String,Integer> moneyInserted;

    private String username;
    private MachineEngine model;
    private URL changeUrl;
    private FXMLLoader changeFXML;
    private ChangeController changeControl;
    private Stage changeStage;

    public CashController(ShoppingCartController cart) throws IOException{
        this.cashStage = cart.getPaymentStage();
        this.insert = 0.00;
        this.change = 0.00;
        this.enoughChange = true;
        this.username = cart.getUsername();
        this.cart = cart;
        this.model = cart.getEngine();

        setFXMLLoader();
    }

    public void setFXMLLoader() throws IOException{
        changeUrl = new File("src/main/resources/change.fxml").toURL();

        changeStage = new Stage();
        changeStage.initStyle(StageStyle.UNDECORATED);

        changeFXML = new FXMLLoader();
        changeControl = new ChangeController(changeStage);

        changeFXML.setLocation(changeUrl);
        changeFXML.setController(changeControl);

        changeStage.setScene(new Scene(changeFXML.load()));
    }

    @FXML
    public void handleMouseEvent(ActionEvent event)  throws IOException, SQLException{
        lbMessage.setText("");
        if(event.getSource() == btTenCent){
            insert += 0.10;
            insertMoney("10c");
            model.addCash("10c");
        }else if (event.getSource() == btTwentyCent){
            insert += 0.20;
            insertMoney("20c");
            model.addCash("20c");
        }else if (event.getSource() == btFiftyCent){
            insert += 0.50;
            insertMoney("50c");
            model.addCash("50c");
        }else if (event.getSource() == btOneDollar){
            insert += 1.00;
            insertMoney("$1");
            model.addCash("$1");
        }else if (event.getSource() == btTwoDollar){
            insert += 2.00;
            insertMoney("$2");
            model.addCash("$2");
        }else if (event.getSource() == btFiveDollar){
            insert += 5.00;
            insertMoney("$5");
            model.addCash("$5");
        }else if (event.getSource() == btTenDollar){
            insert += 10.00;
            insertMoney("$10");
            model.addCash("$10");
        }else if (event.getSource() == btTwentyDollar){
            insert += 20.00;
            insertMoney("$20");
            model.addCash("$20");
        }

        //TODO Payment cancel, record detail to SQL and back to default page
        if(event.getSource() == btCancel){
            model.cancelTransaction(insert, 0, "Cash");
            cashStage.close();
            cart.getDefault().logout();
            cart.getDefault().historyUpdate();
            timer.cancel();
            timer.purge();
            cart.refresh();
        }


        if(event.getSource() == btConfirm){
            model = cart.getEngine();
            //TODO check insert amount is greater than total or not
            //TODO Need to check have enough coin or note to change as well
            //TODO if not enough, ask to re-insert or cancel payment
            if(insert < total){
                lbMessage.setText("Inserted money not enough!");
                lbMessage.setTextFill(Color.web("#e1414e"));
                return;
            }else{
                //TODO Payment successful update to SQL back to default page
                checkAvailableChange();

                if (this.enoughChange){
                    if (!setChange()){
                        lbMessage.setText("Unable to Change, Try again");
                        lbMessage.setTextFill(Color.web("#eb853e"));
                    }else{
                        model.payByCash(insert, insert - total);
                        cart.getDefault().logout();
                        cart.getDefault().historyUpdate();
                        cart.getDefault().addItems("Drinks");
                        timer.cancel();
                        timer.purge();
                        cashStage.close();
                        cart.refresh();
                        changeStage.show();
                    }
                }
                else{
                    lbMessage.setText("Unable to Change, Try again");
                    lbMessage.setTextFill(Color.web("#eb853e"));
                }
            }
        }

        change = insert - total;
        lbChange.setText(String.format("%.2f", change));
        lbInsert.setText(String.format("%.2f", insert));
    }

    //if enough, enoughChange set to True
    //otherwise set to False
    public void checkAvailableChange() throws SQLException{
        if (ChangesData.countAllChange() >= total){
            this.enoughChange = true;
        }else{
            this.enoughChange = false;
        }
    }

    public void cleanUpInsert(){
        change = 0.00;
        insert = 0.00;
        lbInsert.setText("0.00");
        lbChange.setText("0.00");
        this.moneyInserted = ChangesData.createHashMap();
    }

    public boolean setChange() throws SQLException{
        LinkedHashMap<String, String> available = ChangesData.getChanges();
        LinkedHashMap<String, Integer> changesMap = model.getChange(this.change, available);
        if (changesMap == null){
            return false;
        }
        for (String key:changesMap.keySet()){
            int amount = changesMap.get(key);
            switch (key){
                case "10c":
                    this.changeControl.setTenCentText(amount);
                    break;
                case "20c":
                    this.changeControl.setTwentyCentText(amount);
                    break;
                case "50c":
                    this.changeControl.setFiftyCentText(amount);
                    break;
                case "$1":
                    this.changeControl.setOneDollarText(amount);
                    break;
                case "$2":
                    this.changeControl.setTwoDollarText(amount);
                    break;
                case "$5":
                    this.changeControl.setFiveDollarText(amount);
                    break;
                case "$10":
                    this.changeControl.setTenDollarText(amount);
                    break;
                case "$20":
                    this.changeControl.setTwentyDollarText(amount);
                    break;
            }
        }
        this.changeControl.setText(this.change);
        ChangesData.deductChanges(changesMap);
        return true;
    }

    public void restart(){
        timer = new Timer();
        task = new TimerTask(){
            private int deadline = 120;
            @Override
            public void run(){
                 Platform.runLater(() -> {
                    timeSecond.setText(String.valueOf(deadline % 60));
                    timeMin.setText(String.valueOf(deadline / 60)+": ");
                    deadline--;
                    if (deadline < 0){
                        //close the page
                        try{
                            model.cancelTransaction(insert, 0, "Cash");
                            cashStage.close();
                            cart.getDefault().logout();
                            cart.getDefault().historyUpdate();
                            timer.cancel();
                            timer.purge();
                            cart.refresh();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        model = cart.getEngine();
        this.moneyInserted = ChangesData.createHashMap();
        lbChange.setText(String.format("%.2f", 0.0));
        lbInsert.setText(String.format("%.2f", 0.0));
        insert = 0.0;
        timer.scheduleAtFixedRate(task, new Date(), 1000);
    }

    public void insertMoney(String money){
        this.moneyInserted.put(money,this.moneyInserted.get(money) + 1);
        for (Map.Entry<String, Integer> entry : this.moneyInserted.entrySet()) {
            String key = entry.getKey();
            Integer amount = entry.getValue();
        }
    }

    public void updateTotal(double total){
        this.total = total;
        lbTotalCost.setText(String.valueOf(total));
    }


}
