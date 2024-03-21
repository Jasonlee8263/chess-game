package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.AuthData;
import model.GameData;
import model.requestAndResult.JoinGameRequest;

import java.sql.SQLException;
import java.util.Objects;

public class JoinGameService {
    private GameDAO gameDAO;
    private AuthDAO authDAO;
    public JoinGameService(AuthDAO authDAO,GameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public String joinGame(JoinGameRequest request, String authToken) throws DataAccessException, SQLException {
        String username = "";
        if (authDAO.getAuth(authToken)!=null){
            username = authDAO.getAuth(authToken).username();
        }
        GameData gameData = gameDAO.getGame(request.gameID());
        AuthData emptyAuth = new AuthData(null,null);
        if((!(Objects.equals(request.playerColor(),"WHITE") || Objects.equals(request.playerColor(),"BLACK") || request.playerColor()==null)) || gameData==null || gameData.gameID()==null){
            return "Error: bad request";
        }
        else if(("WHITE".equals(request.playerColor()) && gameData.whiteUsername()!=null) || ("BLACK".equals(request.playerColor()) && gameData.blackUsername()!=null)){
            return "Error: already taken";
        }
        gameDAO.updateGame(request.gameID(), username, request.playerColor());
        return "";
    }
}
