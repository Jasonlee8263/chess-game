package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import model.ResponseException;
import model.requestAndResult.*;
import webSocketMessages.userCommands.JoinObserver;
import webSocketMessages.userCommands.JoinPlayer;
import websocket.GameHandler;
import websocket.HttpFacade;
import websocket.WebsocketFacade;

public class ServerFacade {
    private final String serverUrl;
    private static String authToken;
    private WebsocketFacade ws;
    private HttpFacade httpFacade;
    private GameHandler gameHandler;

    public ServerFacade(String url, GameHandler gameHandler) throws ResponseException {
        serverUrl = url;
        httpFacade = new HttpFacade(serverUrl);
        ws = new WebsocketFacade(url, gameHandler);
        this.gameHandler = gameHandler;

    }
    public RegisterResult register(RegisterRequest request) throws ResponseException {
        return httpFacade.register(request);
    }

    public LogInResult login(LogInRequest request) throws ResponseException {
        return httpFacade.login(request);
    }

    public void logout() throws ResponseException {
        httpFacade.logout();
    }

    public CreateGameResult createGame(CreateGameRequest request) throws ResponseException {
        return httpFacade.createGame(request);
    }

    public ListGameResult listGame() throws ResponseException {
        return httpFacade.listGame();
    }

    public void joinGame(JoinGameRequest request) throws ResponseException {
        authToken = httpFacade.authToken;
        JoinPlayer joinPlayer = new JoinPlayer(authToken,request.gameID(),request.playerColor());
        httpFacade.joinGame(request);
        ws.joinPlayer(joinPlayer);
    }
    public void joinAsObserver(JoinGameRequest request) throws ResponseException {
        authToken = httpFacade.authToken;
        JoinObserver joinObserver = new JoinObserver(authToken,request.gameID());
        httpFacade.joinGame(request);
        ws.joinObserver(joinObserver);
    }


    public void clear() throws ResponseException {
        HttpFacade httpFacade = new HttpFacade(serverUrl);
        httpFacade.clear();
    }

}
