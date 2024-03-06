package dataAccess;

import model.GameData;

import java.sql.SQLException;
import java.util.Collection;

public class MySqlGameDAO implements GameDAO{
    public GameData createGame(String gameName){

    }
    public GameData getGame(Integer gameID) throws DataAccessException {

    }
    public Collection<GameData> listGame() throws DataAccessException {

    }
    public void updateGame(Integer gameID,String authToken,String color) throws DataAccessException{

    }
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE game";
        try(var conn = DatabaseManager.getConnection()){
            try (var ps = conn.prepareStatement(statement)){
                ps.executeUpdate();
            }
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
}
