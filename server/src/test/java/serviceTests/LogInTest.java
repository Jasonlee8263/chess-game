package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import requestAndResult.LogInRequest;
import requestAndResult.LogInResult;
import service.LogInService;

import java.sql.SQLException;

public class LogInTest {
    private static LogInService logInService;
    static MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    static MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();

    @BeforeAll
    public static void createLogInService() {
        logInService = new LogInService(memoryAuthDAO, memoryUserDAO);

        memoryUserDAO.createUser(new UserData("test1", "1234", "test@gmail.com"));

    }
    @Test
    public void testLogIn() throws DataAccessException, SQLException {
        LogInResult actual = logInService.login(new LogInRequest("test1","1234"));
        LogInResult expected  = new LogInResult("test1","1234","pass");
        Assertions.assertEquals(expected.username(),actual.username());
    }
    @Test
    public void testLogInFail() throws SQLException, DataAccessException {
        LogInResult result = logInService.login(new LogInRequest("newUser","1234"));
        Assertions.assertEquals("Error: unauthorized",result.message());
    }
}
