package W14B_G4_Assignment2.database;

import java.sql.*;

public class DBConnection{
    private static Connection con = null;
    private static Boolean isConnect = false;

    public static void connect(){
        try{
            // Create the connection to the Google Cloud SQL
            con = DriverManager.getConnection("jdbc:postgresql://54.249.17.222:5432/assignment","assignment","john");
            isConnect = true;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // An example of SQL query by using the connection
    public static ResultSet fetch(String query, String[] params){
        if (!isConnect){
            return null;
        }
        try{
            PreparedStatement stat= con.prepareStatement(query);
            for(int i = 0; i < params.length; i++){
                stat.setString(i+1,params[i]);
            }
            ResultSet rs= stat.executeQuery();
            return rs;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void update(String query, String[] params){
        if (!isConnect){
            return;
        }
        try{
            PreparedStatement stat= con.prepareStatement(query);
            for(int i = 0; i < params.length; i++){
                stat.setString(i+1,params[i]);
            }
            stat.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void update(String query){
        if (!isConnect){
            return;
        }
        try{
            PreparedStatement stat= con.prepareStatement(query);
            stat.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static ResultSet fetch(String query){
        try{
            PreparedStatement stat= con.prepareStatement(query);
            ResultSet rs= stat.executeQuery();
            return rs;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean execute(String query, String[] params){
        if (!isConnect){
            return false;
        }
        try{
            PreparedStatement stat= con.prepareStatement(query);
            for(int i = 0; i < params.length; i++){
                stat.setString(i+1,params[i]);
            }
            stat.executeQuery();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static Connection getConnection(){
        return con;
    }

    public static Boolean getIsConnect(){ return  isConnect; }

}
