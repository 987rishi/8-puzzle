// package ASSIGNMENT5;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class testClient {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            int cnt = -1;
            for (Board board : solver.solution()) {
                StdOut.println(board);
                cnt++;
            }
            StdOut.println("SOln number of items = " + cnt);

        }
    }
}
