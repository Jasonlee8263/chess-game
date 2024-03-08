package dataAccess;

import model.GameData;

import java.sql.SQLException;
import java.util.Collection;

public interface GameDAO {
    GameData createGame(String gameName) throws DataAccessException;
    GameData getGame(Integer gameID) throws DataAccessException;
    Collection<GameData> listGame() throws DataAccessException, SQLException;
    void updateGame(Integer gameID,String authToken,String color) throws DataAccessException, SQLException;
    void clear() throws DataAccessException;
}
