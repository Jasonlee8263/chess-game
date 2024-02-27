package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;

public class CreateGameService {
    private MemoryGameDAO gameDAO;
    private MemoryAuthDAO authDAO;
    public CreateGameService(MemoryAuthDAO authDAO,MemoryGameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }
//    public CreateGameResult createGame(CreateGameRequest request){
//        gameDAO.createGame(request);
//    }
}
