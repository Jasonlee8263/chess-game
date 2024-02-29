package serviceTests;

import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ClearService;

public class ClearTest {
    private static ClearService clearService;
    static MemoryUserDAO memoryUserDAO = new MemoryUserDAO();
    static MemoryAuthDAO memoryAuthDAO = new MemoryAuthDAO();
    static MemoryGameDAO memoryGameDAO = new MemoryGameDAO();

    @BeforeAll
    public static void createClearService() {
        clearService = new ClearService(memoryAuthDAO,memoryGameDAO,memoryUserDAO);


    }

    @Test
    public void testClear() throws DataAccessException {
        UserData user = memoryUserDAO.createUser(new UserData("test1","1234","test@gmail.com"));
        AuthData auth = memoryAuthDAO.createAuth(new AuthData("testAuthToken","test1"));
        GameData game = memoryGameDAO.createGame("test1");
        clearService.clear();
        Assertions.assertNull(memoryUserDAO.getUser(user.username()));
        Assertions.assertNull(memoryAuthDAO.getAuth(auth.authToken()));
        Assertions.assertEquals(0,memoryGameDAO.listGame().size());
    }

}
