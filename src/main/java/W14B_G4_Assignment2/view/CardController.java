package W14B_G4_Assignment2.view;

import java.util.*;
import javafx.application.Platform;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.SQLException;
import java.io.IOException;

import W14B_G4_Assignment2.model.*;
import W14B_G4_Assignment2.database.*;

public class CardController extends Controller{

    private Stage cardStage;
    private Timer timer;
    private TimerTask task;
    private String username;
    private MachineEngine model;
    private ShoppingCartController cart;
    private double total;

    @FXML
    private Label timeSecond, timeMin, lbMessage;

    @FXML
    private Button btCancel, btConfirm;

    @FXML
    private JFXCheckBox saveCheck;

    @FXML
    private JFXTextField cardName;

    @FXML
    private JFXPasswordField cardNumber;

    private String error = "Incorrect Card Number / Name";

    public CardController(ShoppingCartController cart){
        this.cardStage = cart.getPaymentStage();
        this.username = cart.getUsername();
        this.cart = cart;
        this.model = cart.getEngine();
    }

    //TODO check the User input is valid or not
    public boolean identifyCardDetail() throws SQLException{
        String cardname = this.cardName.getText();
        String cardnum = this.cardNumber.getText();

        return model.verifyCard(cardname, cardnum);
    }

    @FXML
    public void handleMouseEvent(ActionEvent event) throws IOException, SQLException{
        //TODO
        if(event.getSource() == btConfirm){
            if(identifyCardDetail()){
                model.payByCreditCard(model.getUser().getName(), this.cardNumber.getText(), total);

                if (saveCheck.isSelected()){
                    model.addCard(model.getUser().getName(), this.cardName.getText());
                }

                cart.getDefault().logout();
                cart.getDefault().historyUpdate();
                cart.getDefault().addItems("Drinks");
                cardStage.close();
                timer.cancel();
                timer.purge();
                cart.refresh();
            }else{
                lbMessage.setText(error);
                lbMessage.setTextFill(Color.web("#eb853e"));
            }
        }

        //TODO Back to default page and log out
        if(event.getSource() == btCancel){
            model.cancelTransaction(total, 0, "Card");
            cart.getDefault().logout();
            cart.getDefault().historyUpdate();
            this.cardStage.close();
            timer.cancel();
            timer.purge();
            cart.refresh();
        }
    }

    public void restart() throws SQLException{
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
                            model.cancelTransaction(total, 0, "Card");
                            cardStage.close();
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
        cardName.setText("");
        cardNumber.setText("");
        lbMessage.setText("");
        ArrayList<String> cardInfo = CardData.getUserSavedDetail(this.model.getUser().getName());
        if (cardInfo != null){
            cardName.setText(cardInfo.get(0));
            cardNumber.setText(cardInfo.get(1));
        }

        timer.scheduleAtFixedRate(task, new Date(), 1000);
    }

    public void updateTotal(double total){
        this.total = total;
    }
}
