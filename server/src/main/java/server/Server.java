package server;

import dataAccess.*;
import spark.*;
import websocket.WebsocketHandler;

public class Server {



    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        UserDAO userDAO = null;
        GameDAO gameDAO = null;
        AuthDAO authDAO = null;
        try {
            userDAO = new MySqlUserDAO();
            gameDAO = new MySqlGameDAO();
            authDAO = new MySqlAuthDAO();
        }
        catch (DataAccessException e){
            System.out.println("Error");
            stop();
            System.exit(1);
        }


        // Register your endpoints and handle exceptions here.
        ClearHandler clearHandler = new ClearHandler(authDAO,gameDAO,userDAO);
        RegisterHandler registerHandler = new RegisterHandler(authDAO,userDAO);
        LogInHandler logInHandler = new LogInHandler(authDAO,userDAO);
        LogOutHandler logOutHandler = new LogOutHandler(authDAO);
        CreateGameHandler createGameHandler = new CreateGameHandler(authDAO,gameDAO);
        ListGameHandler listGameHandler = new ListGameHandler(authDAO,gameDAO);
        JoinGameHandler joinGameHandler = new JoinGameHandler(authDAO,gameDAO);
        WebsocketHandler websocketHandler = new WebsocketHandler(gameDAO,authDAO);

        Spark.webSocket("/connect", websocketHandler);

        Spark.post("/user",registerHandler::register);
        Spark.delete("/db",clearHandler::clear);
        Spark.post("/session",logInHandler::login);
        Spark.delete("/session",logOutHandler::logout);
        Spark.post("/game",createGameHandler::createGame);
        Spark.get("/game",listGameHandler::listGame);
        Spark.put("/game",joinGameHandler::joinGame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
