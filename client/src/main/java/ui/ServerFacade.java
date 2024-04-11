package ui;

import com.google.gson.Gson;
import model.ResponseException;
import model.requestAndResult.*;
import websocket.GameHandler;
import websocket.HttpFacade;
import websocket.WebsocketFacade;

public class ServerFacade {
    private final String serverUrl;
    private static String authToken;

    public ServerFacade(String url, GameHandler gameHandler) throws ResponseException {
        serverUrl = url;
        HttpFacade httpFacade = new HttpFacade(serverUrl);
        WebsocketFacade websocketFacade;
        websocketFacade = new WebsocketFacade(url, gameHandler);

    }
    public RegisterResult register(RegisterRequest request) throws ResponseException {
        HttpFacade httpFacade = new HttpFacade(serverUrl);
        return httpFacade.register(request);
    }

    public LogInResult login(LogInRequest request) throws ResponseException {
        HttpFacade httpFacade = new HttpFacade(serverUrl);
        return httpFacade.login(request);
    }

    public void logout() throws ResponseException {
        HttpFacade httpFacade = new HttpFacade(serverUrl);
        httpFacade.logout();
    }

    public CreateGameResult createGame(CreateGameRequest request) throws ResponseException {
        HttpFacade httpFacade = new HttpFacade(serverUrl);
        return httpFacade.createGame(request);
    }

    public ListGameResult listGame() throws ResponseException {
        HttpFacade httpFacade = new HttpFacade(serverUrl);
        return httpFacade.listGame();
    }

    public Object joinGame(JoinGameRequest request) throws ResponseException {
        HttpFacade httpFacade = new HttpFacade(serverUrl);
        return httpFacade.joinGame(request);
    }

    public void clear() throws ResponseException {
        HttpFacade httpFacade = new HttpFacade(serverUrl);
        httpFacade.clear();
    }

}
