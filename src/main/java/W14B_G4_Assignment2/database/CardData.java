package W14B_G4_Assignment2.database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import java.util.*;

public class CardData{
    public static ArrayList<String> getUserSavedDetail(String username) throws SQLException{
        String sql = "select * from user_cards join cards on (user_cards.cardname=cards.name) where username=?";
        String[] param = {username};

        ResultSet rs = DBConnection.fetch(sql, param);
        ArrayList<String> cardInfo = new ArrayList<>();
        while (rs.next()){
            String nameTemp = rs.getString("cardname");
            String numberTemp = rs.getString("number");

            cardInfo.add(nameTemp);
            cardInfo.add(numberTemp);
            break;
        }
        if (cardInfo.size() == 0) return null;
        return cardInfo;
    }
}
