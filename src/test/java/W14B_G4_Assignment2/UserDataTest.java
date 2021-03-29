package W14B_G4_Assignment2;

import java.util.*;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import W14B_G4_Assignment2.model.*;
import W14B_G4_Assignment2.database.*;

public class UserDataTest {

    @Test
    public void insertUserTest() {
        try {

            assertTrue(UserData.insertUser("Sylvia","123456"));  // prepare examples for verifyUserTest
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void verifyUserTest() throws SQLException{
        assertTrue(UserData.verifyUser("first","first"));
        assertFalse(UserData.verifyUser("first","error"));

    }

    @Test
    public void getUserRoleTest() throws SQLException{
        assertEquals("Customer",UserData.getUserRole("first"));
        assertEquals(null,UserData.getUserRole("qwerasdf"));
    }

    @Test
    public void getUserTest() throws SQLException{
        assertNotNull(UserData.getUser());
    }

    @Test
    public void updateRoleTest() throws SQLException{
        UserData.updateRole("first", "Customer");
    }

    @Test
    public void generateUserReportTest() throws SQLException, IOException{
        assertTrue(UserData.generateUserReport());
    }

    @Test
    public void verifyUsernameTest() throws SQLException, IOException{
        assertTrue(UserData.verifyUsername("first"));
    }
}
