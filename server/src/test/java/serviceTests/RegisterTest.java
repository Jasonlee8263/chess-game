package serviceTests;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import requestAndResult.RegisterRequest;
import requestAndResult.RegisterResult;
import service.RegisterService;

public class RegisterTest {
    private static RegisterService registerService;
    static MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    static MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    @BeforeAll
    public static void createRegisterService() {
        registerService = new RegisterService(memoryAuthDAO,memoryUserDAO);
        RegisterResult result = registerService.register(new RegisterRequest("test1","1234","test1@gmail.com"));

    }
    @Test
    public void testRegister(){
        Assertions.assertEquals("test1",memoryUserDAO.getUser("test1").username());
    }
    @Test
    public void testRegisterFail(){
        RegisterResult result = registerService.register(new RegisterRequest("test1","1234","test1@gmail.com"));
        Assertions.assertNull(result.username());
    }
}
