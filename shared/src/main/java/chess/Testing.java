package chess;


import java.util.Collection;

public class Testing {

    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();
        ChessPosition test = new ChessPosition(2,5);
        ChessPiece piece= board.getPiece(test);
        int[] x = {-1,0};
                board.printBoard();
        Collection<ChessMove> moves= piece.checkDirection(board,test,x);
        for (ChessMove move:moves)
        {
            System.out.println(move);
        }








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
