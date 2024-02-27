package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class LogInService {
    private MemoryAuthDAO authDAO;
    private MemoryGameDAO gameDAO;
    private MemoryUserDAO userDAO;
    public LogInService(MemoryAuthDAO authDAO,MemoryUserDAO userDAO){
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }
    public LogInResult login(LogInRequest request) {
//        if(request.username()==userDAO.getUser().username())
        if((userDAO.getUser(request.username()).username().equals(request.username()) && (userDAO.getUser(request.username()).password().equals(request.password())))) {
            AuthData auth = authDAO.createAuth(new AuthData(request.username(), UUID.randomUUID().toString()));
            String authToken = auth.authToken();
            return new LogInResult(request.username(), authToken);
        }
        return null;
    }
}
