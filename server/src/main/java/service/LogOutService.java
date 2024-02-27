package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import model.AuthData;

public class LogOutService {
    private MemoryAuthDAO authDAO;
    public LogOutService(MemoryAuthDAO authDAO){
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
