package chess;


import java.util.Collection;

public class Testing {

    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        ChessPosition start= new ChessPosition(7,4);
        ChessPiece pawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece rook = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        board.addPiece(start,pawn);
        board.addPiece(new ChessPosition(8,5), rook);
        board.printBoard();
        Collection<ChessMove> moves = pawn.pieceMoves(board,start);
        System.out.println(moves);








//        x= rotate90(x);

//
//        System.out.println("(" + x+","+y+")");
//        x = b*-1;
//        y = a;
//        a = x;
//        b = y;
//        System.out.println("(" + x+","+y+")");
//        x = b*-1;
//        y = a;
//        a = x;
//        b = y;
//        System.out.println("(" + x+","+y+")");



    }
}
