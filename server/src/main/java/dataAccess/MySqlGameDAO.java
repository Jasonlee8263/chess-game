package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class MySqlGameDAO implements GameDAO{
    public MySqlGameDAO() throws DataAccessException{
        configureDatabase();
    }
    public GameData createGame(String gameName) throws DataAccessException{
        GameData game = new GameData(null,null,null,null,null);
        var statement = "INSERT INTO game (gameID, whiteUsername, BlackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, game.gameID());
                ps.setString(2, game.whiteUsername());
                ps.setString(3, game.blackUsername());
                ps.setString(4, game.gameName());
                ps.setString(5, game.game().toString());
            }
            return game;
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
    public GameData getGame(Integer gameID) throws DataAccessException {
        var statement = "SELECT gameID FROM game WHERE gameID=?";
        try(var conn = DatabaseManager.getConnection()){
            try (var ps = conn.prepareStatement(statement)){
                ResultSet rs = ps.executeQuery(statement);
                Integer gameid = null;
                String whiteUsername = "";
                String BlackUsername = "";
                String gameName = "";
                String game = "";
                while(rs.next()){
                    //Display values
                    gameid = rs.getInt("gameID");
                    whiteUsername = rs.getString("whiteUsername");
                    BlackUsername = rs.getString("BlackUsername");
                    gameName = rs.getString("gameName");
                    game = rs.getString("game");
                }
                return new GameData(gameid, whiteUsername,BlackUsername,gameName,null);
            }
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
    public Collection<GameData> listGame() throws DataAccessException {
        return null;
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
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `gameID` int NOT NULL,
              `whiteUsername` varchar(256)',
              `blackUsername` varchar(256)',
              `gameName` varchar(256) NOT NULL',
              `game` TEXT DEFAULT NULL,
              PRIMARY KEY (`gameID`),
            )
            """
    };
    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
