package server;

import com.google.gson.Gson;
import dataAccess.*;
import model.requestAndResult.LogInRequest;
import model.requestAndResult.LogInResult;
import service.LogInService;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

public class LogInHandler {
    private AuthDAO authDAO;
    private UserDAO userDAO;
    public LogInHandler(AuthDAO authDAO,UserDAO userDAO){
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }
    public Object login(Request req, Response res) throws SQLException, DataAccessException {
        Gson gson = new Gson();
        LogInRequest request = gson.fromJson(req.body(), LogInRequest.class);
        LogInService logInService = new LogInService(authDAO, userDAO);
        LogInResult result = logInService.login(request);
        if(result.message()=="Error: unauthorized"){
            res.status(401);
        }
        else{
            res.status(200);
        }
        return gson.toJson(result);

    }
}
