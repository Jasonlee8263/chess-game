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
//    private int executeUpdate(String statement, Object... params) throws DataAccessException {
//        try (var conn = DatabaseManager.getConnection()) {
//            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
//                for (var i = 0; i < params.length; i++) {
//                    var param = params[i];
//                    if (param instanceof String p) ps.setString(i + 1, p);
//                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
//                    else if (param instanceof ChessGame p) ps.setString(i + 1, p.toString());
//                    else if (param == null) ps.setNull(i + 1, NULL);
//                }
//                ps.executeUpdate();
//
//                var rs = ps.getGeneratedKeys();
//                if (rs.next()) {
//                    return rs.getInt(1);
//                }
//
//                return 0;
//            }
//        } catch (SQLException e) {
//            throw new DataAccessException(e.getMessage());
//        }
//    }
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
