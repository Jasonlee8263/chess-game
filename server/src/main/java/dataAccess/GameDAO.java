package dataAccess;

import model.GameData;

import java.util.Collection;

interface GameDAO {
    GameData createGame(GameData game);
    GameData getGame(GameData game) throws DataAccessException;
    Collection<GameData> listGame(GameData game) throws DataAccessException;
    GameData updateGame(GameData game) throws DataAccessException;
    void clear() throws DataAccessException;
}
