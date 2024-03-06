package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import model.AuthData;

public class LogOutService {
    private AuthDAO authDAO;
    public LogOutService(AuthDAO authDAO){
        this.authDAO = authDAO;
    }
    public String delete(String authToken) throws DataAccessException {
        if(authDAO.getAuth(authToken)==null){
            return "Error: unauthorized";
        }
        authDAO.deleteAuth(authToken);
        return "";
    }
}
