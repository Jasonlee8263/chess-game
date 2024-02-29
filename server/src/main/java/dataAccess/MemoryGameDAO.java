package dataAccess;

import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class MemoryGameDAO implements GameDAO{
    private Collection<GameData> gameDataHashSet = new HashSet<>();
    private MemoryAuthDAO authDAO;
    private int gameID = 1;
    private String whiteUsername;
    private String blackUsername;

    @Override
    public GameData createGame(String gameName) {
        GameData newGame = new GameData(gameID,null,null,gameName,null);
        gameDataHashSet.add(newGame);
        gameID++;
        return newGame;
    }

    @Override
    public GameData getGame(Integer gameID){
        for(GameData item: gameDataHashSet) {
            if(item.gameID().equals(gameID)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Collection<GameData> listGame(){
        return gameDataHashSet;
    }

    @Override
    public void updateGame(Integer gameID,String userName,String color){
        GameData game = getGame(gameID);
        if (game != null) {
            if ("WHITE".equals(color)){
                game = new GameData(gameID,userName,game.blackUsername(),game.gameName(),game.game());
            }
            else if("BLACK".equals(color)){
                game = new GameData(gameID,game.whiteUsername(),userName,game.gameName(),game.game());
            }
            gameDataHashSet.removeIf(g -> g.gameID().equals(gameID));
            gameDataHashSet.add(game);
        }
    }
//    public void updateGame(Integer gameID, String userName, String color) {
//        GameData game = getGame(gameID);
//        if (game != null) {
//            String whiteUsername = game.whiteUsername();
//            String blackUsername = game.blackUsername();
//
//            if ("WHITE".equals(color)) {
//                whiteUsername = userName;
//            } else if ("BLACK".equals(color)) {
//                blackUsername = userName;
//            }
//
//            GameData updatedGame = new GameData(gameID, whiteUsername, blackUsername, game.gameName(), game.game());
//            updateGameData(gameID, updatedGame);
//        }
//    }
//
//    private void updateGameData(Integer gameID, GameData updatedGame) {
//        // Find and update the existing GameData object in the collection
//        for (Iterator<GameData> iterator = gameDataHashSet.iterator(); iterator.hasNext();) {
//            GameData gameData = iterator.next();
//            if (gameData.gameID().equals(gameID)) {
//                iterator.remove(); // Remove the old GameData object
//                break; // Exit loop once found
//            }
//        }
//        gameDataHashSet.add(updatedGame); // Add the updated GameData object
//    }

    @Override
    public void clear(){
        gameDataHashSet.clear();
    }



}
