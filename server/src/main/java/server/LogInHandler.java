package server;

import com.google.gson.Gson;
import service.LogInRequest;
import service.LogInResult;
import service.LogInService;
import spark.Request;
import spark.Response;

public class LogInHandler {
    public Object login(Request req, Response res){
        Gson gson = new Gson();
        LogInRequest request = gson.fromJson(req.body(), LogInRequest.class);
        LogInService logInService = new LogInService();
        LogInResult result = logInService.login(request);
        return gson.toJson(result);
    }
}
