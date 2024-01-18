package chess;

import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor color;
    ChessPiece.PieceType type;

    // to check eligablitiy for castling
    boolean hasMoved;



    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType pieceType) {
        color = pieceColor;
        type = pieceType;
        hasMoved = false;
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
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }
    public String toString()
    {
        String print;
        if (getTeamColor() == ChessGame.TeamColor.WHITE)
        {
             print = switch (type)
            {
                case KING -> "\u265A";
                case PAWN -> "\u265F";
                case ROOK -> "\u265C";
                case QUEEN -> "\u265B";
                case BISHOP -> "\u265D";
                case KNIGHT -> "\u265E";
            };
        } else
        {
            print = switch (type)
            {
                case KING -> "\u2654";
                case PAWN -> "\u2659";
                case ROOK -> "\u2656";
                case QUEEN -> "\u2655";
                case BISHOP -> "\u2657";
                case KNIGHT -> "\u2658";
            };
        }
        return print;
    }

    // checking pieces on diagonals given a distance
    // checking pieces on the straights given a distance
    // checking pieces on the kights jumps

    public void checkDiagonals(ChessBoard board,ChessPosition position){
        // 1,1


        // 1,-1
        // -1,-1
        // -1,1
    }

    public void checkStraights(ChessBoard board,ChessPosition position){

    }
}
