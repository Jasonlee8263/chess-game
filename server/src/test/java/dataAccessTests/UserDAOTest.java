package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.MySqlUserDAO;
import dataAccess.UserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

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
    public void testCreateUser() throws SQLException, DataAccessException {
        var expected = new UserData("existingUser","1234","test@email.com");
        var actual = userDAO.createUser(new UserData("existingUser","1234","test@email.com"));
        Assertions.assertEquals(expected,actual);

    }
    @Test
    public void testCreateUserFail() throws SQLException,DataAccessException{
        userDAO.createUser(new UserData("existingUser","1234","test@gmail.com"));
        var thrown = Assertions.assertThrows(DataAccessException.class,()->{
            userDAO.createUser(new UserData("existingUser","1234","test@gmail.com"));
        });
    }
    @Test
    public void testGetUser() throws SQLException, DataAccessException {
        userDAO.createUser(new UserData("existingUser","1234","test@gmail.com"));
        var user = userDAO.getUser("existingUser");
        var expected = new UserData("existingUser","1234","test@gmail.com");
        Assertions.assertEquals(expected,user);
    }
    @Test
    public void testGetUserFail() throws DataAccessException,SQLException{
        var user = userDAO.getUser("existingUser");
        Assertions.assertEquals(new UserData("","",""),user);
    }
    @Test
    public void testClear() throws SQLException, DataAccessException {
        userDAO.createUser(new UserData("existingUser","1234","test@gmail.com"));
        userDAO.clear();
        var user = userDAO.getUser("existingUser");
        Assertions.assertEquals(new UserData("","",""),user);    }
}
