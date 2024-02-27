package serviceTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ClearService;

import java.util.Collection;

public class clearTest {
    private static ClearService clearService;
    static MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    static MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    static MemoryGameDAO memoryGameDAO = new MemoryGameDAO();

    @BeforeAll
    public static void createClearService() {
        clearService = new ClearService(memoryAuthDAO,memoryGameDAO,memoryUserDAO);

        memoryUserDAO.createUser(new UserData("test1","1234","test@gmail.com"));
        memoryAuthDAO.createAuth(new AuthData("testAuthToken","test1"));
        memoryGameDAO.createGame("test1");
    }

    @Test
    public void testClear() throws DataAccessException {
        clearService.clear();
        Assertions.assertEquals(0,memoryUserDAO.getUserData().size());
        Assertions.assertEquals(0,memoryAuthDAO.getAuthData().size());
        Assertions.assertEquals(0,memoryGameDAO.listGame().size());
    }

}
