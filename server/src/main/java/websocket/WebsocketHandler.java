package websocket;

import com.google.gson.Gson;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.requestAndResult.JoinGameRequest;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import service.GameService;
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
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException {
        UserGameCommand userGameCommand = new Gson().fromJson(message, UserGameCommand.class);
        switch (userGameCommand.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(message, session);
            case JOIN_OBSERVER -> joinObserver(message,session);
            case MAKE_MOVE -> makeMove(message,session);
            case LEAVE -> leave(message,session);
            case RESIGN -> resign(message,session);
        }
    }

    private void joinPlayer(String message,Session session) throws IOException, DataAccessException {
        var player = new Gson().fromJson(message,JoinPlayer.class);
        var game = gameDAO.getGame(player.gameID);
        var playerName = authDAO.getAuth(player.getAuthString()).username();
        connections.add(Integer.toString(game.gameID()), player.getAuthString(), session);
        var notifyMessage = String.format("%s is joined as %s",playerName,player.playerColor);
        var notification = new Notification(notifyMessage);
        connections.broadcast(player.getAuthString(), notification);
    }
    private void joinObserver(String message,Session session) throws DataAccessException, IOException {
        var player = new Gson().fromJson(message, JoinObserver.class);
        var game = gameDAO.getGame(player.gameID);
        var playerName = authDAO.getAuth(player.getAuthString()).username();
        connections.add(Integer.toString(game.gameID()), player.getAuthString(), session);
        var notifyMessage = String.format("%s is joined as observer",playerName);
        var notification = new Notification(notifyMessage);
        connections.broadcast(player.getAuthString(), notification);
    }
    private void makeMove(String message,Session session) throws DataAccessException, IOException {
        var player = new Gson().fromJson(message, MakeMove.class);
        var game = gameDAO.getGame(player.gameID);
        var playerName = authDAO.getAuth(player.getAuthString()).username();
        var notifyMessage = String.format("%s is joined as observer",playerName);
//        var notification = new LoadGame(notifyMessage);
//        connections.broadcast(player.getAuthString(), notification);
    }
    private void leave(String message,Session session) throws DataAccessException, IOException {
        var player = new Gson().fromJson(message, Leave.class);
        var game = gameDAO.getGame(player.gameID);
        var playerName = authDAO.getAuth(player.getAuthString()).username();
        var notifyMessage = String.format("%s has left the room",playerName);
        var notification = new Notification(notifyMessage);
        connections.broadcast(player.getAuthString(), notification);
    }
    private void resign(String message,Session session) throws IOException, DataAccessException {
        var player = new Gson().fromJson(message, Resign.class);
        var game = gameDAO.getGame(player.gameID);
        var playerName = authDAO.getAuth(player.getAuthString()).username();
        var notifyMessage = String.format("%s has resigned",playerName);
        var notification = new Notification(notifyMessage);
        connections.broadcast(player.getAuthString(), notification);
    }


}
