package server;

import com.google.gson.Gson;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import requestAndResult.RegisterRequest;
import requestAndResult.RegisterResult;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class RegisterHandler {
    private MemoryAuthDAO authDAO;
    private MemoryUserDAO userDAO;
    public RegisterHandler(MemoryAuthDAO authDAO,MemoryUserDAO userDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }
    public Object register(Request req, Response res){
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
