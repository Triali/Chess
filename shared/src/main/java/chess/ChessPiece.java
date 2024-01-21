package chess;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece
{
    ChessGame.TeamColor color;
    ChessPiece.PieceType type;

    // to check eligablitiy for castling
    boolean hasMoved;


    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType pieceType)
    {
        color = pieceColor;
        type = pieceType;
        hasMoved = false;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType
    {
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
    public ChessGame.TeamColor getTeamColor()
    {
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType()
    {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition)
    {
        throw new RuntimeException("Not implemented");
    }

    @Override
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

    public Collection<ChessMove> checkDiagonals(ChessBoard board, ChessPosition startPos)
    {
        ArrayList<ChessMove> possibleMoves = null;
        // 1,1
        ChessPosition pos = startPos;
        //stop when it runs into a piece or edge of board. if piece is opposide color, that space is valid
        int[] vector = {1, 1};
        ChessGame.TeamColor pieceColor = board.getPiece(startPos).getTeamColor();


        pos = pos.addPosition(vector);


        // 1,-1
        // -1,-1
        // -1,1
        return possibleMoves;
    }

    public void checkStraights(ChessBoard board, ChessPosition position)
    {


    }

    public static int[] rotate90(int[] x)
    {
        int a = x[0];
        int b = x[1];
        x[0] = b * -1;
        x[1] = a;
        return x;
    }

    public static int[] scalar(int[] x, int scale)
    {
        x[0] = x[0] * scale;
        x[1] = x[1] * scale;
        return x;
    }

    public Collection<ChessMove> checkDirection(ChessBoard board,ChessPosition startPos,int[] vector)
    {
        ChessPosition pos = startPos;
        ArrayList<ChessMove> possibleMoves = new ArrayList();
        pos = pos.addPosition(vector);
        while(pos.getColumn() <=8 && pos.getColumn()>= 1&& pos.getRow() <=8 && pos.getRow()>= 1){
            ChessPiece currPiece = board.getPiece(pos);
            if(currPiece != null)
            {
                // same color
                if(currPiece.getTeamColor() == color){
                    return possibleMoves;
                }
                // opposite color
                else{
                    possibleMoves.add(new ChessMove(startPos,pos));
                    return possibleMoves;

                }
            }
            else {
                possibleMoves.add(new ChessMove(startPos,pos));
                pos = pos.addPosition(vector);
            }



        }
        return possibleMoves;
    }


}
