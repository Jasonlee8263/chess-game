package websocket;

import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectionManager {
    public final ConcurrentHashMap<String, List<Connection>> connections = new ConcurrentHashMap<>();

    public void add(String gameID, String authToken, Session session) {
        List<Connection> connectionList = connections.computeIfAbsent(gameID, k -> new CopyOnWriteArrayList<>());
        var connection = new Connection(authToken, session);
        connectionList.add(connection);
    }

    public void removeFromGame(String gameID, String authToken, Session session) {
        connections.remove(gameID);
    }

    public void remove(Session session) {
        connections.values().forEach(connectionList -> connectionList.removeIf(connection -> connection.session.equals(session)));
    }

    public void broadcast(String gameID,String excludeAuthToken, ServerMessage serverMessage) throws IOException {
        List<Connection> connectionList = connections.get(gameID);
        if(connectionList!=null) {
            for (Connection connection : connectionList) {
                if (connection.session.isOpen() && !connection.authToken.equals(excludeAuthToken)) {
                    connection.send(serverMessage.toString());
                }
            }
            connectionList.removeIf(connection -> !connection.session.isOpen());
            if (connectionList.isEmpty()) {
                connections.remove(gameID);
            }
        }
    }
}
