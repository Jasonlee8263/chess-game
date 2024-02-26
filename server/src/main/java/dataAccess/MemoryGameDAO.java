package dataAccess;

import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{
    private Collection<GameData> gameDataHashSet = new HashSet<>();
    @Override
    public GameData createGame(GameData game) {
        gameDataHashSet.add(game);
        return game;
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
