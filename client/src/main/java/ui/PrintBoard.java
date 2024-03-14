package ui;

import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class PrintBoard {
    private ChessGame game;
    private PrintStream out;
    public PrintBoard(ChessGame game){
        this.game = game;
        game.getBoard().initalSetup();
        this.out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
    }
    public void printBoardWhite(){
        out.print(ERASE_SCREEN);
        printHeader();

        for (int i = 1; i <9; i++) {
            printRow(i);
        }
        printHeader();

    }
    private void printHeader(){
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);
        printBorderSquare(-1);
        char letter = 'a';
        for (int i = 1; i < 9; i++) {
            out.print("\u2002\u2009"+letter+"\u2002\u2009");
            letter++;

        }
        printBorderSquare(-1);
        printEndline();
    }
    private void printRow(int i){
        printBorderSquare(9-i);
        for (int j = 1; j < 9; j++) {
            printSquare(i,j);
        }
        printBorderSquare(9-i);
        printEndline();
    }

    public void printBoardBlack(){
        out.print(ERASE_SCREEN);
        printHeaderRev();

        for (int i = 8; i >0; i--) {
            printRowRev(i);
        }
        printHeaderRev();
    }

    private void printRowRev(int i){
        printBorderSquare(i);
        for (int j = 8; j > 0; j--) {
            printSquare(i,j);
        }
        printBorderSquare(i);
        printEndline();
    }

    private void printHeaderRev(){
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_BLACK);
        printBorderSquare(-1);
        char letter = 'a';
        letter+=7;
        for (int i = 1; i < 9; i++) {
            out.print("\u2002\u2009"+letter+"\u2002\u2009");
            letter--;

        }
        printBorderSquare(-1);
        printEndline();
    }


    private  void printSquare(int i, int j){
        if((i+j)%2==1){
            //print black
            out.print(SET_BG_COLOR_DARK_GREY);
            printPiece(i,j);


        }
        else{
            //print White
            out.print(SET_BG_COLOR_LIGHT_LIGHT_GRAY);
            printPiece(i,j);
//            out.print(getPiece(i,j));
//            out.print("\u2002♜\u2002");

        }
    }
    private void printBorderSquare(int i){
//        print gray
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_BG_COLOR_WHITE);
        if(i==-1){
            out.print("   ");
        }else {

            out.print(" " + i + " ");
        }

    }
    private void printEndline(){
        out.print("\u001b[48;5;235m");
        out.print("\n");
    }
    private void printPiece(int row, int col){
        ChessPiece piece = game.getBoard().getPiece(new ChessPosition(row, col));
        if(piece ==null){
            out.print(EMPTY);
        }else {
            out.print("\u2002"+piece+"\u2002");
        }
    }
}
