package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import requestAndResult.ListGameResult;
import service.ListGameService;

import java.sql.SQLException;
import java.util.Collection;

public class ListGameTest {
    private static ListGameService listGameService;
    private static MemoryAuthDAO authDAO = new MemoryAuthDAO();
    private static MemoryGameDAO gameDAO = new MemoryGameDAO();

    @BeforeAll
    public static void setListGameService() {
        listGameService = new ListGameService(authDAO,gameDAO);
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
