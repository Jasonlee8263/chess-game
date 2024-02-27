package server;

import com.google.gson.Gson;
import dataAccess.GameDAO;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import service.CreateGameRequest;
import service.CreateGameResult;
import service.CreateGameService;
import spark.Request;
import spark.Response;

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
        }
        else if(result.message()=="Error: bad request"){
            res.status(400);
        }
        else{
            res.status(200);
        }
        return gson.toJson(result);
    }
}
