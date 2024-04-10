package websocket;

import com.google.gson.Gson;

import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.Session;

import java.io.IOException;

import static java.lang.System.exit;

public class WebsocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    public void onMessage(Session session, String message) throws IOException {
        ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
        switch (serverMessage.getServerMessageType()) {
            case LOAD_GAME -> enter(action.visitorName(), session);
            case ERROR -> exit(action.visitorName());
            case NOTIFICATION ->
        }
    }


}
