package W14B_G4_Assignment2.view;

import W14B_G4_Assignment2.database.UserData;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;


public class LoginController extends Controller{
    private Stage logInStage;
    private Stage signInStage;
    private boolean isValid;
    private Scene signInScene;
    private SigninController signInControl;
    private Parent signInParent;
    private URL signInUrl, historyUrl;
    private FXMLLoader signInLoader, historyLoader;
    private HistoryController historyControl;
    private DefaultController defaultControl;


    @FXML
    private Label error, name, type;

    @FXML
    private JFXTextField userName;

    @FXML
    private JFXPasswordField passWord;

    @FXML
    private Button btSignin, btLogin, btClose;

    public LoginController(Stage stage, Label name, Label type, DefaultController defaultControl) throws IOException{
        setStage();
        this.logInStage = stage;
        setFXMLLoader();
        this.isValid = false;
        this.name = name;
        this.type = type;
        this.defaultControl = defaultControl;
    }

    public void setFXMLLoader() throws IOException{
        this.signInUrl = new File("src/main/resources/signin.fxml").toURL();
        this.historyUrl = new File("src/main/resources/history.fxml").toURL();
        this.signInLoader = new FXMLLoader();
        this.signInControl = new SigninController(this.signInStage);
        this.signInLoader.setLocation(this.signInUrl);
        this.signInLoader.setController(this.signInControl);
    }

    @FXML
    public void handleMouseEvent(ActionEvent event) throws IOException, SQLException {
        if(event.getSource() == this.btClose){
            this.userName.setText("");
            this.passWord.setText("");
            this.error.setText("");
            this.logInStage.close();
        }

        //open signIn window
        if(event.getSource() == this.btSignin){
            if (this.signInParent == null) {
                this.signInParent = this.signInLoader.load();
                this.signInScene = new Scene(signInParent);
            }
            this.signInStage.setScene(this.signInScene);
            this.signInStage.show();
        }

        //to check the input detail match SQL database or not
        if(event.getSource() == this.btLogin){
            String name = this.userName.getText();
            String password = this.passWord.getText();

            try{
                this.isValid = UserData.verifyUser(name, password);
            }catch(Exception e){
                e.printStackTrace();
            }

            if(this.isValid){
                this.name.setText(this.userName.getText());
                this.defaultControl.updateShoppingCartUserName();
                this.defaultControl.getShoppingControl().refresh();
                this.historyControl.updateHistory(this.name);

                if( !UserData.getUserRole(userName.getText()).toLowerCase().equals("customer") ) {
                    this.type.setText("S"); // standing for STAFF
                }
                this.userName.setText("");
                this.passWord.setText("");
                this.error.setText("");
                this.logInStage.close();

            }else{
                error.setText("Incorrect Username/Password!");
                error.setTextFill(Color.web("#e1414e"));
            }
        }
    }

    public void setStage(){
        this.signInStage = new Stage();
        this.signInStage.setTitle("SignIn");
        this.signInStage.initStyle(StageStyle.UNDECORATED);
    }

    public void addHistory(HistoryController historyControl, DefaultController defaultControl) {
        this.historyControl = historyControl;
        this.defaultControl = defaultControl;
    }

}
