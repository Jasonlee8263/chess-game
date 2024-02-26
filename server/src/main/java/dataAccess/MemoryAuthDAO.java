package dataAccess;

import model.AuthData;

import java.util.Collection;
import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO {
    private Collection<AuthData> authDataHashSet = new HashSet<>();
    @Override
    public AuthData createAuth(AuthData auth){
        authDataHashSet.add(auth);
        return auth;
    }

    @Override
    public AuthData getAuth(AuthData auth){
        for(AuthData item:authDataHashSet){
            if(item.equals(auth)){
                return item;
            }
        }
        return null;
    }

    @Override
    public void deleteAuth(AuthData auth){
        authDataHashSet.remove(auth);
    }
    @Override
    public void clear(){
        authDataHashSet.clear();
    }

    public Collection<AuthData> getAuthData() {
        return authDataHashSet;
    }
}
