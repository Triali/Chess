import chess.*;
import ui.PrintBoard;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        ChessGame game = new ChessGame();
        PrintBoard printing = new PrintBoard(game);
        printing.printBoardWhite();
        System.out.println("\n\n");
        printing.printBoardBlack();

    }
}