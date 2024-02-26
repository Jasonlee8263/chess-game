package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.UserData;

import java.util.UUID;

public class LogInService {
    private MemoryAuthDAO authDAO;
    private MemoryGameDAO gameDAO;
    private MemoryUserDAO userDAO;
    public LogInResult login(LogInRequest request) {
        if(userDAO.) {
            return new LogInResult(request.username(), UUID.randomUUID().toString());
        }
    }
}
