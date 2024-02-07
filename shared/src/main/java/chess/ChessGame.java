package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
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
    private void ChangeTeamTurn(){
        currentTurn = (currentTurn == TeamColor.WHITE)?(TeamColor.BLACK):(TeamColor.WHITE);
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
        return Objects.hash(currentGame, currentTurn, blackKing, whiteKing, lastMove);
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
//        System.out.println("running validMoves");
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessPiece piece = currentGame.getPiece(startPosition);
        if(piece == null)
        {
            return validMoves;
        }

        validMoves.addAll( piece.pieceMoves(currentGame,startPosition));
        for (ChessMove move:validMoves)
        {
           if( tryMove(move)){
               validMoves.add(move);
           }
        }


        return validMoves;
    }


    private Collection<ChessMove> AllValidMoves(TeamColor color){
//        System.out.println("running AllValidMoves");
        Collection<ChessMove> allValidMoves = new HashSet<>();
        for(Map.Entry<ChessPosition,ChessPiece> entry:currentGame.getPieces().entrySet()){
            if(entry.getValue().getTeamColor()==color){
                allValidMoves.addAll(validMoves(entry.getKey()));
            }
        }
        return allValidMoves;
    }

    private boolean tryMove(ChessMove move){
//        System.out.println("running tryMove");
        ChessPiece pieceStart = currentGame.getPiece(move.getStartPosition());
        ChessPiece pieceEnd = currentGame.getPiece(move.getEndPosition());
        ChessGame.TeamColor color = pieceStart.getTeamColor();
        boolean isPossible = true;

        currentGame.removePiece(move.getStartPosition());
        currentGame.addPiece(move.getEndPosition(), pieceStart);
        if(isInCheck(color))
        {

            isPossible =  false;
        }
        currentGame.removePiece(move.getEndPosition());
        currentGame.addPiece(move.getStartPosition(), pieceStart);
        currentGame.addPiece(move.getEndPosition(), pieceEnd);
        return isPossible;

    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        FindKings();
//        System.out.println("running makeMove");
        ChessPiece piece = currentGame.getPiece(move.getStartPosition());
        // check if it is a valid move
        Collection<ChessMove> moves = validMoves(move.getStartPosition());
        if(moves.contains(move) || (piece != null))
        {
            currentGame.removePiece(move.getStartPosition());
            currentGame.addPiece(move.getEndPosition(), piece);
            ChangeTeamTurn();

            if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == TeamColor.BLACK)
            {
                blackKing = move.getEndPosition();
            } else if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == TeamColor.WHITE)
            {
                whiteKing = move.getEndPosition();
            }
        }else {
            throw new InvalidMoveException();
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

    public void FindKings(){
        for(Map.Entry<ChessPosition,ChessPiece> entry:currentGame.getPieces().entrySet()){
            if(entry.getValue()==null){

            }
            else if(entry.getValue().getPieceType()==ChessPiece.PieceType.KING){
                if(entry.getValue().getTeamColor() ==TeamColor.WHITE){
                    whiteKing = entry.getKey();
                }
                else if(entry.getValue().getTeamColor() ==TeamColor.BLACK){
                    blackKing = entry.getKey();
                }

            }
        }

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
        FindKings();
//        System.out.println("running isInCheck");
        ChessPosition pos = (teamColor == TeamColor.WHITE)?(getWhiteKing()):(getBlackKing());
        return (CheckDiagonal(pos) || CheckJumps(pos) || CheckPawns(pos) || CheckStraight(pos));
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
//        System.out.println("running isInCheckmate");
        // no valid moves and in check
        return AllValidMoves(teamColor).isEmpty() && isInCheck(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
//        System.out.println("running isInStalemate");
        // no valid moves and not in check
        return AllValidMoves(teamColor).isEmpty() && !isInCheck(teamColor);
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
        }catch (Exception e){
            return null;
        }
        return null;

    }
    public Boolean CheckDiagonal(ChessPosition startPos){
//        System.out.println("running CheckDiagonal");
        int[] dir = new int[]{1,-1};
        for (int i = 0; i < 4; i++)
        {
            if(CheckForAttack(startPos,dir,8)== ChessPiece.PieceType.BISHOP || CheckForAttack(startPos,dir,8)== ChessPiece.PieceType.QUEEN){
                return true;
            }
            dir = rotate90(dir);
        }
        return false;
    }
    public Boolean CheckStraight(ChessPosition startPos){
//        System.out.println("running CheckStraight");
        int[] dir = new int[]{1,0};
        for (int i = 0; i < 4; i++)
        {
            if(CheckForAttack(startPos,dir,8)== ChessPiece.PieceType.ROOK || CheckForAttack(startPos,dir,8)== ChessPiece.PieceType.QUEEN){
                return true;
            }
            dir = rotate90(dir);
        }
        return false;
    }
    public Boolean CheckJumps(ChessPosition startPos){
//        System.out.println("running CheckJumps");
        int[] dir = new int[]{1,2};
        for (int i = 0; i < 4; i++)
        {
            if(CheckForAttack(startPos,dir,1)== ChessPiece.PieceType.KNIGHT){
                return true;
            }
            dir = rotate90(dir);
        }
        int[] dir2 = new int[]{1,-2};
        for (int i = 0; i < 4; i++)
        {
            if(CheckForAttack(startPos,dir2,1)== ChessPiece.PieceType.KNIGHT){
                return true;
            }
            dir2 = rotate90(dir2);
        }
        return false;
    }
    public Boolean CheckPawns(ChessPosition startPos){
//        System.out.println("running CheckPawns");
        try
        {
            TeamColor color = currentGame.getPiece(startPos).getTeamColor();
            int row = (color == TeamColor.WHITE) ? (1) : (-1);
            int col = 1;
            if ((currentGame.getPiece(startPos.addPosition(new int[]{row, col})).getPieceType() == ChessPiece.PieceType.PAWN)
                    || (currentGame.getPiece(startPos.addPosition(new int[]{row, -col})).getPieceType() == ChessPiece.PieceType.PAWN))
            {
                return true;
            }
        }catch (Exception e)
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
