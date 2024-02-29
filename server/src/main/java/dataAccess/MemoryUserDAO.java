package dataAccess;

import model.UserData;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class MemoryUserDAO implements UserDAO {
    private Collection<UserData> userDataHashSet;
    public MemoryUserDAO(){
        userDataHashSet = new HashSet<>();
    }
    @Override
    public UserData createUser(UserData user) {
        userDataHashSet.add(user);
        return user;
    }

    @Override
    public UserData getUser(String username){
        for (UserData item: userDataHashSet) {
            if (item.username().equals(username)){
                return item;
            }
        }
        return null;
    }

    @Override
    public void clear(){
        userDataHashSet.clear();
    }

    public Collection<UserData> getUserData() {
        return userDataHashSet;
    }
}
