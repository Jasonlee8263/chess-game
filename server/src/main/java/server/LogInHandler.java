package server;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import service.LogInRequest;
import service.LogInResult;
import service.LogInService;
import spark.Request;
import spark.Response;

public class LogInHandler {
    private MemoryAuthDAO authDAO;
    private MemoryUserDAO userDAO;
    public LogInHandler(MemoryAuthDAO authDAO,MemoryUserDAO userDAO){
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }
    public Object login(Request req, Response res){
        Gson gson = new Gson();
        LogInRequest request = gson.fromJson(req.body(), LogInRequest.class);
        LogInService logInService = new LogInService(authDAO, userDAO);
        LogInResult result = logInService.login(request);
        res.status(200);
//        System.out.println(gson.toJson(result));
        return gson.toJson(result);
    }
}
