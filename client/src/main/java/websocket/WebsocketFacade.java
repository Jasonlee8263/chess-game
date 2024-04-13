package websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import model.ResponseException;
import webSocketMessages.serverMessages.ERROR;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketFacade extends Endpoint {
    Session session;
    GameHandler gameHandler;
    public WebsocketFacade(String url, GameHandler gameHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.gameHandler = gameHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverMessage.getServerMessageType()){
                        case ERROR -> gameHandler.notify(new ERROR(message));
                        case NOTIFICATION -> gameHandler.notify(new Notification(message));
                        case LOAD_GAME -> gameHandler.notify(new LoadGame(new ChessGame()));
                    }
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void joinPlayer(JoinPlayer joinPlayer) throws ResponseException {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(joinPlayer));
        }
        catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void joinObserver(JoinObserver joinObserver) throws ResponseException {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(joinObserver));
        }
        catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void makeMove(MakeMove makeMove) throws ResponseException {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(makeMove));
        }
        catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    public void leaveGame(Leave leave) throws ResponseException {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(leave));
            this.session.close();
        }
        catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    public void resign(Resign resign) throws ResponseException {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson(resign));
        }
        catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
}
