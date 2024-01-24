package chess;
import java.util.*;


import static chess.ChessGame.TeamColor.*;
import static chess.ChessPiece.PieceType.*;


/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    public ChessBoard() {}
    private Map<ChessPosition,ChessPiece> pieces = new HashMap<>();

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        pieces.put(position,piece);
    }

    /**
     * Removes a chess piece from the board
     *
     * @param position The position to get the piece from
     */
    public void removePiece(ChessPosition position){
        pieces.remove(position);
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return pieces.get(position);
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        pieces.clear();
        initalSetup();
    }

    public void initalSetup(){
        // add the pawns
        for (int i = 1; i <= 8; i++) {
            addPiece(new ChessPosition(2,i), new ChessPiece(WHITE,PAWN));
            addPiece(new ChessPosition(7,i), new ChessPiece(BLACK,PAWN));
        }
        // add the Backline
        for (int i = 1; i <=8 ; i+=7) {
            // set the color
            ChessGame.TeamColor color = (i==1) ? (WHITE):(BLACK);

            // add the Rooks
            addPiece(new ChessPosition(i,1), new ChessPiece(color,ROOK));
            addPiece(new ChessPosition(i,8), new ChessPiece(color,ROOK));

            // add the kights
            addPiece(new ChessPosition(i,2), new ChessPiece(color,KNIGHT));
            addPiece(new ChessPosition(i,7), new ChessPiece(color,KNIGHT));

            // add the Bishops
            addPiece(new ChessPosition(i,3), new ChessPiece(color,BISHOP));
            addPiece(new ChessPosition(i,6), new ChessPiece(color,BISHOP));

            // add the Queen
            addPiece(new ChessPosition(i,4), new ChessPiece(color,QUEEN));

            // add the King
            addPiece(new ChessPosition(i,5), new ChessPiece(color,KING));
        }

    }

    public void printBoard(){
        for (int row = 8; row >=1 ; row--) {

            for (int col = 1; col <=8 ; col++) {
                ChessPiece currPiece = getPiece(new ChessPosition(row,col));
                if(currPiece == null)
                {
                    System.out.print("|\u00A0\u2008\u2008");
                }
                else{
                    System.out.print("|");
                    System.out.print(currPiece);
                }

            }
            System.out.println("|");

        }
    }



}
