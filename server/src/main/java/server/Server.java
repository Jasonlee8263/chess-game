package server;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import spark.*;

public class Server {
private MemoryUserDAO userDAO = new MemoryUserDAO();
private MemoryGameDAO gameDAO = new MemoryGameDAO();
private MemoryAuthDAO authDAO = new MemoryAuthDAO();
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        ClearHandler clearHandler = new ClearHandler(authDAO,gameDAO,userDAO);
        RegisterHandler registerHandler = new RegisterHandler(authDAO,userDAO);
        LogInHandler logInHandler = new LogInHandler(authDAO,userDAO);
        LogOutHandler logOutHandler = new LogOutHandler(authDAO);
        CreateGameHandler createGameHandler = new CreateGameHandler(authDAO,gameDAO);

        Spark.post("/user",registerHandler::register);
        Spark.delete("/db",clearHandler::clear);
        Spark.post("/session",logInHandler::login);
        Spark.delete("/session",logOutHandler::logout);
        Spark.post("/game",createGameHandler::createGame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
