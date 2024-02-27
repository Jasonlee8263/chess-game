package dataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    GameData createGame(String gameName);
    GameData getGame(GameData game) throws DataAccessException;
    Collection<GameData> listGame() throws DataAccessException;
    GameData updateGame(GameData game) throws DataAccessException;
    void clear() throws DataAccessException;
}
