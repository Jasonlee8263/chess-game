package dataAccess;

import model.GameData;

import java.util.Collection;
import java.util.HashSet;

public class MemoryGameDAO implements GameDAO{
    private Collection<GameData> gameData = new HashSet<>();
    @Override
    public GameData createGame(GameData game) {
        return null;
    }

    @Override
    public GameData getGame(GameData game){
        return null;
    }

    @Override
    public Collection<GameData> listGame(GameData game){
        return null;
    }

    @Override
    public GameData updateGame(GameData game){
        return null;
    }

    @Override
    public void clear(){
        gameData.clear();
    }
}
