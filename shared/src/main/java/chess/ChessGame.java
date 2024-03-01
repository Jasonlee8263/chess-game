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
        ChessBoard newBoard = curboard.copy();
        ChessPiece startPiece = newBoard.getPiece(move.getStartPosition());

        if (startPiece == null) {
            throw new InvalidMoveException("Invalid Move: There is no piece at the start position.");
        }

        if (startPiece.getPieceType() == ChessPiece.PieceType.PAWN) {
            if ((startPiece.getTeamColor() == TeamColor.WHITE && move.getEndPosition().getRow() == 8) ||
                    (startPiece.getTeamColor() == TeamColor.BLACK && move.getEndPosition().getRow() == 1)) {
                // Handle pawn promotion
                ChessPiece newPiece = new ChessPiece(startPiece.getTeamColor(), move.getPromotionPiece());
                newBoard.addPiece(move.getEndPosition(), newPiece);
                newBoard.addPiece(move.getStartPosition(), null);
            } else {
                // Handle regular move
                if (!validMoves(move.getStartPosition()).contains(move)) {
                    throw new InvalidMoveException("Invalid Move: This move is not valid.");
                }
                newBoard.addPiece(move.getEndPosition(), newBoard.getPiece(move.getStartPosition()));
                newBoard.addPiece(move.getStartPosition(), null);
            }
        } else {
            // Handle moves for non-pawn pieces
            if (!validMoves(move.getStartPosition()).contains(move)) {
                throw new InvalidMoveException("Invalid Move: This move is not valid.");
            }
            newBoard.addPiece(move.getEndPosition(), newBoard.getPiece(move.getStartPosition()));
            newBoard.addPiece(move.getStartPosition(), null);
        }

        TeamColor nextTurn = (curTurn == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;
        ChessPiece endPiece = newBoard.getPiece(move.getEndPosition());

        if (endPiece == null || endPiece.getTeamColor() != curTurn) {
            throw new InvalidMoveException("Invalid Move: The piece at the end position does not belong to the current player.");
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
