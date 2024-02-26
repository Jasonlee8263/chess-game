package serviceTests;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ClearService;

public class clearTest {
    private static ClearService clearService;
    private MemoryUserDAO memoryUserDAO;
    private MemoryAuthDAO memoryAuthDAO;
    private MemoryGameDAO memoryGameDAO;
    @BeforeAll
    public static void createClearService() {
        clearService = new ClearService();

    }


//    @BeforeEach
//    public void setup(){
//
//    }
//    @Test
//    public void

}
