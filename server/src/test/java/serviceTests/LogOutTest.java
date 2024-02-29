package serviceTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.LogOutService;

public class LogOutTest {
    private static LogOutService logOutService;
    static MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    static MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    @BeforeAll
    public static void createLogOutService() {
        logOutService = new LogOutService(memoryAuthDAO);


    }

    @Test
    public void testLogOut() throws DataAccessException {
        memoryUserDAO.createUser(new UserData("test1","1234","test@gmail.com"));
        AuthData auth = memoryAuthDAO.createAuth(new AuthData("testAuthToken","test1"));
        Assertions.assertEquals(auth,memoryAuthDAO.getAuth(auth.authToken()));
        logOutService.delete(auth.authToken());
        Assertions.assertNull(memoryAuthDAO.getAuth(auth.authToken()));
    }
}
