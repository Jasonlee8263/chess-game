package dataAccess;

import model.UserData;

import java.sql.SQLException;

public interface UserDAO {
    UserData createUser(UserData user) throws DataAccessException, SQLException;
    UserData getUser(String username) throws DataAccessException, SQLException;
    void clear() throws DataAccessException;
}
