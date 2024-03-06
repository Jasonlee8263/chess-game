package dataAccess;

import model.AuthData;
import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlAuthDAO implements AuthDAO{
    public AuthData createAuth(AuthData auth) throws DataAccessException{
        var statement = "INSERT INTO auth (username, authToken) VALUES (?, ?)";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, auth.username());
                ps.setString(2, auth.authToken());
            }
            return new AuthData(auth.username(), auth.authToken());
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        var statement = "SELECT authToken FROM auth WHERE authToken=?";
        try(var conn = DatabaseManager.getConnection()){
            try (var ps = conn.prepareStatement(statement)){
                ResultSet rs = ps.executeQuery(statement);
                String userName = rs.getString("username");
                String authtoken = rs.getString("authToken");
                while(rs.next()){
                    //Display values
                    System.out.println("Username: " + userName);
                    System.out.println("AuthToken: " + authtoken);
                }
                return new AuthData(userName, authtoken);
            }
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }

    public void deleteAuth(String authToken){

    }
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE auth";
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
              `username` varchar(256) NOT NULL,
              `authToken` varchar(256) NOT NULL',
              PRIMARY KEY (`authToken`),
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
