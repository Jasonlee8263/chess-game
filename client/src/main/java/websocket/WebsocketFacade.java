package websocket;

import com.google.gson.Gson;
import model.ResponseException;
import webSocketMessages.serverMessages.ServerMessage;

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
                    gameHandler.notify(serverMessage);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void joinPlayer(String playerName) throws ResponseException {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson());
        }
        catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void joinObserver(String playerName) throws ResponseException {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson());
        }
        catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public void makeMove(String playerName) throws ResponseException {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson());
        }
        catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    public void leaveGame(String playerName) throws ResponseException {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson());
            this.session.close();
        }
        catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    public void resign(String playerName) throws ResponseException {
        try {
            this.session.getBasicRemote().sendText(new Gson().toJson());
        }
        catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
}
