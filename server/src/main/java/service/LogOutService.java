package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import model.AuthData;

import java.sql.SQLException;

public class LogOutService {
    private AuthDAO authDAO;
    public LogOutService(AuthDAO authDAO){
        this.authDAO = authDAO;
    }
    public String delete(String authToken) throws DataAccessException, SQLException {
        AuthData emptyAuth = new AuthData(null,null);
        if(authDAO.getAuth(authToken).equals(emptyAuth)){
            return "Error: unauthorized";
        }
        authDAO.deleteAuth(authToken);
        return "";
    }
}
