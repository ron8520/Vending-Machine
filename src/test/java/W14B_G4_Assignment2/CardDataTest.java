package W14B_G4_Assignment2;
import java.util.*;
import java.sql.SQLException;
import java.io.IOException;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import W14B_G4_Assignment2.database.*;

public class CardDataTest{
    @BeforeEach
    public void setup(){
        DBConnection.connect();
    }

    @Test
    public void testSavedDetail() throws SQLException{
        assertNotNull(CardData.getUserSavedDetail("Anonymous"));
        assertNull(CardData.getUserSavedDetail("Johns"));
    }
}
