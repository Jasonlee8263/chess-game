package dataAccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    GameData createGame(String gameName);
    GameData getGame(Integer gameID) throws DataAccessException;
    Collection<GameData> listGame() throws DataAccessException;
    void updateGame(Integer gameID,String authToken,String color) throws DataAccessException;
    void clear() throws DataAccessException;
}
