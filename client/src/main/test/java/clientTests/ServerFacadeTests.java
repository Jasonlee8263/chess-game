package clientTests;

import model.ResponseException;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;

public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        serverFacade = new ServerFacade("https://localhost:"+port);
    }
    @BeforeEach
    public void clear() throws ResponseException {
        serverFacade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerTest() {

    }
    @Test
    public void registerTestFail(){

    }
    @Test
    public void loginTest(){

    }
    @Test
    public void loginTestFail(){

    }
    @Test
    public void logOutTest(){

    }
    @Test
    public void logOutTestFail(){

    }
    @Test
    public void createGameTest(){

    }
    @Test
    public void createGameTestFail(){

    }
    @Test
    public void listGameTest(){

    }
    @Test
    public void listGameTestFail(){

    }
    @Test
    public void joinGameTest(){

    }
    @Test
    public void joinGameTestFail(){

    }
    @Test
    public void clearTest(){

    }

}
