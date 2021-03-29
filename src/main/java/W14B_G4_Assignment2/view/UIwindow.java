package W14B_G4_Assignment2.view;

import W14B_G4_Assignment2.database.DBConnection;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

public class UIwindow {
    private Stage primaryStage;
    private double x,y;
    private URL defaultUrl;
    private Scene defaultScene;
    private FXMLLoader defaultLoader;
    private DefaultController defaultControl;

    public UIwindow (Stage primaryStage) throws IOException{
        DBConnection.connect();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Vending Machine");
        this.primaryStage.initStyle(StageStyle.UNDECORATED);
        setFXMLLoader();
        Parent root = this.defaultLoader.load();
        setScene(root);
        this.primaryStage.setScene(this.defaultScene);
        this.primaryStage.show();

    }

    public void setScene(Parent root) throws IOException{
        this.defaultScene = new Scene(root);
    }


    public void setFXMLLoader() throws IOException{
        this.defaultUrl = new File("src/main/resources/default.fxml").toURL();
        this.defaultLoader = new FXMLLoader();
        this.defaultControl = new DefaultController();
        this.defaultLoader.setLocation(this.defaultUrl);
        this.defaultLoader.setController(this.defaultControl);
    }

}
