package server;

import com.google.gson.Gson;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import service.ListGameResult;
import service.ListGameService;
import spark.Request;
import spark.Response;

public class ListGameHandler {
    private MemoryAuthDAO authDAO;
    private MemoryGameDAO gameDAO;
    public ListGameHandler(MemoryAuthDAO authDAO,MemoryGameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    public Object listGame(Request req, Response res) {
        String authToken = req.headers("Authorization");
        Gson gson = new Gson();
        ListGameService listGameService = new ListGameService(authDAO, gameDAO);
        ListGameResult result = listGameService.listGame(authToken);
        if(authDAO.getAuth(authToken)==null){
            res.status(401);
        }
        return gson.toJson(result);
    }
}
