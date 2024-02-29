package serviceTests;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import requestAndResult.CreateGameRequest;
import requestAndResult.CreateGameResult;
import service.CreateGameService;

public class CreateGameTest {
    private static CreateGameService createGameService;
    private static MemoryGameDAO gameDAO = new MemoryGameDAO();
    private static MemoryAuthDAO authDAO = new MemoryAuthDAO();
    @BeforeAll
    public static void setCreateGameService(){
        createGameService = new CreateGameService(authDAO,gameDAO);
    }
    @Test
    public void testCreateGame(){
        createGameService.createGame(new CreateGameRequest("test"));
        Assertions.assertEquals(1,gameDAO.listGame().size());
        CreateGameResult createGameResult = createGameService.createGame(new CreateGameRequest(null));
        Assertions.assertEquals(1,gameDAO.listGame().size());


    }
}
