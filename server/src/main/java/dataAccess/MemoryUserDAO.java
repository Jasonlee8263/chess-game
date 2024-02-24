package dataAccess;

import model.UserData;

public class MemoryUserDAO implements UserDAO {
    @Override
    public UserData createUser(UserData user) {
        return null;
    }

    @Override
    public UserData getUser(UserData user) throws DataAccessException{
        return null;
    }

    @Override
    public void clear() throws DataAccessException {

    }
}
