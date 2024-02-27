package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class RegisterService {
    private MemoryAuthDAO authDAO;
    private MemoryUserDAO userDAO;
    public RegisterService(MemoryAuthDAO authDAO,MemoryUserDAO userDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }
    public Object register(RegisterRequest request){
        UserData user = new UserData(request.username(), request.password(), request.email());
        userDAO.createUser(user);
        String authToken = UUID.randomUUID().toString();
        authDAO.createAuth(new AuthData(request.username(), authToken));
        return new RegisterResult(request.username(), authToken);
    }
}
