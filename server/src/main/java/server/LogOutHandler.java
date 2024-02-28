package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import service.LogOutService;
import spark.Request;
import spark.Response;

import java.util.Map;
import java.util.Objects;

public class LogOutHandler {
    private MemoryAuthDAO authDAO;
    public LogOutHandler(MemoryAuthDAO authDAO){
        this.authDAO = authDAO;
    }
    public Object logout(Request req, Response res) throws DataAccessException {
            String authToken = req.headers("Authorization");
            LogOutService logOutService = new LogOutService(authDAO);
            String result = logOutService.delete(authToken);
            if(Objects.equals(result, "Error: unauthorized")){
                res.status(401);
                return new Gson().toJson(Map.of("message","Error: unauthorized"));
            }
            else{
                res.status(200);
                return "{}";
            }

    }
}
