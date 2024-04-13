package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.AuthData;
import model.GameData;
import model.requestAndResult.CreateGameRequest;
import model.requestAndResult.CreateGameResult;
import model.requestAndResult.JoinGameRequest;
import model.requestAndResult.ListGameResult;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Objects;

public class GameService {
    private GameDAO gameDAO;
    private AuthDAO authDAO;
    public GameService(AuthDAO authDAO, GameDAO gameDAO){
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
    public String joinGame(JoinGameRequest request, String authToken) throws DataAccessException, SQLException {
        String username = "";
        if (authDAO.getAuth(authToken)!=null){
            username = authDAO.getAuth(authToken).username();
        }
        GameData gameData = gameDAO.getGame(request.gameID());
        AuthData emptyAuth = new AuthData(null,null);
        if((!(Objects.equals(request.playerColor(),"white") || Objects.equals(request.playerColor(),"black") || request.playerColor()==null)) || gameData==null || gameData.gameID()==null){
            return "Error: bad request";
        }
        else if(("white".equals(request.playerColor()) && gameData.whiteUsername()!=null) || ("black".equals(request.playerColor()) && gameData.blackUsername()!=null)){
            return "Error: already taken";
        }
        gameDAO.updateGame(request.gameID(), username, request.playerColor());
        return "";
    }
    public ListGameResult listGame(String authToken) throws DataAccessException, SQLException {
        AuthData emptyAuth = new AuthData(null,null);
        if (authDAO.getAuth(authToken).equals(emptyAuth)) {
            return new ListGameResult(null,"Error: unauthorized");
        }
        Collection<GameData> gameList = gameDAO.listGame();
        return new ListGameResult(gameList,null);
    }
    public void joinPlayer(){

    }
}
