package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove
{
    private ChessPosition start;
    private ChessPosition end;
    private ChessPiece.PieceType promotionPiece;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece)
    {
        start =startPosition;
        end = endPosition;
        this.promotionPiece = promotionPiece;
    }
    public ChessMove(ChessPosition startPosition, ChessPosition endPosition)
    {
        start =startPosition;
        end = endPosition;
        this.promotionPiece = null;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition()
    {
        return start;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition()
    {

        return end;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessMove chessMove = (ChessMove) o;
        return Objects.equals(start, chessMove.start) && Objects.equals(end, chessMove.end) && promotionPiece == chessMove.promotionPiece;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(start, end, promotionPiece);
    }

    public void setPromotionPiece(ChessPiece.PieceType promotionPiece)
    {
        this.promotionPiece = promotionPiece;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece()
    {
        return promotionPiece;
    }

    @Override
    public String toString()
    {
        return "{" + start +
                "-> "+end +", "+ promotionPiece +
                '}';
    }
};
