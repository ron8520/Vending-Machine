package W14B_G4_Assignment2.database;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.HashMap;

public class UserData {

    public static Boolean verifyUser(String username, String password) throws SQLException {
        String sql = "select COUNT(*) from users where username = ? and password = ?";
        String[] param = {username, password};
        ResultSet result =  DBConnection.fetch(sql,param);
        if(result == null){
            return false;
        }
        if (result.next()){
            int count = result.getInt("count");

            if(count== 1){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /*public static boolean ifExist(String username, String password) throws SQLException{
        String ifExist = "select COUNT(*) from users where username = ?;";
        String[] ifExistParam = {username};
        ResultSet result =  DBConnection.fetch(ifExist,ifExistParam);
        if(result == null){
            return false;
        }
        if (result.next()){
            int count = result.getInt("count");
            if(count!= 0){
                return false;
            }
        }
        return true;
    }*/

    public static boolean insertUser(String username, String password) throws SQLException{
        String sql = "insert into Users values (?,?, 'customer');";
        String[] param = {username, password};
        DBConnection.execute(sql,param);
        return true;
    }

    public static String getUserRole(String username) throws SQLException{
        String sql = "select role from users where username = ?";
        String[] param = {username};
        ResultSet result = DBConnection.fetch(sql,param);
        if (result == null){
            return null;
        }
        if (result.next()){
            String ret = result.getString("role");
            return ret;
        }
        return null;
    }


    public static HashMap<String, String> getUser() throws SQLException{
        String sql = "select username, role from users";
        ResultSet result = DBConnection.fetch(sql);

        if (result == null){
            return null;
        }

        HashMap<String, String> users = new HashMap<>();
        while (result.next()){
            String name = result.getString("username");
            String role = result.getString("role");

            if(!name.equals("Anonymous"))
                users.put(name, role);
        }

        return users;
    }

    public static void updateRole(String username, String role) throws SQLException{
        String sql = "update users set role = ? where username = ? ";
        String[] param = {role, username};

        DBConnection.update(sql,param);

    }

    public static boolean generateUserReport() throws SQLException, IOException {
        String sql = "select username, role from users";
        ResultSet result = DBConnection.fetch(sql);

        if (result == null){
            return false;
        }

        HashMap<String, String> userList = new HashMap<>();
        while (result.next()){
            String nameTemp = result.getString("username");
            String roleTemp = result.getString("role");
            userList.put(nameTemp, roleTemp);
        }

        FileWriter writer = new FileWriter("OwnerReport2.txt");
        writer.write("name\trole\n");
        for(String i : userList.keySet()) {
            writer.write(i + "\t");
            writer.write(userList.get(i) + "\n");
        }
        writer.close();
        return true;
    }

    public static boolean verifyUsername(String username) throws SQLException {
        String sql = "select COUNT(*) from users where username = ? ";
        String[] param = {username};
        ResultSet result =  DBConnection.fetch(sql,param);
        if(result == null){
            return false;
        }
        return true;
    }

}
