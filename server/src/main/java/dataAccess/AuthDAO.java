package dataAccess;

import model.AuthData;

public interface AuthDAO {
    AuthData createAuth(AuthData auth);
    AuthData getAuth(AuthData auth) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
    void clear() throws DataAccessException;
}
