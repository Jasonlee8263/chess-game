package clientTests;

import model.ResponseException;
import model.requestAndResult.LogInRequest;
import model.requestAndResult.RegisterRequest;
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
        serverFacade = new ServerFacade("http://localhost:"+port);
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
        RegisterRequest request = new RegisterRequest("test","password","testemail");
        Assertions.assertDoesNotThrow(()->serverFacade.register(request));
    }
    @Test
    public void registerTestFail(){
        RegisterRequest request = new RegisterRequest("test","password",null);
        Assertions.assertThrows(ResponseException.class, ()->serverFacade.register(request));
    }
    @Test
    public void loginTest() throws ResponseException {
        RegisterRequest request = new RegisterRequest("test","password","testemail");
        serverFacade.register(request);
        LogInRequest logInRequest = new LogInRequest("test","password");
        Assertions.assertDoesNotThrow(()->serverFacade.login(logInRequest));
    }
    @Test
    public void loginTestFail() throws ResponseException{
        RegisterRequest request = new RegisterRequest("test","password","testemail");
        serverFacade.register(request);
        LogInRequest logInRequest = new LogInRequest("test","password2");
        Assertions.assertThrows(ResponseException.class,()->serverFacade.login(logInRequest));
    }
    @Test
    public void logOutTest() throws ResponseException{
        RegisterRequest request = new RegisterRequest("test","password","testemail");
        serverFacade.register(request);
        LogInRequest logInRequest = new LogInRequest("test","password");
        serverFacade.login(logInRequest);
        serverFacade.logout();

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
