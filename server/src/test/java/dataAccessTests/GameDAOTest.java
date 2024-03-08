package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.MySqlGameDAO;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void testCreateGame() throws DataAccessException {
        var game = gameDAO.createGame("testGame");
        Assertions.assertEquals("testGame",game.gameName());
    }
    @Test
    public void testCreateGameFail() throws DataAccessException {
        GameData createdGame1 = gameDAO.createGame("testGame");
        var createdGame2 = gameDAO.createGame("testGame");
        Assertions.assertNotEquals(createdGame1.gameID(), createdGame2.gameID());

    }
    @Test
    public void testGetGame() throws DataAccessException {
        var game = gameDAO.createGame("testGame");
        Assertions.assertNotNull(gameDAO.getGame(game.gameID()));
    }
    @Test
    public void testGetGameFail() throws DataAccessException {
        Assertions.assertEquals(new GameData(null,"","","",null),gameDAO.getGame(123));
    }
    @Test
    public void testListGame() throws DataAccessException, SQLException {
        gameDAO.createGame("testGame");
        var gameList = gameDAO.listGame();
        Assertions.assertEquals(1,gameList.size());
    }
    @Test
    public void testListGameFail() throws SQLException, DataAccessException {
        assertTrue(gameDAO.listGame().isEmpty());
    }
    @Test
    public void testUpdateGame() throws DataAccessException, SQLException {
        GameData gameData = gameDAO.createGame("testGame");
        gameDAO.updateGame(gameData.gameID(),"testUser","WHITE");
        GameData updatedGame = gameDAO.getGame(gameData.gameID());
        Assertions.assertNotEquals(gameData,updatedGame);
    }
    @Test
    public void testUpdateGameFail() throws DataAccessException, SQLException {
        GameData gameData = gameDAO.createGame("testGame");
        gameDAO.updateGame(gameData.gameID(),"testUser","WHITE");
        GameData updatedGame = gameDAO.getGame(123);
        Assertions.assertNotEquals(gameData,updatedGame);
    }
    @Test
    public void testClear() throws DataAccessException, SQLException {
        gameDAO.createGame("testGame");
        gameDAO.clear();
        var gameList = gameDAO.listGame();
        Assertions.assertEquals(0,gameList.size());
    }
}
