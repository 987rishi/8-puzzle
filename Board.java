// package ASSIGNMENT5;

// import edu.princeton.cs.algs4.Queue;
// import edu.princeton.cs.algs4.Queue;
// import java.util.Iterator;
// import java.util;;
import java.lang.Math;
import java.util.Random;

import edu.princeton.cs.algs4.Queue;

public class Board {
    private int[][] Board;
    private final int dimension;
    private int Blankx;
    private int Blanky;
    private int rowS1 = 1;
    private int colS1 = 0;
    private int rowS2 = 0;
    private int colS2 = 1;

    private Board(Board that) {
        if (this == that)
            throw new IllegalArgumentException("SAME OBJECT PASSED");
        this.dimension = that.dimension;
        this.Board = new int[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (that.Board[i][j] == 0) {
                    Blankx = i;
                    Blanky = j;
                }
                this.Board[i][j] = that.Board[i][j];
            }
        }
    }

    public Board(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException("INPUT ARRAY IS NULL");
        if (tiles.length != tiles[0].length)
            throw new IllegalArgumentException("DIMENSIONS OF INPUT ARRAY DOES NOT MATCH");
        this.dimension = tiles.length;
        this.Board = new int[this.dimension][this.dimension];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == 0) {
                    Blankx = i;
                    Blanky = j;
                }
                Board[i][j] = tiles[i][j];
            }
        }
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(dimension + "\n");
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                output.append(String.format("%5d", Board[i][j]));
            }
            output.append("\n");
        }
        return output.toString();
    }

    public int dimension() {
        return this.dimension;
    }

    public int hamming() {
        int dist = 0;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (i == this.dimension - 1 && j == this.dimension - 1)
                    break;
                if (!(Board[i][j] == (i * this.dimension) + j + 1))
                    dist++;
            }
        }
        return dist;
    }

    public int manhattan() {
        int matD = 0;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (Board[i][j] == 0)
                    // matD = matD + Math.abs((9 - 1) % 3 - j) + Math.abs((9 - 1) / 3 - i);
                    continue;
                if (!(Board[i][j] == (i * this.dimension + j + 1))) {

                    matD = matD + Math.abs((Board[i][j] - 1) % this.dimension - j)
                            + Math.abs((Board[i][j] - 1) / this.dimension - i);
                }
                // System.out.println("Board[][] " + Board[i][j] + " " + i + " " + j + " "
                // + (Math.abs((Board[i][j] - 1) % 3 - j) + Math.abs((Board[i][j] - 1) / 3 -
                // i)));
            }
        }
        return matD;
    }

    public boolean isGoal() {
        int dim = this.dimension;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (i == dim - 1 && j == dim - 1)
                    continue;
                if (Board[i][j] != (i * dim + j + 1))
                    return false;
            }
        }
        return true;
    }

    public boolean equals(Object y) {
        if (this == y)
            return true;
        if (y == null)
            return false;
        if (this.getClass() != y.getClass())
            return false;

        Board t = (Board) y;
        if (this.dimension != t.dimension)
            return false;
        int dim = this.dimension;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (Board[i][j] != t.Board[i][j])
                    return false;
            }
        }
        return true;

    }

    public Iterable<Board> neighbors() {
        Queue<Board> myQueue = new Queue<Board>();
        int rowB = -1, colB = -1;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (Board[i][j] == 0) {
                    rowB = i;
                    colB = j;

                }
            }
        }
        // System.out.println("0 pos " + rowB + " " + colB);
        if (rowB - 1 >= 0) {
            Board neighbour = new Board(this);
            neighbour.swap(neighbour, rowB - 1, colB, rowB, colB);
            myQueue.enqueue(neighbour);
        }
        if (rowB + 1 <= this.dimension - 1) {
            Board neighbour = new Board(this);
            neighbour.swap(neighbour, rowB + 1, colB, rowB, colB);
            myQueue.enqueue(neighbour);
        }
        if (colB + 1 <= this.dimension - 1) {
            Board neighbour = new Board(this);
            neighbour.swap(neighbour, rowB, colB, rowB, colB + 1);
            myQueue.enqueue(neighbour);
        }
        if (colB - 1 >= 0) {
            Board neighbour = new Board(this);
            neighbour.swap(neighbour, rowB, colB, rowB, colB - 1);
            myQueue.enqueue(neighbour);
        }
        // System.out.println("queue size " + myQueue.size());

        return myQueue;
    }

    public Board twin() {
        // int twinDimen = this.dimension;
        // int[][] twinTiles = new int[twinDimen][twinDimen];
        // for (int i = 0; i < twinDimen; i++) {
        // for (int j = 0; j < twinDimen; j++)
        // twinTiles[i][j] = Board[i][j];
        // }
        Board twin = new Board(this.Board);
        // if((this.Blankx==rowS1||this.Blankx==rowS2)&&(this.Blanky==colS1||this.Blanky==colS2))
        // int rowS1 = 1, colS1 = 1;
        // int rowS2 = 0, colS2 = 0;
        Random rn = new Random();
        while (rowS1 == this.Blankx && colS1 == this.Blanky) {
            rowS1 = rn.nextInt(this.dimension);
            colS1 = rn.nextInt(this.dimension);
        }
        while (rowS2 == this.Blankx && colS2 == this.Blanky || rowS2 == rowS1 && colS2 == colS1) {
            rowS2 = rn.nextInt(this.dimension);
            colS2 = rn.nextInt(this.dimension);
        }

        // System.out.println(" blank tile " + Blankx + " " + Blanky);
        // System.out.println("rows1,cols1,rows2,cols2 " + rowS1 + " " + colS1 + " " +
        // rowS2 + " " + colS2);
        swap(twin, rowS1, colS1, rowS2, colS2);
        // System.out.println("TWIN INSIDE " + twin);
        return twin;
    }

    private void swap(Board myBoard, int fr, int fc, int sr, int sc) {
        if (myBoard == null)
            throw new IllegalArgumentException("NULL ARGUMENT");
        int temp;

        temp = myBoard.Board[fr][fc];
        myBoard.Board[fr][fc] = myBoard.Board[sr][sc];
        myBoard.Board[sr][sc] = temp;
        // System.out.println("VOARD AFETE SWAP "+my);

        return;
    }

    public static void main(String[] argv) {
        int[][] tiles = { { 1, 5, 2 }, { 7, 0, 4 }, { 8, 6, 3 } };
        int[][] goal = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        // for (int i = 0; i < 3; i++) {
        // for (int j = 0; j < 3; j++) {
        // if (i == 2 && j == 2)
        // tiles[i][j] = 0;
        // else
        // tiles[i][j] = i * 3 + j + 1;
        // }
        // }
        // System.out.println(tiles);
        Board gB = new Board(goal);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(String.format("%3d", tiles[i][j]));
            }
            System.out.println("\n");

        }

        Board b = new Board(tiles);
        System.out.println("isGoal    " + b.isGoal());
        Iterable<Board> it = b.neighbors();
        for (Board c : it) {
            System.out.println(c);
        }
        System.out.println("original board  " + b);

        Board twinBoard;
        twinBoard = b.twin();
        System.out.println("twin  Board  " + twinBoard);

        System.out.println("twin    " + b.twin());
        System.out.println("twin    " + b.twin());
        System.out.println("twin    " + b.twin());
        System.out.println("twin    " + b.twin());
        System.out.println("twin    " + b.twin());
        System.out.println("twin    " + b.twin());
        System.out.println("hamD   " + b.hamming());

        // System.out.println("isGoal gB " + gB.isGoal());

        // while (it.iterator().hasNext()) {
        // it.iterator().next();

        // }

    }
}
