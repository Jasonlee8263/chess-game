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
        if((userDAO.getUser(request.username()).username().equals(request.username()) && (userDAO.getUser(request.username()).password().equals(request.password())))) {
            String authToken = UUID.randomUUID().toString();
            authDAO.createAuth(new AuthData(request.username(), authToken));
            return new LogInResult(request.username(), authToken);
        }
        return null;
    }
}
