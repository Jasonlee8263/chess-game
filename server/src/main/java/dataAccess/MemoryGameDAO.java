package dataAccess;

import model.GameData;

import java.util.Collection;

public class MemoryGameDAO implements GameDAO{
    @Override
    public GameData createGame(GameData game) {
        return null;
    }

    @Override
    public GameData getGame(GameData game) throws DataAccessException{
        return null;
    }

    @Override
    public Collection<GameData> listGame(GameData game) throws DataAccessException{
        return null;
    }

    @Override
    public GameData updateGame(GameData game) throws DataAccessException{
        return null;
    }

    @Override
    public void clear() throws DataAccessException {

    }
}
