import chess.*;
import ui.PrintBoard;
import java.util.Scanner;

public class Client
{
    private String token;

    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        ChessGame game = new ChessGame();
    }

    private void listLoginOption(){
        System.out.println("Please select an option by entering the number");
        System.out.println("1.Register");
        System.out.println("2.Login");
        System.out.println("3.Help");
        System.out.println("4.Quit");
    }
    public void getLoginOption(){

        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        switch(a){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
        }
    }
    private void listUserOption(){
        System.out.println("Please select an option by entering the number");
        System.out.println("1.Logout");
        System.out.println("2.List Games");
        System.out.println("3.Create Game");
        System.out.println("4.Join Game");
        System.out.println("5.Join Observer");
        System.out.println("6.Help");
    }
    public void getUserOption(){
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        switch(a){
            case 1://Logout
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            default:
                break;
        }
    }



    private void printBlack(ChessGame game){
        PrintBoard printing = new PrintBoard(game);
        printing.printBoardBlack();
    }

    private void printWhite(ChessGame game){
        PrintBoard printing = new PrintBoard(game);
        printing.printBoardWhite();
    }
}