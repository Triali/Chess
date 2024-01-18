package chess;




public class Testing {
    public static int[] rotate90(int[] x){
        int a = x[0];
        int b = x[1];
        x[0] = b*-1;
        x[1] = a;
        return x;
    }
    public static void main(String[] args) {
        ChessBoard board = new ChessBoard();




        board.printBoard();

int[] x = {1,1};
//        x= rotate90(x);
        System.out.println("(" + x[0]+","+x[1]+")");
        x= rotate90(x);
        System.out.println("(" + x[0]+","+x[1]+")");
        x= rotate90(x);
        System.out.println("(" + x[0]+","+x[1]+")");
        x= rotate90(x);
        System.out.println("(" + x[0]+","+x[1]+")");

//
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
