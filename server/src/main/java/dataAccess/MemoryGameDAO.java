package dataAccess;

import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{
    private Collection<GameData> gameDataHashSet = new HashSet<>();
    public int gameID = 1;
    @Override
    public GameData createGame(String gameName) {
        GameData newGame = new GameData(gameID,null,null,gameName,null);
        gameDataHashSet.add(newGame);
        gameID++;
        return newGame;
    }

    @Override
    public GameData getGame(GameData game){
        for(GameData item: gameDataHashSet) {
            if(item.equals(game)) {
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
    public GameData updateGame(GameData game){
        return null;
    }

    @Override
    public void clear(){
        gameDataHashSet.clear();
    }

}
