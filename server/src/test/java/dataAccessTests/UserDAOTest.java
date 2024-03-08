package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.MySqlUserDAO;
import dataAccess.UserDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDAOTest {
    private static UserDAO userDAO;
    @BeforeAll
    public static void createUserDAO() throws DataAccessException {
        userDAO = new MySqlUserDAO();
    }
    //tests
    @BeforeEach
    public void clear() throws DataAccessException {
        userDAO.clear();
    }
    @Test
    public void testCreateUser(){

    }
    @Test
    public void testCreateUserFail(){

    }
    @Test
    public void testGetUser(){

    }
    @Test
    public void testGetUserFail(){

    }
    @Test
    public void testClear(){

    }
}
