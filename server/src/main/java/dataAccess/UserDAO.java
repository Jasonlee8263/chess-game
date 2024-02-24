package dataAccess;

import model.UserData;

interface UserDAO {
    UserData createUser(UserData user);
    UserData getUser(UserData user) throws DataAccessException;
    void clear() throws DataAccessException;
}
