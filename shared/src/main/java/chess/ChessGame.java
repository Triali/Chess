package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard currentGame;
    private TeamColor currentTurn;

    private ChessPosition blackKing;
    private ChessPosition whiteKing;
    private ChessMove lastMove;

    public ChessGame() {
        currentGame = new ChessBoard();
        currentTurn = TeamColor.WHITE;
        blackKing = new ChessPosition(8,5);
        whiteKing = new ChessPosition(1,5);

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentTurn;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(currentGame, chessGame.currentGame) && currentTurn == chessGame.currentTurn && Objects.equals(lastMove, chessGame.lastMove);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(currentGame, currentTurn, lastMove);
    }

    @Override
    public String toString()
    {
        return "ChessGame{" +
                "currentGame=" + currentGame +
                ", currentTurn=" + currentTurn +
                ", lastMove=" + lastMove +
                '}';
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        currentTurn = team;
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
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece piece = currentGame.getPiece(move.getStartPosition());
        // check if it is a valid move
        currentGame.removePiece(move.getStartPosition());
        currentGame.addPiece(move.getEndPosition(),piece);
        if(piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == TeamColor.BLACK ){
            blackKing = move.getEndPosition();
        }else if(piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == TeamColor.WHITE ){
            whiteKing = move.getEndPosition();
        }
    }

    public ChessPosition getBlackKing()
    {
        return blackKing;
    }

    public void setBlackKing(ChessPosition blackKing)
    {
        this.blackKing = blackKing;
    }

    public void setWhiteKing(ChessPosition whiteKing)
    {
        this.whiteKing = whiteKing;
    }

    public ChessPosition getWhiteKing()
    {
        return whiteKing;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // no valid moves and in check
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // no valid moves and not in check
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        currentGame = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return currentGame;
    }

    /**
     * returns enemy chess pieces in a given direction and range
     *
     * @param startPos the position of the king
     *
     * @param vector the direction vector being checked
     *
     * @param spaces the number of spaces to check for
     *
     */
    public ChessPiece.PieceType CheckForAttack(ChessPosition startPos, int[] vector, int spaces)
    {
        ChessPosition pos = startPos;
        TeamColor color = currentGame.getPiece(pos).getTeamColor();
        pos = pos.addPosition(vector);
        int count = 0;
        while (pos.getColumn() <= 8 && pos.getColumn() >= 1 && pos.getRow() <= 8 && pos.getRow() >= 1 && count < spaces)
        {
            ChessPiece currPiece = currentGame.getPiece(pos);
            if (currPiece != null)
            {
                // same color
                if (currPiece.getTeamColor() == color)
                {
                    return null;
                }
                // opposite color
                else
                {
                    return currPiece.getPieceType();
                }
            } else
            {
                pos = pos.addPosition(vector);
            }
            count++;

        }
        return null;

    }
}
