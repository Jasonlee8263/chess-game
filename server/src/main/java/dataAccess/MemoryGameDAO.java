package dataAccess;

import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class MemoryGameDAO implements GameDAO{
    private Collection<GameData> gameDataHashSet = new HashSet<>();
    private MemoryAuthDAO authDAO;
    private int gameID = 1;
    private String whiteUsername;
    private String blackUsername;

    @Override
    public GameData createGame(String gameName) {
        GameData newGame = new GameData(gameID,null,null,gameName,null);
        gameDataHashSet.add(newGame);
        gameID++;
        return newGame;
    }

    @Override
    public GameData getGame(Integer gameID){
        for(GameData item: gameDataHashSet) {
            if(item.gameID().equals(gameID)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Collection<GameData> listGame(){
        return gameDataHashSet;
    }

    @Override
    public void updateGame(Integer gameID,String userName,String color){
        GameData game = getGame(gameID);
        if (game != null) {
            if ("WHITE".equals(color)){
                game = new GameData(gameID,userName,game.blackUsername(),game.gameName(),game.game());
            }
            else if("BLACK".equals(color)){
                game = new GameData(gameID,game.whiteUsername(),userName,game.gameName(),game.game());
            }
            gameDataHashSet.removeIf(g -> g.gameID().equals(gameID));
            gameDataHashSet.add(game);
        }
    }
//

    @Override
    public void clear(){
        gameDataHashSet.clear();
    }



}
