package dataAccess;

import model.AuthData;

import java.util.Collection;
import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO {
    private Collection<AuthData> authData = new HashSet<>();
    @Override
    public AuthData createAuth(AuthData auth){
        return null;
    }

    @Override
    public AuthData getAuth(AuthData auth){
        return null;
    }

    @Override
    public void deleteAuth(AuthData auth){
    }
    @Override
    public void clear(){
        authData.clear();
    }
}
