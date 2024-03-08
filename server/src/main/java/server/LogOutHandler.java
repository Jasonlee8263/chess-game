package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.AuthDAO;
import service.LogOutService;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class LogOutHandler {
    private AuthDAO authDAO;
    public LogOutHandler(AuthDAO authDAO){
        this.authDAO = authDAO;
    }
    public Object logout(Request req, Response res) throws DataAccessException, SQLException {
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
