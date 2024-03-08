package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.MySqlGameDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameDAOTest {
    private static GameDAO gameDAO;
    @BeforeAll
    public static void createGameDAO() throws DataAccessException {
        gameDAO = new MySqlGameDAO();
    }
    @BeforeEach
    public void clear() throws DataAccessException {
        gameDAO.clear();
    }
    @Test
    public void testCreateGame(){

    }
    @Test
    public void testCreateGameFail(){

    }
    @Test
    public void testGetGame(){

    }
    @Test
    public void testGetGameFail(){

    }
    @Test
    public void testListGame(){

    }
    @Test
    public void testListGameFail(){

    }
    @Test
    public void testUpdateGame(){

    }
    @Test
    public void testUpdateGameFail(){

    }
    @Test
    public void testClear(){

    }
}
