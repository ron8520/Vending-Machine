package W14B_G4_Assignment2.view;

import W14B_G4_Assignment2.database.UserData;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;


public class SigninController extends Controller{
    private Stage signStage;
    private boolean isSamePassword = false;
    private boolean isUnique = false;

    @FXML
    private Label error_message;

    @FXML
    private JFXTextField userName;

    @FXML
    private JFXPasswordField passWord, confirmPassWord;

    @FXML
    private Button btClose, btSignUp;

    public SigninController(Stage stage){
        this.signStage = stage;
    }

    @FXML
    public void handleMouseEvent(ActionEvent event) throws IOException{
        if(event.getSource() == this.btClose){
            this.userName.setText("");
            this.passWord.setText("");
            this.confirmPassWord.setText("");
            this.error_message.setText("");
            this.signStage.close();
        }

        if(event.getSource() == this.btSignUp){

            if (this.passWord.getText().equals(this.confirmPassWord.getText())){
                this.isSamePassword = true;

            }else{
                error_message.setText("Password is the not same!");
                error_message.setTextFill(Color.web("#eb853e"));
            }

            String name = this.userName.getText();
            String password = this.passWord.getText();

            try{
                if (UserData.verifyUsername(name)){
                    this.isUnique = true;
                }else{
                    error_message.setText("Username already exists!");
                    error_message.setTextFill(Color.web("#eb853e"));
                }

                //sign up sucessful, close current window
                if(this.isUnique && this.isSamePassword){
                    this.userName.setText("");
                    this.passWord.setText("");
                    this.confirmPassWord.setText("");
                    this.error_message.setText("");
                    this.signStage.close();
                    UserData.insertUser(this.userName.getText(), this.passWord.getText());
                }else{

                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }



}
