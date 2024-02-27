package dataAccess;

import model.UserData;

public interface UserDAO {
    UserData createUser(UserData user);
    UserData getUser(String username) throws DataAccessException;
    void clear() throws DataAccessException;
}
