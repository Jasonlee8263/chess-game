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
    public AuthData getAuth(String authToken){
        for(AuthData item:authDataHashSet){
            if(item.authToken().equals(authToken)){
                return item;
            }
        }
        return null;
    }

    @Override
    public void deleteAuth(String authToken){
        AuthData tempAuth = null;
        for(AuthData item:authDataHashSet){
            if(item.authToken().equals(authToken)){
                tempAuth = item;
            }
        }
        if(tempAuth!=null){
            authDataHashSet.remove(tempAuth);
        }
    }
    @Override
    public void clear(){
        authDataHashSet.clear();
    }

    public Collection<AuthData> getAuthData() {
        return authDataHashSet;
    }
}
