package server;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import requestAndResult.ListGameResult;
import service.ListGameService;
import spark.Request;
import spark.Response;

import java.sql.SQLException;

public class ListGameHandler {
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    public ListGameHandler(AuthDAO authDAO,GameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    public Object listGame(Request req, Response res) throws DataAccessException, SQLException {
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
