package W14B_G4_Assignment2;

import java.sql.ResultSet;
import java.util.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import W14B_G4_Assignment2.model.*;
import W14B_G4_Assignment2.database.*;
import java.sql.SQLException;
import java.io.IOException;


public class TransactionDataTest{

    @Test
    public void generateAllTransTest() throws SQLException, IOException{
        assertTrue(TransactionData.generateAllTrans());
    }

    @Test
    public void getRecentPurchaseTest() throws SQLException, IOException{
        LinkedHashMap<String, String> recents = TransactionData.getRecentPurchase("first");
    }
}
