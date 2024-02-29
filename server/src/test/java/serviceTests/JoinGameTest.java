package serviceTests;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.JoinGameService;

public class JoinGameTest {
    private static JoinGameService joinGameService;
    private static MemoryAuthDAO authDAO = new MemoryAuthDAO();
    private static MemoryGameDAO gameDAO = new MemoryGameDAO();
    private static MemoryUserDAO userDAO = new MemoryUserDAO();
    @BeforeAll
    public static void setJoinGameService(){
        joinGameService = new JoinGameService(authDAO,gameDAO);
    }
    @Test
    public void testJoinGame(){
        UserData user = userDAO.createUser(new UserData("testUser","1234","test@gmail.com"));
        GameData game = gameDAO.createGame("test");
        gameDAO.updateGame(game.gameID(), user.username(),"WHITE");
        Assertions.assertEquals(new GameData(game.gameID(), "testUser", null, "test", null),gameDAO.getGame(game.gameID()));
    }
}
