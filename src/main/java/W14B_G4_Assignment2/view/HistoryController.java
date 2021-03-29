package W14B_G4_Assignment2.view;

import W14B_G4_Assignment2.database.TransactionData;
import W14B_G4_Assignment2.database.UserData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class HistoryController extends Controller{

    private Stage historyStage;

    @FXML
    private VBox vbox;

    @FXML
    private Label first, second, third, fourth, fifth;

    @FXML
    private ImageView firstImg, secondImg, thirdImg, fourthImg, fifthImg;

    @FXML
    private Image drink, chocolate, chip, candy;

    public HistoryController(Stage stage) throws IOException {
        loadingImage();
        this.historyStage = stage;
    }

    public VBox getVbox(){
        return this.vbox;
    }

    public void loadingImage() throws IOException{
        drink = new Image(new FileInputStream("src/main/resources/noun_Drink_3583231.png"));
        chocolate = new Image(new FileInputStream("src/main/resources/noun_Chocolate_1564420.png"));
        chip = new Image(new FileInputStream("src/main/resources/noun_snack_1134821.png"));
        candy = new Image(new FileInputStream("src/main/resources/noun_candy_3582024.png"));
    }

    //TODO
    public void updateHistory(Label username) throws SQLException {
        String name = username.getText();
        LinkedHashMap<String, String> recents = TransactionData.getRecentPurchase(name);
        ImageView[] imgArr = {firstImg, secondImg, thirdImg, fourthImg, fifthImg};
        Label[] labelArr = {first, second, third, fourth, fifth};

        // resetting
        for(ImageView iv: imgArr) {
            iv.setImage(null);
        }
        for(Label l : labelArr) {
            l.setText("");
        }

        int i = 0;
        for(String item: recents.keySet()) {
            String nameTemp = item;
            String catTemp = recents.get(item);
            switch (catTemp){
                case "Drinks":
                    imgArr[i].setImage(drink);
                    break;
                case "Chocolates":
                    imgArr[i].setImage(chocolate);
                    break;
                case "Candies":
                    imgArr[i].setImage(candy);
                    break;
                case "Chips":
                    imgArr[i].setImage(chip);
                    break;
            }
            labelArr[i].setText(nameTemp);
            i++;
        }

    }

}
