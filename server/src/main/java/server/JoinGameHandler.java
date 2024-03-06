package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import requestAndResult.JoinGameRequest;
import service.*;
import spark.Request;
import spark.Response;

import java.util.Map;

public class JoinGameHandler {
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    public JoinGameHandler(AuthDAO authDAO, GameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    public Object joinGame(Request req, Response res) throws DataAccessException {
        String authToken =  req.headers("Authorization");
        Gson gson = new Gson();
        JoinGameRequest request = gson.fromJson(req.body(),JoinGameRequest.class);
        JoinGameService joinGameService = new JoinGameService(authDAO,gameDAO);
        String result = joinGameService.joinGame(request,authToken);
        if(authDAO.getAuth(authToken)==null){
            res.status(401);
            return new Gson().toJson(Map.of("message","Error: unauthorized"));
        }
        else if(result=="Error: already taken"){
            res.status(403);
            return new Gson().toJson(Map.of("message","Error: already taken"));
        }
        else if(result=="Error: bad request"){
            res.status(400);
            return new Gson().toJson(Map.of("message","Error: bad request"));

        }

        return "{}";
    }
}
