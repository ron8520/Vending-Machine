package W14B_G4_Assignment2.view;

import W14B_G4_Assignment2.database.TransactionData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import W14B_G4_Assignment2.database.ChangesData;
import java.sql.SQLException;


public class CashierController extends Controller{

    @FXML
    private Button btReport, btConfirm, btExit;

    @FXML
    private VBox vbCashList;

    @FXML
    private Label lbMessage;

    private String reportSuccessful = "Report Export Successful";
    private Stage cashierStage;
    private URL cashierItemUrl;
    private FXMLLoader cashierItemLoader;
    private CashierItemController cashierItemControl;
    private List<Node> nodes;
    private Node node;
    private ArrayList<CashierItemController> controllersList;

    @Override
    public void initialize(URL url, ResourceBundle resources){
        try{
            addCash();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public CashierController(Stage cashierStage) throws IOException{
        this.cashierStage = cashierStage;
        setURL();
    }

    public void setURL() throws IOException {
        cashierItemUrl = new File("src/main/resources/CashierItem.fxml").toURL();
    }

    @FXML
    public void handleMouseEvent(ActionEvent event) throws SQLException, IOException {
        //TODO EXPORT THE CASHIER REPORT
        if(event.getSource() == btReport){
            if(ChangesData.generateReport()) {
                TransactionData.generateAllTrans();
                lbMessage.setText(reportSuccessful);
                lbMessage.setTextFill(Color.web("#61d38b"));
            } else{
                lbMessage.setText("Report Export Failed");
                lbMessage.setTextFill(Color.web("#e35d68"));
            }

        }

        //TODO UPDATE CURRENT AMOUNT NOTE TO THE SQL
        if(event.getSource() == btConfirm){

            for(CashierItemController cii : controllersList) {
                String changeName = cii.getLbNote();
                String newQuantity = cii.getLbAmount();
                if( !ChangesData.modifyChange(changeName, newQuantity) ) {
                    return;
                }
            }

            lbMessage.setText("Add Change Successful");
            lbMessage.setTextFill(Color.web("#61d38b"));
            this.vbCashList.getChildren().clear();
            addCash();
        }

        if(event.getSource() == btExit){
            lbMessage.setText("");
            this.cashierStage.close();
        }
    }

    //TODO ADD ALL THE COINS TO THE VBOX

    public void addCash() throws IOException, SQLException{
        //TODO
        nodes = new ArrayList<>();
        this.controllersList = new ArrayList<>();

        LinkedHashMap<String, String> changes = ChangesData.getChanges();
        for(String changeName: changes.keySet()) {
            cashierItemLoader = new FXMLLoader();
            cashierItemControl = new CashierItemController(lbMessage);

            cashierItemLoader.setLocation(cashierItemUrl);
            cashierItemLoader.setController(cashierItemControl);

            node =  cashierItemLoader.load();
            cashierItemControl.setNode(node);
            cashierItemControl.setAllInfo( changeName, changes.get(changeName) );
            controllersList.add(cashierItemControl);

            nodes.add(node);
            vbCashList.getChildren().add(nodes.get(nodes.size() - 1));
        }
    }

    public Label getLbMsg() {
        return this.lbMessage;
    }
}
