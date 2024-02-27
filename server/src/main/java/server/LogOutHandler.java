package server;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import service.LogOutService;
import spark.Request;
import spark.Response;

public class LogOutHandler {
    private MemoryAuthDAO authDAO;
    public LogOutHandler(MemoryAuthDAO authDAO){
        this.authDAO = authDAO;
    }
    public Object logout(Request req, Response res) throws DataAccessException {
            String authToken =  req.headers("Authorization");
            LogOutService logOutService = new LogOutService(authDAO);
            logOutService.delete(authToken);
            res.status(200);
            return "{}";

    }
}
