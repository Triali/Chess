package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;


/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece
{
    private ChessGame.TeamColor color;

    public void setType(PieceType type)
    {
        this.type = type;
    }

    private ChessPiece.PieceType type;

    // to check eligablitiy for castling
    private boolean hasMoved;


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
        HashSet<ChessMove> possibleMoves = new HashSet();
        switch (type)
        {
            case KING:
                possibleMoves.addAll(checkDiagonals(board, myPosition, 1));
                possibleMoves.addAll(checkStraights(board, myPosition, 1));
                break;
            case PAWN:
                possibleMoves.addAll(checkPawn(board, myPosition));
                break;
            case ROOK:
                possibleMoves.addAll(checkStraights(board, myPosition, 8));
                break;
            case QUEEN:
                possibleMoves.addAll(checkDiagonals(board, myPosition, 8));
                possibleMoves.addAll(checkStraights(board, myPosition, 8));
                break;
            case BISHOP:
                possibleMoves.addAll(checkDiagonals(board, myPosition, 8));
                break;
            case KNIGHT:
                possibleMoves.addAll(checkJumps(board, myPosition));
                break;
        }
        return possibleMoves;
    }

    boolean isOpposite(ChessGame.TeamColor otherColor)
    {
        return (color != otherColor);
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
//            case KING -> "K";
//                case PAWN -> "P";
//                case ROOK -> "R";
//                case QUEEN -> "Q";
//                case BISHOP -> "B";
//                case KNIGHT -> "N";
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
//                case KING -> "k";
//                case PAWN -> "p";
//                case ROOK -> "r";
//                case QUEEN -> "q";
//                case BISHOP -> "b";
//                case KNIGHT -> "n";
            };
        }
        return print;
    }


    // checking pieces on diagonals given a distance
    // checking pieces on the straights given a distance
    // checking pieces on the kights jumps

    private Collection<ChessMove> checkDiagonals(ChessBoard board, ChessPosition startPos, int spaces)
    {
        HashSet<ChessMove> possibleMoves = new HashSet();
        // 1,1

        //stop when it runs into a piece or edge of board. if piece is opposide color, that space is valid
        int[] vector = {1, 1};
        for (int i = 0; i < 4; i++)
        {
            possibleMoves.addAll(checkDirection(board, startPos, vector, spaces));
            vector = rotate90(vector);
        }

        return possibleMoves;
    }

    private Collection<ChessMove> checkJumps(ChessBoard board, ChessPosition startPos)
    {
        int spaces = 1;
        HashSet<ChessMove> possibleMoves = new HashSet();
        // 1,1

        //stop when it runs into a piece or edge of board. if piece is opposide color, that space is valid
        int[] vector = {2, 1};
        for (int i = 0; i < 4; i++)
        {
            possibleMoves.addAll(checkDirection(board, startPos, vector, spaces));
            vector = rotate90(vector);
        }
        int[] vector2 = {1, 2};
        for (int i = 0; i < 4; i++)
        {
            possibleMoves.addAll(checkDirection(board, startPos, vector2, spaces));
            vector2 = rotate90(vector2);
        }

        return possibleMoves;
    }

    private Collection<ChessMove> checkStraights(ChessBoard board, ChessPosition position, int spaces)
    {
        HashSet<ChessMove> possibleMoves = new HashSet();
        // 1,1

        //stop when it runs into a piece or edge of board. if piece is opposide color, that space is valid
        int[] vector = {1, 0};
        for (int i = 0; i < 4; i++)
        {
            possibleMoves.addAll(checkDirection(board, position, vector, spaces));
            vector = rotate90(vector);
        }

        return possibleMoves;


    }

    private static int[] rotate90(int[] x)
    {
        int a = x[0];
        int b = x[1];
        x[0] = b * -1;
        x[1] = a;
        return x;
    }

    private Collection<ChessMove> checkDirection(ChessBoard board, ChessPosition startPos, int[] vector, int spaces)
    {
        ChessPosition pos = startPos;
        HashSet<ChessMove> possibleMoves = new HashSet();
        pos = pos.addPosition(vector);
        int count = 0;
        while (pos.getColumn() <= 8 && pos.getColumn() >= 1 && pos.getRow() <= 8 && pos.getRow() >= 1 && count < spaces)
        {
            ChessPiece currPiece = board.getPiece(pos);
            if (currPiece != null)
            {
                // same color
                if (currPiece.getTeamColor() == color)
                {
                    return possibleMoves;
                }
                // opposite color
                else
                {
                    possibleMoves.add(new ChessMove(startPos, pos));
                    return possibleMoves;

                }
            } else
            {
                possibleMoves.add(new ChessMove(startPos, pos));
                pos = pos.addPosition(vector);
            }
            count++;


        }
        return possibleMoves;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return hasMoved == that.hasMoved && color == that.color && type == that.type;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(color, type, hasMoved);
    }

    // checking the pawns potential moves:
    //   diagonal if piece of opposite color is there
    //   straight if no piece is there
    private Collection<ChessMove> checkPawn(ChessBoard board, ChessPosition position)
    {
        HashSet<ChessMove> possibleMoves = new HashSet();
        // Check color
        int[] diagonal;
        int[] ahead;
        int backRow;
        int startRow;
        if (color == ChessGame.TeamColor.BLACK)
        {
            diagonal = new int[]{-1, 1};
            ahead = new int[]{-1, 0};
            backRow = 2;
            startRow = 7;
        } else
        {
            diagonal = new int[]{1, -1};
            ahead = new int[]{1, 0};
            backRow = 7;
            startRow = 2;
        }
        // diagonals
        for (int i = 0; i < 2; i++)
        {
            ChessPosition next = position.addPosition(diagonal);
            if (board.getPiece(next)!=null && isOpposite(board.getPiece(next).getTeamColor()))
            {
                if (position.getRow() == backRow)
                {
                    possibleMoves.addAll(PawnPromote(position, next));
                } else
                {
                    possibleMoves.add(new ChessMove(position, next));
                }
            }
            diagonal = rotate90(diagonal);
        }
//        ahead
        ChessPosition next = position.addPosition(ahead);
        if (board.getPiece(next) == null)
        {
            if (position.getRow() == backRow)
            {
                possibleMoves.addAll(PawnPromote(position, next));
            } else
            {
                if (position.getRow() == startRow && board.getPiece(next.addPosition(ahead)) == null)
                {
                    possibleMoves.add(new ChessMove(position, next.addPosition(ahead)));
                }
                possibleMoves.add(new ChessMove(position, next));

            }
        }


        return possibleMoves;
    }

    private Collection<ChessMove> PawnPromote(ChessPosition start, ChessPosition end)
    {
        HashSet<ChessMove> possibleMoves = new HashSet();
        possibleMoves.add(new ChessMove(start, end, PieceType.BISHOP));
        possibleMoves.add(new ChessMove(start, end, PieceType.QUEEN));
        possibleMoves.add(new ChessMove(start, end, PieceType.ROOK));
        possibleMoves.add(new ChessMove(start, end, PieceType.KNIGHT));
        return possibleMoves;
    }

    private Collection<ChessMove> CheckAttacks(ChessPosition start){
        HashSet<ChessMove> possibleMoves = new HashSet();


        return possibleMoves;
    }



}
