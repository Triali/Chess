package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame
{
    private ChessBoard currentGame;
    private TeamColor currentTurn;
    private ChessPosition blackKing;
    private ChessPosition whiteKing;

    public ChessGame()
    {
        currentGame = new ChessBoard();
        currentTurn = TeamColor.WHITE;
    }

    public ChessGame(ChessBoard board, TeamColor currentTurn)
    {
        currentGame = board;
        this.currentTurn = currentTurn;

        findKings();
    }

    /**
     * @return Which team's turn it is
     */
//    public TeamColor getTeamTurn()
//    {
//        return currentTurn;
//    }
    private void changeTeamTurn()
    {
        currentTurn = (currentTurn == TeamColor.WHITE) ? (TeamColor.BLACK) : (TeamColor.WHITE);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(currentGame, chessGame.currentGame) && (currentTurn == chessGame.currentTurn);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(currentGame, currentTurn, blackKing, whiteKing);
    }

    @Override
    public String toString()
    {
        return "ChessGame{" + "currentGame=" + currentGame + ", currentTurn=" + currentTurn + ", lastMove="+ '}';
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team)
    {
        currentTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor
    {
        WHITE, BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition)
    {
//        System.out.println("running validMoves");
        Collection<ChessMove> validMoves = new HashSet<>();
        Collection<ChessMove> possibleMoves = new HashSet<>();
        ChessPiece piece = currentGame.getPiece(startPosition);
        if (piece == null)
        {
            return validMoves;
        }
//        System.out.println(piece.pieceMoves(currentGame,startPosition));
        possibleMoves.addAll(piece.pieceMoves(currentGame, startPosition));
//        System.out.println(possibleMoves);
        for (ChessMove move : possibleMoves)
        {
//            System.out.println(move +" "+tryMove(move));
            if (tryMove(move))
            {
                validMoves.add(move);
            }
        }


        return validMoves;
    }


    private Collection<ChessMove> allValidMoves(TeamColor color)
    {
        Collection<ChessMove> allValidMoves = new HashSet<>();
        Iterator<Map.Entry<ChessPosition, ChessPiece>> it = currentGame.getPieces().entrySet().iterator();
        for (int row = 8; row >= 1; row--)
        {
            for (int col = 1; col <= 8; col++)
            {
                ChessPosition pos = new ChessPosition(row, col);
                ChessPiece currPiece = currentGame.getPiece(pos);
                if (currPiece != null && currPiece.getTeamColor() == color)
                {
                    allValidMoves.addAll(validMoves(pos));
                }
            }
        }
        return allValidMoves;
    }

    private boolean tryMove(ChessMove move)
    {
//        System.out.println("running tryMove");
        ChessPiece pieceStart = currentGame.getPiece(move.getStartPosition());
        ChessPiece pieceEnd = currentGame.getPiece(move.getEndPosition());
        ChessGame.TeamColor color = pieceStart.getTeamColor();
        boolean isPossible = true;

        currentGame.removePiece(move.getStartPosition());
        currentGame.addPiece(move.getEndPosition(), pieceStart);
//        System.out.println(move);
        if (isInCheck(color))
        {

            isPossible = false;
        }
        currentGame.removePiece(move.getEndPosition());
        currentGame.addPiece(move.getStartPosition(), pieceStart);
        if (pieceEnd != null)
        {
            currentGame.addPiece(move.getEndPosition(), pieceEnd);
        }
//        System.out.println();
        return isPossible;

    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException
    {
        findKings();
        System.out.println("running makeMove");
        ChessPiece piece = currentGame.getPiece(move.getStartPosition());
        // check if it is a valid move
        Collection<ChessMove> moves = validMoves(move.getStartPosition());
        System.out.println(move);
        System.out.println(moves);
        ChessPiece.PieceType promotion = move.getPromotionPiece();

        if (promotion != null)
        {
            piece.setType(promotion);
        }
        if (piece.getTeamColor() != currentTurn)
        {
            throw new InvalidMoveException();
        }

        if (moves.contains(move) && (piece != null))
        {
            System.out.println("good move");
            currentGame.removePiece(move.getStartPosition());
//            System.out.println("After Remove \n"+currentGame);
            currentGame.addPiece(move.getEndPosition(), piece);
            changeTeamTurn();

            if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == TeamColor.BLACK)
            {
                blackKing = move.getEndPosition();
            } else if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == TeamColor.WHITE)
            {
                whiteKing = move.getEndPosition();
            }
        } else
        {
            throw new InvalidMoveException();
        }

    }

    public ChessPosition getBlackKing()
    {
        return blackKing;
    }

    public void findKings()
    {
        for (Map.Entry<ChessPosition, ChessPiece> entry : currentGame.getPieces().entrySet())
        {
            if (entry.getValue() == null)
            {

            } else if (entry.getValue().getPieceType() == ChessPiece.PieceType.KING)
            {
                if (entry.getValue().getTeamColor() == TeamColor.WHITE)
                {
                    whiteKing = entry.getKey();
                } else if (entry.getValue().getTeamColor() == TeamColor.BLACK)
                {
                    blackKing = entry.getKey();
                }
            }
        }
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
    public boolean isInCheck(TeamColor teamColor)
    {
        findKings();
//        System.out.println("running isInCheck");
        ChessPosition pos = (teamColor == TeamColor.WHITE) ? (getWhiteKing()) : (getBlackKing());

        System.out.println(pos);
//        System.out.println("Diagonal " + CheckDiagonal(pos));
//        System.out.println("Jumps " + CheckJumps(pos));
        System.out.println("Pawns " + checkPawns(pos));
//        System.out.println("Straight " + CheckStraight(pos));
//        System.out.println("King " + CheckKing(pos));
        System.out.println();


        return (checkDiagonal(pos) || checkJumps(pos) || checkPawns(pos) || checkStraight(pos) || checkKing(pos));
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor)
    {
//        System.out.println("running isInCheckmate");
        // no valid moves and in check
        System.out.println("Size " + allValidMoves(teamColor));
        System.out.println("Check " + isInCheck(teamColor));
        return allValidMoves(teamColor).isEmpty() && isInCheck(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor)
    {
        return allValidMoves(teamColor).isEmpty() && !isInCheck(teamColor);
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board)
    {
        currentGame = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard()
    {
        return currentGame;
    }

    /**
     * returns enemy chess pieces in a given direction and range
     *
     * @param startPos the position of the king
     * @param vector   the direction vector being checked
     * @param spaces   the number of spaces to check for
     */
    public ChessPiece.PieceType checkForAttack(ChessPosition startPos, int[] vector, int spaces)
    {
//        System.out.println("running CheckForAttack");
        ChessPosition pos = startPos;
        try
        {
            TeamColor color = currentGame.getPiece(startPos).getTeamColor();
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
        } catch (Exception e)
        {
            return null;
        }
        return null;

    }

    public Boolean checkKing(ChessPosition startPos)
    {
        int[] dir = new int[]{1, -1};
        for (int i = 0; i < 4; i++)
        {
            if (checkForAttack(startPos, dir, 1) == ChessPiece.PieceType.KING)
            {
                return true;
            }
            dir = rotate90(dir);
        }
        dir = new int[]{1, 0};
        for (int i = 0; i < 4; i++)
        {
            if (checkForAttack(startPos, dir, 1) == ChessPiece.PieceType.KING)
            {
                return true;
            }
            dir = rotate90(dir);
        }
        return false;
    }

    public Boolean checkDiagonal(ChessPosition startPos)
    {
//        System.out.println("running CheckDiagonal");
        int[] dir = new int[]{1, -1};
        for (int i = 0; i < 4; i++)
        {
            if (checkForAttack(startPos, dir, 8) == ChessPiece.PieceType.BISHOP || checkForAttack(startPos, dir, 8) == ChessPiece.PieceType.QUEEN)
            {
                return true;
            }
            dir = rotate90(dir);
        }
        return false;
    }

    public Boolean checkStraight(ChessPosition startPos)
    {
//        System.out.println("running CheckStraight");
        int[] dir = new int[]{1, 0};
        for (int i = 0; i < 4; i++)
        {
            if (checkForAttack(startPos, dir, 8) == ChessPiece.PieceType.ROOK || checkForAttack(startPos, dir, 8) == ChessPiece.PieceType.QUEEN)
            {
                return true;
            }
            dir = rotate90(dir);
        }
        return false;
    }

    public Boolean checkJumps(ChessPosition startPos)
    {
//        System.out.println("running CheckJumps");
        int[] dir = new int[]{1, 2};
        for (int i = 0; i < 4; i++)
        {
            if (checkForAttack(startPos, dir, 1) == ChessPiece.PieceType.KNIGHT)
            {
                return true;
            }
            dir = rotate90(dir);
        }
        int[] dir2 = new int[]{1, -2};
        for (int i = 0; i < 4; i++)
        {
            if (checkForAttack(startPos, dir2, 1) == ChessPiece.PieceType.KNIGHT)
            {
                return true;
            }
            dir2 = rotate90(dir2);
        }
        return false;
    }

    public Boolean checkPawns(ChessPosition startPos)
    {
//        System.out.println("running CheckPawns");
        try
        {
            TeamColor color = currentGame.getPiece(startPos).getTeamColor();
            int row = (color == TeamColor.WHITE) ? (1) : (-1);
            int col = 1;
            int[] array = new int[]{row, col};
            int[] array2 = new int[]{row, -col};
            System.out.println("pawn Start " + startPos);
            System.out.println("add pos " + array[0] + ", " + array[1]);
            System.out.println("add pos " + array2[0] + ", " + array2[1]);
            ChessPosition left = startPos.addPosition(new int[]{row, col});
            ChessPosition right = startPos.addPosition(new int[]{row, -col});

            ChessPiece rightPiece = currentGame.getPiece(right);
            ChessPiece leftPiece = currentGame.getPiece(left);
            System.out.println("pawn Right " + right + " " + rightPiece);
            System.out.println("pawn Left " + left + " " + leftPiece);

            if ((leftPiece != null) && (leftPiece.getPieceType() == ChessPiece.PieceType.PAWN) && (color != leftPiece.getTeamColor()))
            {
                return true;
            } else if ((rightPiece != null) && (rightPiece.getPieceType() == ChessPiece.PieceType.PAWN) && (color != rightPiece.getTeamColor()))
            {
                return true;
            }
        } catch (Exception e)
        {
            return false;
        }
        return false;
    }

    private static int[] rotate90(int[] x)
    {
        int a = x[0];
        int b = x[1];
        x[0] = b * -1;
        x[1] = a;
        return x;
    }
}