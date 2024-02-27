package serviceTests;

import chess.ChessGame;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeAll;
import service.LogOutService;

public class LogOutTest {
    private static LogOutService logOutService;
    static MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    static MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    @BeforeAll
    public static void createLogOutService() {
        logOutService = new LogOutService(memoryAuthDAO);

        memoryUserDAO.createUser(new UserData("test1","1234","test@gmail.com"));
        memoryAuthDAO.createAuth(new AuthData("testAuthToken","test1"));
    }

    public void testLogOut(){

    }
}
