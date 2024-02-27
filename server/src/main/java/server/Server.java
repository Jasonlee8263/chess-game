package server;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import spark.*;

public class Server {
private MemoryUserDAO userDAO;
private MemoryGameDAO gameDAO;
private MemoryAuthDAO authDAO;
    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        ClearHandler clearHandler = new ClearHandler(authDAO,gameDAO,userDAO);
        LogInHandler logInHandler = new LogInHandler(authDAO,userDAO);

        Spark.delete("/db",clearHandler::clear);
        Spark.post("/session",logInHandler::login);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
