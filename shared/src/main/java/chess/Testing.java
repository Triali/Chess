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


        board.addPiece(new ChessPosition(5,4),King);
        board.addPiece(new ChessPosition(8,4), rook);
        board.addPiece(new ChessPosition(8,7), queen);
        board.addPiece(new ChessPosition(6,3), pawn);
        board.addPiece(new ChessPosition(7,3), knight);
        board.addPiece(new ChessPosition(5,1), queen);

        ChessGame game = new ChessGame();
        game.setBoard(board);
        game.setWhiteKing(new ChessPosition(5,4));





        board.printBoard();
        int[] dir = new int[]{0,-1};
        System.out.println(game.isInCheck(ChessGame.TeamColor.WHITE));





    }
}
