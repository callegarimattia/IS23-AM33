package server.model;

public class DACANCELLARE {


    public static void main(String[] args) {

        Board board = new Board(4);
        Tile[][] matrix = board.getMainBoard();
        for(int i = 0; i<9; i++){
            for(int j = 0; j<9; j++){
                System.out.print(matrix[i][j]+"\t");
            }
            System.out.println("\n");
        }


    }


}
