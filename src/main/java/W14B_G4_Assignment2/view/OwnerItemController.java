package W14B_G4_Assignment2.view;

import W14B_G4_Assignment2.database.UserData;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OwnerItemController extends Controller{

    @FXML
    private Button btUpdate;

    @FXML
    private Label lbName, lbMessage;

    @FXML
    private JFXTextField lbRole;
    private List<String> roles;
    private OwnerController ownerControl;

    public OwnerItemController(Label lbMessage, OwnerController ownerControl){
        this.ownerControl = ownerControl;
        this.lbMessage = lbMessage;
        roles = new ArrayList<>();

        roles.add("Customer");
        roles.add("Seller");
        roles.add("Cashier");
    }

    @FXML
    public void handleMouseEvent(ActionEvent event) throws SQLException, IOException {

        if(event.getSource() == btUpdate){
            String role = lbRole.getText();

            //TODO CHECK THE VALID INPUT IF VALUE UPDATE TO SQL
            if(!roles.contains(role)){
                lbMessage.setText("Invalid Role Name");
                lbMessage.setTextFill(Color.web("#e35d68"));
            }else if (role.equals(UserData.getUserRole(lbName.getText()))){
                lbMessage.setText("Role Already Set Up");
                lbMessage.setTextFill(Color.web("#e35d68"));
            }else{
                UserData.updateRole(lbName.getText(), role);
                lbMessage.setText("Role Update Successful");
                lbMessage.setTextFill(Color.web("#61d38b"));
                ownerControl.addName();
            }

        }
    }

    public void setText(String name, String role){
        lbName.setText(name);
        lbRole.setPromptText(role);
    }
}
