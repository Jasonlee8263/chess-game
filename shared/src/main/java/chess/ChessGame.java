package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
private TeamColor curTurn;
private ChessBoard curboard;
    public ChessGame() {
        //Default
        curTurn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return curTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        curTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */

    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> moves = new HashSet<>();
        Collection<ChessMove> valMoves = new HashSet<>();
        ChessPiece piece = curboard.getPiece(startPosition);
        if(piece != null) {
            moves = piece.pieceMoves(curboard,startPosition);
            for(ChessMove move:moves) {
                ChessBoard originBoard = curboard.copy();
                ChessBoard newBoard = curboard.copy();
                newBoard.addPiece(move.getEndPosition(),newBoard.getPiece(startPosition));
                newBoard.addPiece(move.getStartPosition(),null);
                curboard = newBoard;
                if(!isInCheck(newBoard.getPiece(move.getEndPosition()).getTeamColor())){
                    valMoves.add(move);
                }
                curboard = originBoard;
            }
        }
        return valMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */

    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessBoard newBoard = new ChessBoard();
        ChessBoard originalBoard = new ChessBoard();
        newBoard = curboard.copy();
        ChessPiece startPiece = newBoard.getPiece(move.getStartPosition());
        ChessPiece originalPosPiece = newBoard.getPiece(move.getEndPosition());
        if(startPiece!=null && startPiece.getPieceType() == ChessPiece.PieceType.PAWN && startPiece.getTeamColor()==TeamColor.WHITE && move.getEndPosition().getRow()==8){
            ChessPiece newPiece = new ChessPiece(newBoard.getPiece(move.getStartPosition()).getTeamColor(),move.getPromotionPiece());
            newBoard.addPiece(move.getEndPosition(),newPiece);
            newBoard.addPiece(move.getStartPosition(),null);
        }
        else if(startPiece!=null && startPiece.getPieceType() == ChessPiece.PieceType.PAWN && startPiece.getTeamColor()==TeamColor.BLACK && move.getEndPosition().getRow()==1){
            ChessPiece newPiece = new ChessPiece(newBoard.getPiece(move.getStartPosition()).getTeamColor(),move.getPromotionPiece());
            newBoard.addPiece(move.getEndPosition(),newPiece);
            newBoard.addPiece(move.getStartPosition(),null);
        }
        else if(validMoves(move.getStartPosition()).contains(move)){
            newBoard.addPiece(move.getEndPosition(),newBoard.getPiece(move.getStartPosition()));
            newBoard.addPiece(move.getStartPosition(),null);
        }
        else {
            throw new InvalidMoveException("Invalid Move");
        }
        TeamColor nextTurn = (curTurn == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        ChessPiece simulate = newBoard.getPiece(move.getEndPosition());
        if(newBoard.getPiece(move.getEndPosition()).getTeamColor()!=curTurn){
            throw new InvalidMoveException("Invalid Move");
        }
        curboard = newBoard;
        curTurn = nextTurn;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = curboard.findKing(teamColor);
        TeamColor opposingTeam = (teamColor == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        for(ChessPosition position:curboard.getAllPositions(opposingTeam)) {
            if(curboard.getPiece(position).pieceMoves(curboard,position).stream().anyMatch(chessMove -> chessMove.getEndPosition().equals(kingPosition))){
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if(isInCheck(teamColor) && isInStalemate(teamColor)) {
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if (validMoves(curboard.findKing(teamColor)).isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        curboard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return curboard;
    }
}
