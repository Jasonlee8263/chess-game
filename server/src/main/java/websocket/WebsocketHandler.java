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
import java.util.Objects;

@WebSocket
public class WebsocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private GameDAO gameDAO;
    private AuthDAO authDAO;
    private boolean resignState = false;

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
                    var player = new Gson().fromJson(message, MakeMove.class);
                    var connection = new Connection(player.getAuthString(), session);
                    var error = new Gson().toJson(new ERROR("Error: Invaild Move"));
                    connection.send(error);
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
        var board = new LoadGame(game.game());
        connection.send(new Gson().toJson(board));
        var notifyMessage = String.format("%s is joined as %s",playerName,player.playerColor);
        var notification = new Notification(notifyMessage);
        connections.broadcast(Integer.toString(player.gameID),player.getAuthString(), notification);
    }
    private void joinObserver(String message,Session session) throws DataAccessException, IOException {
        var observer = new Gson().fromJson(message, JoinObserver.class);
        var connection = new Connection(observer.getAuthString(), session);
        var game = gameDAO.getGame(observer.gameID);
        var playerName = authDAO.getAuth(observer.getAuthString()).username();
        if(game.gameID()==null || playerName==null){
            var error = new Gson().toJson(new ERROR("Error: This game doesn't exist"));
            connection.send(error);
            return;
        }
        connections.add(Integer.toString(observer.gameID), observer.getAuthString(), session);
        var board = new LoadGame(new ChessGame());
        connection.send(new Gson().toJson(board));
        var notifyMessage = String.format("%s is joined as observer",playerName);
        var notification = new Notification(notifyMessage);
        connections.broadcast(observer.gameID.toString(),observer.getAuthString(), notification);
    }
    private void makeMove(String message,Session session) throws DataAccessException, IOException, InvalidMoveException {
        var makeMove = new Gson().fromJson(message, MakeMove.class);
        var playerName = authDAO.getAuth(makeMove.getAuthString()).username();
        var connection = new Connection(makeMove.getAuthString(), session);
        var game = gameDAO.getGame(makeMove.gameID);
        if (game.game().getTeamTurn().equals(ChessGame.TeamColor.WHITE) && !game.whiteUsername().equals(playerName)){
            var error = new Gson().toJson(new ERROR("Error: Invaild Command"));
            connection.send(error);
            return;
        }
        else if (game.game().getTeamTurn().equals(ChessGame.TeamColor.BLACK) && !game.blackUsername().equals(playerName)){
            var error = new Gson().toJson(new ERROR("Error: Invaild Command"));
            connection.send(error);
            return;
        }
        else if(game.game().isInStalemate(game.game().getTeamTurn())||game.game().isInCheckmate(game.game().getTeamTurn())){
            var error = new Gson().toJson(new ERROR("Error: Invaild Command"));
            connection.send(error);
            return;
        }
        game.game().makeMove(makeMove.move);
        var board = new LoadGame(game.game());
        var notifyMessage = String.format("%s did %s",playerName,makeMove.move.toString());
        var notification = new Notification(notifyMessage);
        connections.broadcast(makeMove.gameID.toString(),null,board);
        connections.broadcast(makeMove.gameID.toString(),makeMove.getAuthString(), notification);
    }
    private void leave(String message,Session session) throws DataAccessException, IOException {
        var leave = new Gson().fromJson(message, Leave.class);
        var playerName = authDAO.getAuth(leave.getAuthString()).username();
        var notifyMessage = String.format("%s has left the room",playerName);
        var notification = new Notification(notifyMessage);
        connections.remove(session);
        connections.broadcast(leave.gameID.toString(),leave.getAuthString(), notification);
    }
    private void resign(String message,Session session) throws IOException, DataAccessException {
        var resign = new Gson().fromJson(message, Resign.class);
        var playerName = authDAO.getAuth(resign.getAuthString()).username();
        var game = gameDAO.getGame(resign.gameID);
        var connection = new Connection(resign.getAuthString(), session);
        if(!playerName.equals(game.whiteUsername()) && !playerName.equals(game.blackUsername())){
            var error = new Gson().toJson(new ERROR("Error: Invaild Command"));
            connection.send(error);
            return;
        }
        if (resignState){
            var error = new Gson().toJson(new ERROR("Error: Invaild Command"));
            connection.send(error);
            return;
        }
        var notifyMessage = String.format("%s has resigned",playerName);
        var notification = new Notification(notifyMessage);
        resignState = true;
        connections.broadcast(resign.gameID.toString(),null, notification);
    }


}
