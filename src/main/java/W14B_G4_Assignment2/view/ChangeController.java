package W14B_G4_Assignment2.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ChangeController extends Controller{

    @FXML
    private Label lbChange;

    @FXML
    private Label lbTwentyDollar;

    @FXML
    private Label lbTwoDollar;

    @FXML
    private Label lbTwentyCent;

    @FXML
    private Label lbFiveDollar;

    @FXML
    private Label lbTenCent;

    @FXML
    private Label lbOneDollar;

    @FXML
    private Label lbTenDollar;

    @FXML
    private Label lbFiftyCent;

    @FXML
    private Button btExit;
    private Stage changeStage;


    public ChangeController(Stage changeStage){
        this.changeStage = changeStage;
    }

    @FXML
    public void handleMouseEvent(ActionEvent event){
        if(event.getSource() == btExit){
            this.changeStage.close();
        }
    }

    public void setTwentyDollarText(int num){
        lbTwentyDollar.setText(String.valueOf(num));
    }

    public void setTenDollarText(int num){
        lbTenDollar.setText(String.valueOf(num));
    }

    public void setFiveDollarText(int num){
        lbFiveDollar.setText(String.valueOf(num));
    }

    public void setTwoDollarText(int num){
        lbTwoDollar.setText(String.valueOf(num));
    }

    public void setOneDollarText(int num){
        lbOneDollar.setText(String.valueOf(num));
    }

    public void setFiftyCentText(int num){
        lbFiftyCent.setText(String.valueOf(num));
    }

    public void setTwentyCentText(int num){
        lbTwentyCent.setText(String.valueOf(num));
    }

    public void setTenCentText(int num){
        lbTenCent.setText(String.valueOf(num));
    }

    public void setText(double change){
        lbChange.setText(String.valueOf(change));
    }
}
