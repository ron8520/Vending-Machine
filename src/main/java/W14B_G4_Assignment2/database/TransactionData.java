package W14B_G4_Assignment2.database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import java.util.*;

public class TransactionData{
    public static boolean makeTransaction(String sql, String[] param) throws SQLException{
        DBConnection.update(sql, param);
        return true;
    }

    public static int getTransactionNum() throws SQLException{
        String sql = "select COUNT(*) from transactions";
        ResultSet result = DBConnection.fetch(sql);

        if (result == null){
            return 0;
        }

        if (result.next()){
            int count = result.getInt("count");
            return count;
        }

        return 0;
    }

    public static boolean generateCancelledTrans() throws SQLException, IOException {
        String sql = "select username, date_time, status from transactions where status = 'Cancelled'";
        ResultSet result = DBConnection.fetch(sql);

        if (result == null){
            return false;
        }

        List<List<String>> cancelReports = new ArrayList<>();
        while (result.next()){
            List<String> eachReport = new ArrayList<>();
            String nameTemp = result.getString("username");
            String timeTemp = String.valueOf(result.getTimestamp("date_time"));
            String status = result.getString("status");
            eachReport.add(nameTemp);
            eachReport.add(timeTemp);
            eachReport.add(status);
            cancelReports.add(eachReport);
        }

        FileWriter writer = new FileWriter("OwnerReport1.txt");
        writer.write("username\tdate_time\tstatus\n");
        for(List<String> eachList : cancelReports) {
            for(String i : eachList) {
                writer.write(i + "\t");
            }
            writer.write("\n");
        }
        writer.close();
        return true;
    }

    public static boolean generateAllTrans() throws SQLException, IOException {
        String sql = "select * from transactions";
        ResultSet result = DBConnection.fetch(sql);

        if (result == null){
            return false;
        }

        HashMap<String, String[]> transList = new HashMap<>();
        while (result.next()){
            String idTemp = result.getString("transaction_id"); // store as String
            String methodTemp = result.getString("method");
            String paidTemp = result.getString("paid_amount");
            String returnTemp = result.getString("return_change");
            String timeTemp = String.valueOf(result.getTimestamp("date_time"));
            String nameTemp = result.getString("username");
            String status = result.getString("status");
            String[] arr = {idTemp, methodTemp, paidTemp, returnTemp, timeTemp, status};
            transList.put(nameTemp, arr);
        }


        FileWriter writer = new FileWriter("CashierReport2.txt");
        writer.write("transaction_id\tmethod\tpaid_amount\treturn_change\tdate_time\tusername\tstatus\n");
        for(String i : transList.keySet()) {
            writer.write(transList.get(i)[0] + "\t");
            writer.write(transList.get(i)[1] + "\t");
            writer.write(transList.get(i)[2] + "\t");
            writer.write(transList.get(i)[3] + "\t");
            writer.write(transList.get(i)[4] + "\t");
            writer.write(i + "\t");
            writer.write(transList.get(i)[5] + "\n");
        }
        writer.close();
        return true;
    }

    public static LinkedHashMap<String, String> getRecentPurchase(String username) throws SQLException {
        String sql = "select username, item_name, category from " +
                "transaction_sold_items tsi natural join transactions join items i on (tsi.item_name = i.name ) " +
                "where username = ? and status = 'Completed' order by transaction_id desc limit 5";
        String[] param = {username};
        ResultSet result = DBConnection.fetch(sql, param);

        if (result == null){
            return null;
        }

        LinkedHashMap<String, String> recents = new LinkedHashMap<>();
        while (result.next()){
            String itemTemp = result.getString("item_name");
            String categoryTemp = result.getString("category");
            recents.put(itemTemp, categoryTemp);
        }
        return recents;
    }

}
