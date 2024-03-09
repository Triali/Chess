import chess.*;
import server.*;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("â™• 240 Chess Client: " + piece);

        Server server= new Server();
        server.run(8080);

    }
}