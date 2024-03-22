package ui;

import com.google.gson.Gson;
import model.ResponseException;
import model.requestAndResult.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
    private final String serverUrl;
    private static String authToken;

    public ServerFacade(String url) {
        serverUrl = url;
    }
    public RegisterResult register(RegisterRequest request) throws ResponseException {
        String path = "/user";
        RegisterResult response = this.makeRequest("POST",path,request, RegisterResult.class);
        authToken = response.authToken();
        return response;
    }

    public LogInResult login(LogInRequest request) throws ResponseException {
        String path = "/session";
        LogInResult response = this.makeRequest("POST",path,request,LogInResult.class);
        authToken = response.authToken();
        return response;
    }

    public void logout() throws ResponseException {
        String path = "/session";
        this.makeRequest("DELETE",path,null,null);
    }

    public CreateGameResult createGame(CreateGameRequest request) throws ResponseException {
        String path = "/game";
        return this.makeRequest("POST",path,request,CreateGameResult.class);
    }

    public ListGameResult listGame() throws ResponseException {
        String path = "/game";
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            http.addRequestProperty("Authorization",authToken);
            writeBody(null, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, ListGameResult.class);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    public Object joinGame(JoinGameRequest request) throws ResponseException {
        String path = "/game";
        return this.makeRequest("PUT",path,request,null);
    }

    public void clear() throws ResponseException {
        String path = "/db";
        this.makeRequest("DELETE",path,null,null);
    }
    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.addRequestProperty("Authorization",authToken);
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
