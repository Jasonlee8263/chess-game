package websocket;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;

import dataAccess.*;
import model.requestAndResult.JoinGameRequest;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import service.GameService;
import webSocketMessages.serverMessages.ERROR;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;


import java.io.IOException;

@WebSocket
public class WebsocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private GameDAO gameDAO;
    private AuthDAO authDAO;
    private ChessGame chessGame = new ChessGame();
    public WebsocketHandler(GameDAO gameDAO,AuthDAO authDAO) {
        try {
            this.gameDAO = new MySqlGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            this.authDAO = new MySqlAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(message, session);
            case JOIN_OBSERVER -> joinObserver(message,session);
            case MAKE_MOVE -> {
                try {
                    makeMove(message,session);
                } catch (InvalidMoveException e) {
                    throw new RuntimeException(e);
                }
            }
            case LEAVE -> leave(message,session);
            case RESIGN -> resign(message,session);
        }
    }

    private void joinPlayer(String message,Session session) throws IOException, DataAccessException {
        var player = new Gson().fromJson(message,JoinPlayer.class);
        var connection = new Connection(player.getAuthString(), session);
        var game = gameDAO.getGame(player.gameID);
        var playerName = authDAO.getAuth(player.getAuthString()).username();
        if(game.whiteUsername()==null && game.blackUsername()==null) {
            var error = new Gson().toJson(new ERROR("No one ever joined"));
            connection.send(error);
            return;
        }
        if(player.playerColor.equals(ChessGame.TeamColor.WHITE.toString())){
            if(game.whiteUsername()!=null && !game.whiteUsername().equals(playerName)){
                var error = new Gson().toJson(new ERROR("Error: already taken"));
                connection.send(error);
                return;
            }
        }
        else if(player.playerColor.equals(ChessGame.TeamColor.BLACK.toString())){
            if(game.blackUsername()!=null && !game.blackUsername().equals(playerName)){
                var error = new Gson().toJson(new ERROR("Error: already taken"));
                connection.send(error);
                return;
            }
        }
        connections.add(Integer.toString(player.gameID), player.getAuthString(), session);
        var board = new LoadGame(new ChessGame());
        connection.send(new Gson().toJson(board));
        var notifyMessage = String.format("%s is joined as %s",playerName,player.playerColor);
        var notification = new Notification(notifyMessage);
        connections.broadcast(Integer.toString(player.gameID),player.getAuthString(), notification);
    }
    private void joinObserver(String message,Session session) throws DataAccessException, IOException {
        var player = new Gson().fromJson(message, JoinObserver.class);
        var connection = new Connection(player.getAuthString(), session);
        var game = gameDAO.getGame(player.gameID);
        var playerName = authDAO.getAuth(player.getAuthString()).username();
        if(game.gameID()==null || playerName==null){
            var error = new Gson().toJson(new ERROR("Error: This game doesn't exist"));
            connection.send(error);
            return;
        }
        connections.add(Integer.toString(player.gameID), player.getAuthString(), session);
        var board = new LoadGame(new ChessGame());
        connection.send(new Gson().toJson(board));
        var notifyMessage = String.format("%s is joined as observer",playerName);
        var notification = new Notification(notifyMessage);
        connections.broadcast(player.gameID.toString(),player.getAuthString(), notification);
    }
    private void makeMove(String message,Session session) throws DataAccessException, IOException, InvalidMoveException {
        var player = new Gson().fromJson(message, MakeMove.class);
        var playerName = authDAO.getAuth(player.getAuthString()).username();
        connections.add(Integer.toString(player.gameID), player.getAuthString(), session);
        chessGame.makeMove(player.move);
        var board = new LoadGame(chessGame);
        var notifyMessage = String.format("%s did %s",playerName,player.move.toString());
        var notification = new Notification(notifyMessage);
        connections.broadcast(null,null,board);
        connections.broadcast(player.gameID.toString(),player.getAuthString(), notification);
    }
    private void leave(String message,Session session) throws DataAccessException, IOException {
        var player = new Gson().fromJson(message, Leave.class);
        var playerName = authDAO.getAuth(player.getAuthString()).username();
        var notifyMessage = String.format("%s has left the room",playerName);
        var notification = new Notification(notifyMessage);
        connections.removeFromGame(Integer.toString(player.gameID),player.getAuthString(),session);
        connections.broadcast(player.gameID.toString(),player.getAuthString(), notification);
    }
    private void resign(String message,Session session) throws IOException, DataAccessException {
        var player = new Gson().fromJson(message, Resign.class);
        var playerName = authDAO.getAuth(player.getAuthString()).username();
        var notifyMessage = String.format("%s has resigned",playerName);
        var notification = new Notification(notifyMessage);
        connections.broadcast(player.gameID.toString(),player.getAuthString(), notification);
    }


}
