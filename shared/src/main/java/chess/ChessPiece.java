package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
//        throw new RuntimeException("Not implemented");
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
//        throw new RuntimeException("Not implemented");
        Collection<ChessMove> moves = new HashSet<>();
        public ChessMove kingMoves(ChessBoard board) {
            int [][] directions = {{1,0},{1,1},{1,-1},{0,1},{1,1},{1,-1},{-1,0},{-1,1},{-1,-1},{0,-1},{-1,1},{-1,-1}};
            for (int[] direction: directions) {
                int row = myPosition.getRow() + direction[0];
                int col = myPosition.getColumn() + direction[1];
                while (board.isVaildPosition(row, col) && board.isTaken(row,col)) {
                    ChessPosition newPosition = new ChessPosition(row, col);
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
            return moves;
        }
        public ChessMove queenMoves(ChessBoard board) {
            int [][] directions = {{1,0},{1,1},{1,-1},{0,1},{1,1},{1,-1},{-1,0},{-1,1},{-1,-1},{0,-1},{-1,1},{-1,-1}};
            for (int[] direction: directions) {
                int row = myPosition.getRow() + direction[0];
                int col = myPosition.getColumn() + direction[1];
                while (board.isVaildPosition(row, col)) {
                    ChessPosition newPosition = new ChessPosition(row, col);
                    moves.add(new ChessMove(myPosition, newPosition, null));
                    row += direction[0];
                    col += direction[1];
                }
            }
            return moves;
        }
        public Collection<ChessMove> bishopMoves(ChessBoard board) {
            int [][] directions = {{1,1},{1,-1},{-1,1},{-1,-1}};
            for (int[] direction: directions) {
                int row = myPosition.getRow() + direction[0];
                int col = myPosition.getColumn() + direction[1];
                while (board.isVaildPosition(row,col)){
                    ChessPosition newPosition = new ChessPosition(row,col);
                    moves.add(new ChessMove(myPosition,newPosition,null));
                    row += direction[0];
                    col += direction[1];
                }
            }
            return moves;
        }

        public int[] knightMoves(ChessBoard board) {
            int [][] directions = {{2,1},{2,-1},{1,2},{-1,2},{-2,1},{-2,-1},{-1,-2},{1,-2}};
            for (int[] direction: directions) {
                int row = myPosition.getRow() + direction[0];
                int col = myPosition.getColumn() + direction[1];
                while (board.isVaildPosition(row,col)) {
                    ChessPosition newPosition = new ChessPosition(row,col);
                    moves.add(new ChessMove(myPosition,newPosition,null));
                }
            }
            return moves;
        }
        public ChessMove rookMoves(ChessBoard board) {
            int [][] directions = {{1,0},{0,1},{-1,0},{0,-1}};
            for (int[] direction: directions) {
                int row = myPosition.getRow() + direction[0];
                int col = myPosition.getColumn() + direction[1];
                while (board.isVaildPosition(row, col)) {
                    ChessPosition newPosition = new ChessPosition(row, col);
                    moves.add(new ChessMove(myPosition, newPosition, null));
                    row += direction[0];
                    col += direction[1];
                }
            }
            return moves;
        }
        public ChessMove pawnMoves(ChessBoard board) {

        }
        switch (type) {
            case KING:

            case QUEEN:
            case BISHOP:
            case KNIGHT:
            case ROOK:
            case PAWN:
        }

        return moves;
    }
}
