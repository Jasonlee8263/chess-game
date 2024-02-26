package server;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;
    private final UserDAO userDAO;
    private ClearService clearService;
    public ClearHandler(AuthDAO authDAO, GameDAO gameDAO, UserDAO userDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        this.userDAO = userDAO;
        clearService = new ClearService(authDAO, gameDAO, userDAO);
    }

    public Object clear(Request req, Response res) {
        try{
            clearService.clear();
            res.status(200);
            return "{}";
        } catch (DataAccessException e) {
            res.status(500);
            return "{ \"message\": \"Error: "+e.getMessage()+"\" }";
        }

    }
}
