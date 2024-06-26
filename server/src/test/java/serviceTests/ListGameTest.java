package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import model.requestAndResult.ListGameResult;
import service.GameService;

import java.sql.SQLException;

public class ListGameTest {
    private static GameService listGameService;
    private static MemoryAuthDAO authDAO = new MemoryAuthDAO();
    private static MemoryGameDAO gameDAO = new MemoryGameDAO();

    @BeforeAll
    public static void setListGameService() {
        listGameService = new GameService(authDAO,gameDAO);
    }
    @Test
    public void testListGame() throws DataAccessException, SQLException {
        AuthData auth = authDAO.createAuth(new AuthData("testUser","test1"));
        GameData game = gameDAO.createGame("test");
        ListGameResult result = listGameService.listGame(auth.authToken());
        Assertions.assertNull(result.message());
    }
    @Test
    public void testListGameFail() throws DataAccessException, SQLException {
        AuthData auth = authDAO.createAuth(new AuthData("testUser","test1"));
        GameData game = gameDAO.createGame("test");
        ListGameResult result = listGameService.listGame(null);
        Assertions.assertEquals(result.message(),"Error: unauthorized");
    }

}
