package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import model.GameData;
import model.ResponseException;
import model.requestAndResult.*;
import webSocketMessages.userCommands.Leave;
import websocket.GameHandler;
import websocket.WebsocketFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static ui.EscapeSequences.*;

public class ChessClient {
    private final ServerFacade serverFacade;
    private final String serverUrl;
    private WebsocketFacade ws;
    private boolean loginState = false;
    private boolean joinState = false;
    private GameHandler gameHandler;
    private ChessBoard chessBoard;
    private String authToken;
    private Integer gameID;


    public ChessClient(String serverUrl) throws ResponseException {
        serverFacade = new ServerFacade(serverUrl,gameHandler);
        this.serverUrl = serverUrl;
//        this.gameHandler = gameHandler;
        chessBoard = new ChessBoard();
        chessBoard.resetBoard();
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
                case "redraw" -> redraw();
                case "leave" -> leave();
                case "makemove" -> makeMove();
                case "resign" -> resign();
                case "highlight" -> highLight();
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
            System.out.println(String.format("GameNumber: %s, Gamename: %s, WhitePlayer: %s, BlackPlayer: %s", game.gameID(), game.gameName(), game.whiteUsername(), game.blackUsername()));
        }
        return "";
    }
    public String joinGame(String... params) throws ResponseException {
        if(params.length==2){
            JoinGameRequest req = new JoinGameRequest(params[0],Integer.parseInt(params[1]));
            gameID = Integer.parseInt(params[1]);
            serverFacade.joinGame(req);
        }
        String playerColor = params[0];
        String oppositeColor = Objects.equals(playerColor, "white") ? "black" : "white";
        drawBoard(playerColor,chessBoard);
        System.out.println();
        joinState = true;
        return "Join Success!";
    }

    public String joinAsObserver(String... params) throws ResponseException {
        if(params.length==1) {
            JoinGameRequest req = new JoinGameRequest(null,Integer.parseInt(params[0]));
            serverFacade.joinGame(req);
        }
        drawBoard(null,chessBoard);
        System.out.println();
        joinState = true;
        return "Joined as observer";
    }

    public String redraw(){
        return drawBoard();
    }

    public String leave() throws ResponseException {
        ws.leaveGame(new Leave(authToken,gameID));
        return;
    }

    public String makeMove() {
        return null;
    }

    public String resign() {
        return null;
    }

    public String highLight() {
        return null;
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
        else if(joinState){
            return """
                    - Redraw - Redraw Chess Board
                    - Leave
                    - MakeMove
                    - Resign
                    - Highlight - Highlight Legal Moves
                    - Help - possible commands
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

    private void drawBoard(String playerColor,ChessBoard chessBoard) {
        boolean whiteAtBottom = (playerColor == null || Objects.equals(playerColor,"") || Objects.equals(playerColor, "white"));

        if (whiteAtBottom) {
            System.out.print("   ");
            for (int col = 8; col >= 1; col--) {
                System.out.print(" " + col + " ");
            }
            System.out.println();
            // Print row indices from 1 to 8
            for (int row = 1; row <= 8; row++) {
                // Print row index on the right side
                System.out.print(row + " ");

                for (int col = 1; col <= 8; col++) {
                    // Alternate colors for the chessboard squares
                    if ((row + col) % 2 == 0) {
                        System.out.print(SET_BG_COLOR_BLACK);
                    } else {
                        System.out.print(SET_BG_COLOR_WHITE);
                    }

                    ChessPosition chessPosition = new ChessPosition(row,col);
                    ChessPiece chessPiece = chessBoard.getPiece(chessPosition);
                    // Print the chess piece or empty square
                    if (chessPiece!=null && chessPiece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
                        getPieceType(chessPiece, WHITE_KNIGHT, WHITE_QUEEN, WHITE_PAWN, WHITE_ROOK, WHITE_BISHOP, WHITE_KING);
                    }
                    else if(chessPiece!=null && chessPiece.getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
                        getPieceType(chessPiece, BLACK_KNIGHT, BLACK_QUEEN, BLACK_PAWN, BLACK_ROOK, BLACK_BISHOP, BLACK_KING);
                    }
                    else{
                        System.out.print(EMPTY);
                    }
                }
                // Print row index at the bottom
                System.out.println(RESET_BG_COLOR + " " + row);
            }
            System.out.print("   ");
            for (int col = 8; col >= 1; col--) {
                System.out.print(" " + col + " ");
            }
            System.out.println();
        } else {
            System.out.print("   ");
            for (int col = 1; col <= 8; col++) {
                System.out.print(" " + col + " ");
            }
            System.out.println();
            // Print row indices from 8 to 1
            for (int row = 8; row >= 1; row--) {
                // Print row index on the right side
                System.out.print(row + " ");

                for (int col = 8; col >= 1; col--) {
                    // Alternate colors for the chessboard squares
                    if ((row + col) % 2 == 0) {
                        System.out.print(SET_BG_COLOR_BLACK);
                    } else {
                        System.out.print(SET_BG_COLOR_WHITE);
                    }

                    // Print the chess piece or empty square
                    ChessPosition chessPosition = new ChessPosition(row,col);
                    ChessPiece chessPiece = chessBoard.getPiece(chessPosition);
                    // Print the chess piece or empty square
                    if (chessPiece!=null && chessPiece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
                        getPieceType(chessPiece, WHITE_KNIGHT, WHITE_QUEEN, WHITE_PAWN, WHITE_ROOK, WHITE_BISHOP, WHITE_KING);
                    }
                    else if(chessPiece!=null && chessPiece.getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
                        getPieceType(chessPiece, BLACK_KNIGHT, BLACK_QUEEN, BLACK_PAWN, BLACK_ROOK, BLACK_BISHOP, BLACK_KING);
                    }
                    else{
                        System.out.print(EMPTY);
                    }
                }
                // Print row index at the bottom
                System.out.println(RESET_BG_COLOR + " " + row);
            }
            System.out.print("   ");
            for (int col = 1; col <= 8; col++) {
                System.out.print(" " + col + " ");
            }
            System.out.println();
        }
    }

    private void getPieceType(ChessPiece chessPiece, String knight, String queen, String pawn, String rook, String bishop, String king) {
        switch (chessPiece.getPieceType()){
            case KNIGHT -> System.out.print(knight);
            case QUEEN -> System.out.print(queen);
            case PAWN -> System.out.print(pawn);
            case ROOK -> System.out.print(rook);
            case BISHOP -> System.out.print(bishop);
            case KING -> System.out.print(king);
            case null, default -> System.out.print(EMPTY);
        }
    }
}
