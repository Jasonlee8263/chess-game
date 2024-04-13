package websocket;

import chess.ChessGame;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

public interface GameHandler {
    void notify(ServerMessage serverMessage);
    void updateGame(ChessGame game);
}
