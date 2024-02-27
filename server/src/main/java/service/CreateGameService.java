package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import model.GameData;

public class CreateGameService {
    private MemoryGameDAO gameDAO;
    private MemoryAuthDAO authDAO;
    private int gameID;
    public CreateGameService(MemoryAuthDAO authDAO,MemoryGameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    public CreateGameResult createGame(CreateGameRequest request){
        GameData gameData = new GameData(null,null,null, request.gameName(), null);
        if(gameData.gameName()==null){
            return new CreateGameResult(null,"Error: bad request");
        }
        else{
            gameDAO.createGame(request.gameName());
            gameID++;
            return new CreateGameResult(gameID, null);
        }


    }
}
