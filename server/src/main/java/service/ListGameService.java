package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import model.GameData;
import requestAndResult.ListGameResult;

import java.util.Collection;

public class ListGameService {
    private MemoryGameDAO gameDAO;
    private MemoryAuthDAO authDAO;
    public ListGameService(MemoryAuthDAO authDAO, MemoryGameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    public ListGameResult listGame(String authToken){
        if (authDAO.getAuth(authToken) == null) {
            return new ListGameResult(null,"Error: unauthorized");
        }
        Collection<GameData> gameList = gameDAO.listGame();
        return new ListGameResult(gameList,null);
    }
}
