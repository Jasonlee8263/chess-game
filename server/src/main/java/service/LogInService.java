package service;

import dataAccess.*;
import model.AuthData;
import requestAndResult.LogInRequest;
import requestAndResult.LogInResult;

import java.sql.SQLException;
import java.util.UUID;

public class LogInService {
    private AuthDAO authDAO;
    private GameDAO gameDAO;
    private UserDAO userDAO;
    public LogInService(AuthDAO authDAO,UserDAO userDAO){
        this.authDAO = authDAO;
        this.userDAO = userDAO;
    }
    public LogInResult login(LogInRequest request) throws SQLException, DataAccessException {
        if (userDAO.getUser(request.username())==null || !userDAO.getUser(request.username()).password().equals(request.password())){
            return new LogInResult(null,null,"Error: unauthorized");
        }
        else{
            String authToken = UUID.randomUUID().toString();
            authDAO.createAuth(new AuthData(request.username(), authToken));
            return new LogInResult(request.username(), authToken,null);
        }
    }
}
