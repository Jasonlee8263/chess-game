package server;

import com.google.gson.Gson;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import requestAndResult.CreateGameRequest;
import requestAndResult.CreateGameResult;
import service.CreateGameService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class CreateGameHandler {
    private MemoryAuthDAO authDAO;
    private MemoryGameDAO gameDAO;
    public CreateGameHandler(MemoryAuthDAO authDAO,MemoryGameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    public Object createGame(Request req, Response res){
        String authToken =  req.headers("Authorization");
        Gson gson = new Gson();
        CreateGameRequest request = gson.fromJson(req.body(),CreateGameRequest.class);
        CreateGameService createGameService = new CreateGameService(authDAO,gameDAO);
        CreateGameResult result = createGameService.createGame(request);
        if(authDAO.getAuth(authToken)==null){
            res.status(401);
            return new Gson().toJson(Map.of("message","Error: unauthorized"));
        }
        else if(result.message()=="Error: bad request"){
            res.status(400);
            return new Gson().toJson(Map.of("message","Error: bad request"));
        }
        else{
            res.status(200);
        }
        return gson.toJson(result);
    }
}
