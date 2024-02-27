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
    public void delete(String authToken) throws DataAccessException {
        authDAO.deleteAuth(authToken);
    }
}
