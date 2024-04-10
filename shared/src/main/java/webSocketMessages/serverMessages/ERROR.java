package webSocketMessages.serverMessages;

public class ERROR extends ServerMessage{
    private String errorMessage;
    public ERROR(String errorMessage) {
        super(ServerMessageType.ERROR);
        this.errorMessage = errorMessage;
    }
}
