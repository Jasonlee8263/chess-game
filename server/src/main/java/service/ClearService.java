package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;

public class ClearService {
    private final MemoryAuthDAO authDAO = new MemoryAuthDAO();
    private final MemoryGameDAO gameDAO = new MemoryGameDAO();
    private final MemoryUserDAO userDAO = new MemoryUserDAO();
//    public ClearService(MemoryAuthDAO authDAO, MemoryGameDAO gameDAO, MemoryUserDAO userDAO) {
//        this.authDAO = authDAO;
//        this.gameDAO = gameDAO;
//        this.userDAO = userDAO;
//    }

    public void clear() {
        authDAO.clear();
        gameDAO.clear();
        userDAO.clear();
    }
}
