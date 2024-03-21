package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import model.requestAndResult.RegisterRequest;
import model.requestAndResult.RegisterResult;
import service.RegisterService;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

public class RegisterHandler {
    private AuthDAO authDAO;
    private UserDAO userDAO;
    public RegisterHandler(AuthDAO authDAO,UserDAO userDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }
    public Object register(Request req, Response res) throws SQLException, DataAccessException {
        Gson gson = new Gson();
        RegisterRequest request = gson.fromJson(req.body(),RegisterRequest.class);
        RegisterService registerService = new RegisterService(authDAO,userDAO);
        RegisterResult result = registerService.register(request);
        if(result.message()=="Error: bad request"){
            res.status(400);
        }
        else if(result.message()=="Error: already taken"){
            res.status(403);
        }
        else{
            res.status(200);
        }
        return gson.toJson(result);
    }
}
