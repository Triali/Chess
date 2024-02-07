package chess;


import java.util.Collection;

public class Testing {

    public static void main(String[] args) {
        // create board
        ChessBoard board = new ChessBoard();

        ChessPiece Pawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        ChessPiece Rook = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece Queen = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        ChessPiece Bishop = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece Knight = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece King = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);


        ChessPiece rook = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece knight = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece queen = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        ChessPiece bishop = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece pawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece king = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);


        board.addPiece(new ChessPosition(2,3),King);
//        board.addPiece(new ChessPosition(2,3), Bishop);
        board.addPiece(new ChessPosition(6,3), rook);
//        board.addPiece(new ChessPosition(7,8), queen);
        board.addPiece(new ChessPosition(8,8), knight);
//        board.addPiece(new ChessPosition(6,7), pawn);

        ChessGame game = new ChessGame();
        game.setBoard(board);





        board.printBoard();
        System.out.println(game.CheckStraight(new ChessPosition(2,3)));
        System.out.println(game.isInCheck(ChessGame.TeamColor.WHITE));
//        int[] dir = new int[]{0,-1};
//        System.out.println(game.validMoves(new ChessPosition(1,2)));
//        try
//        {
//
//            game.makeMove(new ChessMove(new ChessPosition(1, 2), new ChessPosition(1, 1)));
//
//            System.out.println("moving King");
//            board.printBoard();
//
//            System.out.println(game.getTeamTurn());
//        }catch (Exception e)
//        {
//
//        }




    }
}
