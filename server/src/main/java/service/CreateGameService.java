package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.GameData;
import model.requestAndResult.CreateGameRequest;
import model.requestAndResult.CreateGameResult;

public class CreateGameService {
    private GameDAO gameDAO;
    private AuthDAO authDAO;
    public CreateGameService(AuthDAO authDAO,GameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
    public CreateGameResult createGame(CreateGameRequest request) throws DataAccessException {
        GameData gameData = new GameData(null,null,null, request.gameName(), null);
        if(gameData.gameName()==null){
            return new CreateGameResult(null,"Error: bad request");
        }
        else{
            GameData gameData1 = gameDAO.createGame(request.gameName());
            return new CreateGameResult(gameData1.gameID(), null);
        }


    }
}
