import com.google.gson.Gson;
import model.UserData;
import model.requestAndResult.CreateGameRequest;
import model.requestAndResult.JoinGameRequest;
import model.requestAndResult.LogInRequest;
import model.requestAndResult.RegisterRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }
    public Object register(RegisterRequest request){
        String path = "/user";
        return this.makeRequest("POST",path,request,RegisterRequest.class);
    }

    public Object login(LogInRequest request){
        String path = "/session";
        return this.makeRequest("POST",path,request,LogInRequest.class);
    }

    public void logout() {
        String path = "/session";
        this.makeRequest("DELETE",path,null,null);
    }

    public Object createGame(CreateGameRequest request){
        String path = "/game";
        return this.makeRequest("POST",path,request,CreateGameRequest.class);
    }

    public void listGame() {
        String path = "/game";
        this.makeRequest("GET",path,null,null);
    }

    public Object joinGame(JoinGameRequest request) {
        String path = "/game";
        return this.makeRequest("PUT",path,request,JoinGameRequest.class);
    }
    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
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
