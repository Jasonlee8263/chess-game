package serviceTests;

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
import model.requestAndResult.JoinGameRequest;
import service.GameService;

import java.sql.SQLException;

public class JoinGameTest {
    private static GameService joinGameService;
    private static MemoryAuthDAO authDAO = new MemoryAuthDAO();
    private static MemoryGameDAO gameDAO = new MemoryGameDAO();
    private static MemoryUserDAO userDAO = new MemoryUserDAO();
    @BeforeAll
    public static void setJoinGameService(){
        joinGameService = new GameService(authDAO,gameDAO);
    }
    @Test
    public void testJoinGame() throws DataAccessException, SQLException {
        UserData user = userDAO.createUser(new UserData("testUser","1234","test@gmail.com"));
        GameData game = gameDAO.createGame("test");
        AuthData auth = authDAO.createAuth(new AuthData("testUser","test1"));

        joinGameService.joinGame(new JoinGameRequest("WHITE",game.gameID()), auth.authToken());
        Assertions.assertEquals(new GameData(game.gameID(), "testUser", null, "test", null),gameDAO.getGame(game.gameID()));
    }
    @Test
    public void testJoinGameFail() throws DataAccessException, SQLException {
        UserData user = userDAO.createUser(new UserData("testUser","1234","test@gmail.com"));
        GameData game = gameDAO.createGame("test");
        AuthData auth = authDAO.createAuth(new AuthData("testUser","test1"));

//        joinGameService.joinGame(new JoinGameRequest("RED",game.gameID()), auth.authToken());

        Assertions.assertEquals("Error: bad request", joinGameService.joinGame(new JoinGameRequest("RED",game.gameID()), auth.authToken()));

    }
}
