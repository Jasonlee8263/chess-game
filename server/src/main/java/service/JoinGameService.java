package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import model.GameData;
import org.eclipse.jetty.server.Request;

import java.util.Objects;

public class JoinGameService {
    private MemoryGameDAO gameDAO;
    private MemoryAuthDAO authDAO;
    public JoinGameService(MemoryAuthDAO authDAO,MemoryGameDAO gameDAO){
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public String joinGame(JoinGameRequest request,String authToken){
        String username = "";
        if (authDAO.getAuth(authToken)!=null){
            username = authDAO.getAuth(authToken).username();
        }
        GameData gameData = gameDAO.getGame(request.gameID());
        if((!(Objects.equals(request.playerColor(),"WHITE") || Objects.equals(request.playerColor(),"BLACK") || request.playerColor()==null)) || gameData==null){
            return "Error: bad request";
        }
        else if(("WHITE".equals(request.playerColor()) && gameData.whiteUsername()!=null) || ("BLACK".equals(request.playerColor()) && gameData.blackUsername()!=null)){
            return "Error: already taken";
        }
        gameDAO.updateGame(request.gameID(), username, request.playerColor());
        return "";
    }
}
