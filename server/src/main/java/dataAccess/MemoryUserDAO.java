package dataAccess;

import model.UserData;

import java.util.Collection;
import java.util.HashSet;

public class MemoryUserDAO implements UserDAO {
    private Collection<UserData> userData = new HashSet<>();
    @Override
    public UserData createUser(UserData user) {
        return null;
    }

    @Override
    public UserData getUser(UserData user){
        return null;
    }

    @Override
    public void clear(){
        userData.clear();
    }
}
