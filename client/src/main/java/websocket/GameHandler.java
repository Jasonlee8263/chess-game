package websocket;

import webSocketMessages.serverMessages.ServerMessage;

public interface GameHandler {
    void notify(ServerMessage serverMessage);
}
