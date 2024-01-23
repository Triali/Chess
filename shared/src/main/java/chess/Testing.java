package chess;


import java.util.Collection;

public class Testing {

    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        ChessPosition start= new ChessPosition(5,4);
        ChessPiece bishop = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        board.addPiece(start,bishop);
        board.printBoard();
        Collection<ChessMove> moves = bishop.checkDiagonals(board,start,8);
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
