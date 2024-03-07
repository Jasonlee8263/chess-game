package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class MySqlGameDAO implements GameDAO{
    public MySqlGameDAO() throws DataAccessException{
        configureDatabase();
    }
    public GameData createGame(String gameName) throws DataAccessException{
        GameData game = new GameData(null,null,null,null,null);
        ChessGame chessGame = new ChessGame();
        var json = new Gson().toJson(chessGame);
        var statement = "INSERT INTO game (whiteUsername, BlackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement,RETURN_GENERATED_KEYS)) {
                ps.setString(1, game.whiteUsername());
                ps.setString(2, game.blackUsername());
                ps.setString(3, gameName);
                ps.setString(4, json);
                ps.executeUpdate();
                var rs = ps.getGeneratedKeys();
                var ID = 0;
                if (rs.next()) {
                    ID = rs.getInt(1);
                    game.gameID() = ID;
                }


            }
            return game;
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
    public GameData getGame(Integer gameID) throws DataAccessException {
        var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, game FROM game WHERE gameID=?";
        try(var conn = DatabaseManager.getConnection()){
            try (var ps = conn.prepareStatement(statement)){
                ResultSet rs = ps.executeQuery();
                Integer gameid = null;
                String whiteUsername = "";
                String blackUsername = "";
                String gameName = "";
                String game = "";
                ChessGame chessGame = null;
                while(rs.next()){
                    //Display values
                    gameid = rs.getInt("gameID");
                    whiteUsername = rs.getString("whiteUsername");
                    blackUsername = rs.getString("blackUsername");
                    gameName = rs.getString("gameName");
                    game = rs.getString("game");
                    chessGame = new Gson().fromJson(game, ChessGame.class);
                }
                return new GameData(gameid, whiteUsername,blackUsername,gameName,chessGame);
            }
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
    public Collection<GameData> listGame() throws DataAccessException, SQLException {
        var results = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, BlackUsername, gameName, game FROM game";
            try (var ps = conn.prepareStatement(statement)) {
                var rs = ps.executeQuery();
            }
        }
        return results;
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
            CREATE TABLE IF NOT EXISTS  game (
              `gameID` int NOT NULL AUTO_INCREMENT,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256) NOT NULL,
              `game` TEXT DEFAULT NULL,
              PRIMARY KEY (`gameID`)
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
