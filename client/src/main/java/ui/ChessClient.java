package ui;

import model.GameData;
import model.ResponseException;
import model.requestAndResult.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static ui.EscapeSequences.*;

public class ChessClient {
    private final ServerFacade serverFacade;
    private final String serverUrl;
    private boolean loginState = false;


    public ChessClient(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> logIn(params);
                case "logout" -> logOut();
                case "creategame" -> createGame(params);
                case "listgame" -> listGame();
                case "joingame" -> joinGame(params);
                case "joinasobserver" -> joinAsObserver(params);
                case "quit" -> "quit";
                default -> help();
            };
        }
        catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String register(String... params) throws ResponseException {
        if(params.length==3){
            RegisterRequest req = new RegisterRequest(params[0],params[1],params[2]);
            String username = serverFacade.register(req).username();
            loginState = true;
            return String.format("You registered and logged in as %s.", username);
        }
        throw new ResponseException(400, "Bad Request");
    }

    public String logIn(String... params) throws ResponseException{
        if(params.length==2){
            LogInRequest req = new LogInRequest(params[0],params[1]);
            var response = serverFacade.login(req);
            String username = response.username();
            loginState = true;
            return String.format("You logged in as %s.", username);
        }
        throw new ResponseException(400, "Bad Request");
    }
    public String logOut() throws ResponseException {
        serverFacade.logout();
        loginState = false;
        return "You logged out";
    }
    public String createGame(String... params) throws ResponseException {
        if(params.length==1){
            CreateGameRequest req = new CreateGameRequest(params[0]);
            var response = serverFacade.createGame(req);
            return "You created a game";
        }
        throw new ResponseException(400,"Bad Request");
    }
    public String listGame() throws ResponseException {
        ListGameResult response = serverFacade.listGame();
        for(GameData game:response.games()){
            System.out.println(String.format("GameID: %s, Gamename: %s, WhitePlayer: %s, BlackPlayer: %s", game.gameID(), game.gameName(), game.whiteUsername(), game.blackUsername()));
        }
        return "";
    }
    public String joinGame(String... params) throws ResponseException {
        if(params.length==2){
            JoinGameRequest req = new JoinGameRequest(params[0],Integer.parseInt(params[1]));
            serverFacade.joinGame(req);
        }
        String playerColor = params[0];
        drawBoard(playerColor);
        return "Join Success!";
    }

    public String joinAsObserver(String... params) throws ResponseException {
        if(params.length==1) {
            JoinGameRequest req = new JoinGameRequest(null,Integer.parseInt(params[0]));
            serverFacade.joinGame(req);
        }
        drawBoard(null);
        return "Joined as observer";
    }

    public String help() {
        if (!loginState) {
            return """
                    - Register <username> <password> <email>
                    - LogIn <username> <password>
                    - Help - possible commands
                    - Quit
                    """;
        }
        return """
                - Creategame <gamename>
                - Listgame
                - Joingame [WHITE|BLACK|<empty>] <gameid>
                - JoinAsObserver <gameid>
                - LogOut
                - Help - possible commands
                - Quit
                """;
    }
    private void drawBoard(String playerColor) {
        boolean whiteAtBottom = (playerColor == null || Objects.equals(playerColor, "white"));

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                // Alternate colors for the chessboard squares
                if ((row + col) % 2 == 0) {
                    System.out.print(SET_BG_COLOR_BLACK);
                } else {
                    System.out.print(SET_BG_COLOR_WHITE);
                }

                // Print the chess piece or empty square
                if (whiteAtBottom) {
                    // White pieces at the bottom, black pieces at the top
                    if (row == 1 || row == 2) {
                        switch (col) {
                            case 1:
                            case 8:
                                System.out.print(BLACK_ROOK);
                                break;
                            case 2:
                            case 7:
                                System.out.print(BLACK_KNIGHT);
                                break;
                            case 3:
                            case 6:
                                System.out.print(BLACK_BISHOP);
                                break;
                            case 4:
                                System.out.print(BLACK_QUEEN);
                                break;
                            case 5:
                                System.out.print(BLACK_KING);
                                break;
                        }
                    } else if (row == 7 || row == 8) {
                        switch (col) {
                            case 1:
                            case 8:
                                System.out.print(WHITE_ROOK);
                                break;
                            case 2:
                            case 7:
                                System.out.print(WHITE_KNIGHT);
                                break;
                            case 3:
                            case 6:
                                System.out.print(WHITE_BISHOP);
                                break;
                            case 4:
                                System.out.print(WHITE_QUEEN);
                                break;
                            case 5:
                                System.out.print(WHITE_KING);
                                break;
                        }
                    } else {
                        System.out.print(EMPTY);
                    }
                } else {
                    // Black pieces at the bottom, white pieces at the top
                    if (row == 7 || row == 8) {
                        switch (col) {
                            case 1:
                            case 8:
                                System.out.print(BLACK_ROOK);
                                break;
                            case 2:
                            case 7:
                                System.out.print(BLACK_KNIGHT);
                                break;
                            case 3:
                            case 6:
                                System.out.print(BLACK_BISHOP);
                                break;
                            case 4:
                                System.out.print(BLACK_QUEEN);
                                break;
                            case 5:
                                System.out.print(BLACK_KING);
                                break;
                        }
                    } else if (row == 1 || row == 2) {
                        switch (col) {
                            case 1:
                            case 8:
                                System.out.print(WHITE_ROOK);
                                break;
                            case 2:
                            case 7:
                                System.out.print(WHITE_KNIGHT);
                                break;
                            case 3:
                            case 6:
                                System.out.print(WHITE_BISHOP);
                                break;
                            case 4:
                                System.out.print(WHITE_QUEEN);
                                break;
                            case 5:
                                System.out.print(WHITE_KING);
                                break;
                        }
                    } else {
                        System.out.print(EMPTY);
                    }
                }
            }
            System.out.println("\u001B[49m");
        }
    }
}
