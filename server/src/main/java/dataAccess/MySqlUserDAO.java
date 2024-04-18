package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import com.mysql.cj.protocol.Resultset;
import model.UserData;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySqlUserDAO implements UserDAO{
    public MySqlUserDAO() throws DataAccessException{
        configureDatabase();
    }
    public UserData createUser(UserData user) throws DataAccessException, SQLException {
        var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                    ps.setString(1, user.username());
                    ps.setString(2, user.password());
                    ps.setString(3, user.email());
                    ps.executeUpdate();
                }
            return new UserData(user.username(), user.password(), user.email());
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
    public UserData getUser(String username) throws DataAccessException,SQLException{
        var statement = "SELECT username, password, email FROM user WHERE username=?";
        try(var conn = DatabaseManager.getConnection()){
            String userName = "";
            String password = "";
            String email = "";
            try (var ps = conn.prepareStatement(statement)){
                ps.setString(1,username);
                try(var rs = ps.executeQuery()){
                    while(rs.next()){
                        //Display values
                        userName = rs.getString("username");
                        password = rs.getString("password");
                        email = rs.getString("email");
                        System.out.println("Username: " + userName);
                        System.out.println("Password: " + password);
                        System.out.println("Email: " + email);
                    }
                }
                return new UserData(userName, password,email);
            }
        }
        catch (SQLException e){
            throw new DataAccessException(e.getMessage());
        }
    }
    public void clear() throws DataAccessException{
        var statement = "TRUNCATE user";
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
            CREATE TABLE IF NOT EXISTS  user (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`)
            )
            """
    };
    private void configureDatabase() throws DataAccessException {
        MySqlAuthDAO.tryConfigureDatabase(createStatements);
    }
}
