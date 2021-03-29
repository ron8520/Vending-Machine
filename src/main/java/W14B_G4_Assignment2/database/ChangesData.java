package W14B_G4_Assignment2.database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import java.util.*;

public class ChangesData{
    public static boolean addChanges(ArrayList<String> cashes) throws SQLException{
        for (int i = 0;i < cashes.size();i++){
            String sql = "update changes set quantity = quantity + 1 where name=?";
            String[] param = {cashes.get(i)};

            DBConnection.update(sql, param);
        }
        return true;
    }

    public static boolean deductChanges(LinkedHashMap<String, Integer> changesMap) throws SQLException{
        for (String key:changesMap.keySet()){
            int amount = changesMap.get(key);
            String sql = "update changes set quantity = quantity -" + amount + "where name=?";
            String[] param = {key};

            DBConnection.update(sql, param);
        }
        return true;
    }

    public static double countAllChange() throws SQLException{
        String sql = "select * from changes";
        ResultSet rs = DBConnection.fetch(sql);

        double result = 0.0;
        while (rs.next()){
            String nameTemp = rs.getString("name");
            int qTemp = rs.getInt("quantity");

            if (nameTemp.charAt(0) == '$'){
                result += Double.parseDouble(nameTemp.substring(1)) * qTemp;
            }else{
                result += Double.parseDouble(nameTemp.substring(0,2)) * qTemp / 100;
            }
        }
        return result;

    }

    public static LinkedHashMap<String,Integer> createHashMap(){
        LinkedHashMap<String,Integer> map = new LinkedHashMap<String,Integer>();
        map.put("$20",0);
        map.put("$10",0);
        map.put("$5",0);
        map.put("$2",0);
        map.put("$1",0);
        map.put("50c",0);
        map.put("20c",0);
        map.put("10c",0);
        return map;
    }

    public static LinkedHashMap getChanges()  throws SQLException{
        LinkedHashMap<String,String> map = new LinkedHashMap<>();

        String sql = "select name, quantity from changes order by value desc";
        ResultSet rs = DBConnection.fetch(sql);

        while (rs.next()){
            String nameTemp = rs.getString("name");
            String qTemp = String.valueOf(rs.getInt("quantity"));
            map.put(nameTemp, qTemp);
        }
        return map;
    }

    public static double getValue(String cash){
        switch(cash) {
            case "$20": return 20.0;
            case "$10": return 10.0;
            case "$5": return 5.0;
            case "$2": return 2.0;
            case "$1": return 1.0;
            case "50c": return 0.5;
            case "20c": return 0.2;
            case "10c": return 0.1;
            default: return 0.0;
        }
    }

    public static boolean modifyChange(String name, String quantity) throws SQLException {
        if(Integer.valueOf(quantity) < 5) {
            return false;
        }
        String sql = "update changes set quantity = " + Integer.valueOf(quantity) + "where name = ?";
        String[] param = {name};
        DBConnection.update(sql,param);
        return true;
    }

    public static boolean generateReport() throws SQLException, IOException {
        String sql = "select * from changes";
        ResultSet result = DBConnection.fetch(sql);

        if (result == null){
            return false;
        }

        HashMap<String, String> changelList = new HashMap<>();
        while (result.next()){
            String nameTemp = result.getString("name");
            String qTemp = String.valueOf(result.getInt("quantity"));
            changelList.put(nameTemp, qTemp);
        }

        FileWriter writer = new FileWriter("CashierReport1.txt");
        writer.write("name\tquantity\n");
        for(String i : changelList.keySet()) {
            writer.write(i + "\t");
            writer.write(changelList.get(i) + "\n");
        }
        writer.close();
        return true;
    }
}
