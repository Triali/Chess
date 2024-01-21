package chess;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove
{
    ChessPosition start;
    ChessPosition end;
    ChessPiece.PieceType promotionPiece;

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
        return start.toString() + "-> " + end.toString() + ((this.promotionPiece == null)?(""):(": " + this.promotionPiece));

    }

};
