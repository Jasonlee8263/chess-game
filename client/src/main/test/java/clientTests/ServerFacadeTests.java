package clientTests;

import org.junit.jupiter.api.*;
import server.Server;

public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }
    @BeforeEach
    public void clear(){
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void registerTest() {
        Assertions.assertTrue(true);
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
