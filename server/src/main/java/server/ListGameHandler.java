package server;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.AuthData;
import model.requestAndResult.ListGameResult;
import service.GameService;
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
        GameService listGameService = new GameService(authDAO, gameDAO);
        ListGameResult result = listGameService.listGame(authToken);
        if(authDAO.getAuth(authToken).equals(new AuthData(null,null))){
            res.status(401);
        }
        return gson.toJson(result);
    }
}
