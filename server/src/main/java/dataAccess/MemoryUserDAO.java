package dataAccess;

import model.UserData;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class MemoryUserDAO implements UserDAO {
    private Collection<UserData> userDataHashSet = new HashSet<>();
    @Override
    public UserData createUser(UserData user) {
        userDataHashSet.add(user);
        return user;
    }

    @Override
    public UserData getUser(UserData user){
        for (UserData item: userDataHashSet) {
            if (item.equals(user)){
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
