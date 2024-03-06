package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.GameData;
import requestAndResult.ListGameResult;

import java.sql.SQLException;
import java.util.Collection;

public class ListGameService {
    private GameDAO gameDAO;
    private AuthDAO authDAO;
    public ListGameService(AuthDAO authDAO, GameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    public ListGameResult listGame(String authToken) throws DataAccessException, SQLException {
        if (authDAO.getAuth(authToken) == null) {
            return new ListGameResult(null,"Error: unauthorized");
        }
        Collection<GameData> gameList = gameDAO.listGame();
        return new ListGameResult(gameList,null);
    }
}
