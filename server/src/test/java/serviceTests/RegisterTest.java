package serviceTests;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.LogInService;
import service.RegisterRequest;
import service.RegisterService;

public class RegisterTest {
    private static RegisterService registerService;
    static MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    static MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    @BeforeAll
    public static void createRegisterService() {
        registerService = new RegisterService(memoryAuthDAO,memoryUserDAO);
    }
    @Test
    public void testRegister(){
        int expected = 1;
        registerService.register(new RegisterRequest("test1","1234","test1@gmail.com"));
        Assertions.assertEquals(expected,memoryUserDAO.getUserData().size());
        memoryUserDAO.createUser(new UserData("test_dup","1234","test@gmail.com"));
//        Assertions.assertThrows()
    }
}