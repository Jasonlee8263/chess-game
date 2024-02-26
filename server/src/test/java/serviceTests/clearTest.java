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
        memoryGameDAO.createGame(new GameData(1,"white","black","testGame",new ChessGame()));
    }

    @Test
    public void testClear() throws DataAccessException {
        clearService.clear();
        int expected = 0;
        Assertions.assertEquals(expected,memoryUserDAO.getUserData().size());
        Assertions.assertEquals(expected,memoryAuthDAO.getAuthData().size());
        Assertions.assertEquals(expected,memoryGameDAO.listGame().size());
    }

}
