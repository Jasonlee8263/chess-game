package dataAccess;

import model.AuthData;

import java.sql.SQLException;

public interface AuthDAO {
    AuthData createAuth(AuthData auth) throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException, SQLException;
    void clear() throws DataAccessException;
}
