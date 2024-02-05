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
        Collection<ChessMove> moves = new HashSet<>();
        switch (board.getPiece(myPosition).getPieceType()) {
            case KING:
                int [][] kingDirection = {{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1}};
                for(int[]direction:kingDirection) {
                    int row = myPosition.getRow() + direction[0];
                    int col = myPosition.getColumn() + direction[1];
                    ChessPosition newPosition = new ChessPosition(row, col);
                    if (board.isValidPosition(row, col)) {
                        if(board.getPiece(newPosition)==null) {
                            moves.add(new ChessMove(myPosition,newPosition,null));
                        }
                        else if (board.getPiece(myPosition).pieceColor != board.getPiece(newPosition).pieceColor) {
                            moves.add(new ChessMove(myPosition, newPosition, null));
                        }
                    }
                }
                break;
            case QUEEN:
                int [][] queenDirections = {{1,-1},{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1}};
                calc(board, myPosition, moves, queenDirections);
                break;
            case BISHOP:
                int [][] bishopDirections = {{1,1},{1,-1},{-1,1},{-1,-1}};
                calc(board, myPosition, moves, bishopDirections);
                break;
            case KNIGHT:
                int [][] knightDirection = {{2,-1},{2,1},{1,2},{-1,2},{-2,1},{-2,-1},{-1,-2},{1,-2}};
                for(int[]direction:knightDirection) {
                    int row = myPosition.getRow() + direction[0];
                    int col = myPosition.getColumn() + direction[1];
                    ChessPosition newPosition = new ChessPosition(row, col);
                    if (board.isValidPosition(row, col)) {
                        if(board.getPiece(newPosition)==null) {
                            moves.add(new ChessMove(myPosition,newPosition,null));
                        }
                        else if (board.getPiece(myPosition).pieceColor != board.getPiece(newPosition).pieceColor) {
                            moves.add(new ChessMove(myPosition, newPosition, null));
                        }
                    }
                }
                break;
            case ROOK:
                int [][] rookDirections = {{1,0},{0,1},{-1,0},{0,-1}};
                calc(board, myPosition, moves, rookDirections);
                break;
            case PAWN:
                if(board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE){
                    int [][] whiteDiagonals = {{1,-1},{1,1}};
                    ChessPosition newPosition1 = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
                    if(board.isValidPosition(newPosition1.getRow(), newPosition1.getColumn())){
                        if(board.getPiece(newPosition1)==null) {
                            if(newPosition1.getRow() == 8) {
                                moves.add(new ChessMove(myPosition,newPosition1,PieceType.ROOK));
                                moves.add(new ChessMove(myPosition,newPosition1,PieceType.KNIGHT));
                                moves.add(new ChessMove(myPosition,newPosition1,PieceType.BISHOP));
                                moves.add(new ChessMove(myPosition,newPosition1,PieceType.QUEEN));
                            }
                            else{
                                moves.add(new ChessMove(myPosition,newPosition1,null));
                            }
                        }
                    }
                    if(myPosition.getRow()==2){
                        ChessPosition newPosition2 = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn());
                        if(board.isValidPosition(newPosition2.getRow(), newPosition2.getColumn())){
                            if(board.getPiece(newPosition1)==null && board.getPiece(newPosition2)==null) {
                                moves.add(new ChessMove(myPosition,newPosition2,null));
                            }
                        }
                    }
                    for (int[] diagonal:whiteDiagonals) {
                        int row = myPosition.getRow()+diagonal[0];
                        int col = myPosition.getColumn()+diagonal[1];
                        if(board.isValidPosition(row,col)){
                            ChessPosition newPosition3 = new ChessPosition(row,col);
                            if(board.getPiece(newPosition3)!=null && board.getPiece(myPosition).getTeamColor()!=board.getPiece(newPosition3).getTeamColor()) {
                                if(newPosition3.getRow() == 8) {
                                    moves.add(new ChessMove(myPosition,newPosition3,PieceType.ROOK));
                                    moves.add(new ChessMove(myPosition,newPosition3,PieceType.KNIGHT));
                                    moves.add(new ChessMove(myPosition,newPosition3,PieceType.BISHOP));
                                    moves.add(new ChessMove(myPosition,newPosition3,PieceType.QUEEN));
                                }
                                else {
                                    moves.add(new ChessMove(myPosition,newPosition3,null));
                                }
                            }
                        }
                    }
                }
                else {
                    int [][] blackDiagonals = {{-1,-1},{-1,1}};
                    ChessPosition newPosition1 = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
                    if(board.isValidPosition(newPosition1.getRow(), newPosition1.getColumn())){
                        if(board.getPiece(newPosition1)==null) {
                            if(newPosition1.getRow() == 1) {
                                moves.add(new ChessMove(myPosition,newPosition1,PieceType.ROOK));
                                moves.add(new ChessMove(myPosition,newPosition1,PieceType.KNIGHT));
                                moves.add(new ChessMove(myPosition,newPosition1,PieceType.BISHOP));
                                moves.add(new ChessMove(myPosition,newPosition1,PieceType.QUEEN));
                            }
                            else{
                                moves.add(new ChessMove(myPosition,newPosition1,null));
                            }
                        }
                    }
                    if(myPosition.getRow()==7){
                        ChessPosition newPosition2 = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn());
                        if(board.isValidPosition(newPosition2.getRow(), newPosition2.getColumn())){
                            if(board.getPiece(newPosition1)==null && board.getPiece(newPosition2)==null) {
                                moves.add(new ChessMove(myPosition,newPosition2,null));
                            }
                        }
                    }
                    for (int[] diagonal:blackDiagonals) {
                        int row = myPosition.getRow()+diagonal[0];
                        int col = myPosition.getColumn()+diagonal[1];
                        if(board.isValidPosition(row,col)){
                            ChessPosition newPosition3 = new ChessPosition(row,col);
                            if(board.getPiece(newPosition3)!=null && board.getPiece(myPosition).getTeamColor()!=board.getPiece(newPosition3).getTeamColor()) {
                                if(newPosition3.getRow() == 1) {
                                    moves.add(new ChessMove(myPosition,newPosition3,PieceType.ROOK));
                                    moves.add(new ChessMove(myPosition,newPosition3,PieceType.KNIGHT));
                                    moves.add(new ChessMove(myPosition,newPosition3,PieceType.BISHOP));
                                    moves.add(new ChessMove(myPosition,newPosition3,PieceType.QUEEN));
                                }
                                else {
                                    moves.add(new ChessMove(myPosition,newPosition3,null));
                                }
                            }
                        }
                    }
                }
                break;
        }
        return moves;
    }

    private void calc(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves, int[][] directions) {
        for (int[] direction: directions) {
            int row = myPosition.getRow() + direction[0];
            int col = myPosition.getColumn() + direction[1];
            while (board.isValidPosition(row, col)) {
                ChessPosition newPosition = new ChessPosition(row, col);
                if(board.getPiece(newPosition) ==null){
                    moves.add(new ChessMove(myPosition, newPosition, null));
                }
                else {
                    if(board.getPiece(myPosition).getTeamColor()!=board.getPiece(newPosition).getTeamColor()) {
                        moves.add(new ChessMove(myPosition,newPosition,null));
                    }
                    break;
                }
                row += direction[0];
                col += direction[1];
            }
        }
    }
}
