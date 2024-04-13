package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import model.requestAndResult.CreateGameRequest;
import model.requestAndResult.CreateGameResult;
import service.GameService;

public class CreateGameTest {
    private static GameService createGameService;
    private static MemoryGameDAO gameDAO = new MemoryGameDAO();
    private static MemoryAuthDAO authDAO = new MemoryAuthDAO();
    @BeforeAll
    public static void setCreateGameService(){
        createGameService = new GameService(authDAO,gameDAO);
    }
    @Test
    public void testCreateGame() throws DataAccessException {
        createGameService.createGame(new CreateGameRequest("test"));
        Assertions.assertEquals(1,gameDAO.listGame().size());

    }
    @Test
    public void testCreateGameFail() throws DataAccessException {
        CreateGameResult createGameResult = createGameService.createGame(new CreateGameRequest(null));
        Assertions.assertEquals(0,gameDAO.listGame().size());
    }
}
