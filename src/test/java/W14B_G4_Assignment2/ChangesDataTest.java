package W14B_G4_Assignment2;
import java.util.*;
import java.sql.SQLException;
import java.io.IOException;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import W14B_G4_Assignment2.database.*;

public class ChangesDataTest{

    @BeforeEach
    public void setup(){
        DBConnection.connect();
    }

    @Test
    public void addChangesTest() throws SQLException{
        ArrayList<String> cashes = new ArrayList<>();
        cashes.add("$1");
        assertTrue(ChangesData.addChanges(cashes));
    }

    @Test
    public void deductChangesTest() throws SQLException{
        LinkedHashMap<String, Integer> changesMap = new LinkedHashMap<>();
        changesMap.put("$1",1);
        assertTrue(ChangesData.deductChanges(changesMap));
    }

    @Test
    public void modifyChangesTest() throws SQLException{
        assertTrue(ChangesData.modifyChange("10c", "10"));
    }

    @Test
    public void modifyQuantityTooSmall() throws SQLException{
        assertFalse(ChangesData.modifyChange("10c", "1"));
    }

    @Test
    public void reportTest() throws SQLException, IOException{
        assertTrue(ChangesData.generateReport());
    }


    @Test
    public void countTest() throws SQLException{
        assertTrue(ChangesData.countAllChange() > 0);
    }
}
