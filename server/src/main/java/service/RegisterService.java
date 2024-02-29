package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import requestAndResult.RegisterRequest;
import requestAndResult.RegisterResult;

import java.util.UUID;

public class RegisterService {
    private MemoryAuthDAO authDAO;
    private MemoryUserDAO userDAO;
    public RegisterService(MemoryAuthDAO authDAO,MemoryUserDAO userDAO){
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }
    public RegisterResult register(RegisterRequest request){
        UserData user = new UserData(request.username(), request.password(), request.email());
        if(user.username()==null || user.password()==null || user.email()==null){
            return new RegisterResult(null, null,"Error: bad request");
        }
        else if(userDAO.getUser(user.username())!=null && userDAO.getUser(user.username()).username().equals(user.username())){
            return new RegisterResult(null, null,"Error: already taken");
        }
        else{
            userDAO.createUser(user);
            String authToken = UUID.randomUUID().toString();
            authDAO.createAuth(new AuthData(request.username(), authToken));
            return new RegisterResult(request.username(), authToken,null);
        }
    }
}
