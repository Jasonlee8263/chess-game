package dataAccessTests;

import dataAccess.*;
import model.AuthData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class AuthDAOTest {
    private static AuthDAO authDAO;
    private static UserDAO userDAO;
    @BeforeAll
    public static void createAuthDAO() throws DataAccessException {
        authDAO = new MySqlAuthDAO();
        userDAO = new MySqlUserDAO();
    }
    @BeforeEach
    public void clear() throws DataAccessException {
        authDAO.clear();
    }
    @Test
    public void testCreateAuth() throws DataAccessException, SQLException {
        AuthData expected = new AuthData("existingUser","testAuth");
        AuthData existingUser = authDAO.createAuth(new AuthData("existingUser","testAuth"));
        Assertions.assertEquals(expected,existingUser,"error");
    }
    @Test
    public void testCreateAuthFail() throws DataAccessException,SQLException{
        authDAO.createAuth(new AuthData("existingUser","testAuth"));
        var thrown = Assertions.assertThrows(DataAccessException.class,()->{
            authDAO.createAuth(new AuthData("existingUser","testAuth"));
        });
    }
    @Test
    public void testGetAuth() throws DataAccessException {
        var existingAuth = authDAO.createAuth(new AuthData("existingUser","testAuth"));
        var auth = authDAO.getAuth(existingAuth.authToken());
        Assertions.assertEquals("testAuth",auth.authToken());
    }
    @Test
    public void testGetAuthFail() throws DataAccessException {
        var auth = authDAO.getAuth("testAuth");
        Assertions.assertEquals(new AuthData(null,null),auth);
    }
    @Test
    public void testDeleteAuth() throws DataAccessException, SQLException {
        var auth = authDAO.createAuth(new AuthData("existingUser","testAuth"));
        authDAO.deleteAuth(auth.authToken());
        var testAuth = authDAO.getAuth("testAuth");
        Assertions.assertEquals(new AuthData(null,null),testAuth);
    }
    @Test
    public void testDeleteAuthFail(){
        Assertions.assertDoesNotThrow(() -> {
            authDAO.deleteAuth("testAuth");
        });
    }
    @Test
    public void testClear() throws DataAccessException {
        authDAO.createAuth(new AuthData("user1", "auth1"));
        authDAO.createAuth(new AuthData("user2", "auth2"));
        authDAO.clear();
        Assertions.assertEquals(new AuthData(null, null), authDAO.getAuth("auth1"));
        Assertions.assertEquals(new AuthData(null, null), authDAO.getAuth("auth2"));
    }
}
