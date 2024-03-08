package DAOTests;

import dataAccess.*;
import model.AuthData;
import model.UserData;
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
    public void testCreateAuthFail(){
        AuthData expected = new AuthData("existingUser","testAuth");

    }
    @Test
    public void testGetAuth(){

    }
    @Test
    public void testGetAuthFail(){

    }
    @Test
    public void testDeleteAuth(){

    }
    @Test
    public void testDeleteAuthFail(){

    }
    @Test
    public void testClear(){

    }
}
