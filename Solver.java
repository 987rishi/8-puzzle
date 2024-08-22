// package ASSIGNMENT5;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private boolean solvable;
    private Stack<Board> seq;
    private int minMoves = Integer.MAX_VALUE;

    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("CONSTRUCTOR NULL");
        if (initial.isGoal() == true) {
            this.solvable = true;
            this.seq = new Stack<Board>();
            this.seq.push(initial);
            this.minMoves = 0;
            return;
        }
        Board twinBoard = initial.twin();

        // System.out.println("TWIN " + initial.twin());
        // System.out.println("TWIN BOARD " + twinBoard);

        searchNode oriNode = new searchNode(initial, 0, initial.manhattan(), null, null);
        // System.out.println("INITIAL MANHATTAN " + initial.manhattan());

        searchNode twinNode = new searchNode(twinBoard, 0, twinBoard.manhattan(), null, null);

        MinPQ<searchNode> originalPQ = new MinPQ<searchNode>();
        MinPQ<searchNode> twinPQ = new MinPQ<searchNode>();
        originalPQ.insert(oriNode);
        twinPQ.insert(twinNode);

        int oriMoves = 0;
        int twinMoves = 0;
        // System.out.println("HELLO");
        // System.out.println("ORI PQ SIZE " + originalPQ.size());
        // boolean oriSolvable = false;
        // boolean twinSolvable = false;

        this.seq = new Stack<Board>();

        searchNode temp1 = new searchNode(initial, 0, 0, null, null);
        while (!originalPQ.isEmpty() && !twinPQ.isEmpty()) {
            temp1 = originalPQ.delMin();
            // System.out.println("BOARD " + temp1.currentBoard());
            // System.out.println("mathD " + temp1.manhatD());
            // System.out.println("hammingD " + temp1.hamD());

            // System.out.println("PRIORITY " + temp1.precedence());
            // System.out.println("ORIGINAL " + temp1.currentBoard());
            // this.seq.enqueue(temp1.currentBoard());
            if (temp1.currentBoard().isGoal()) {
                this.minMoves = temp1.getMoves();
                // oriSolvable = true;
                this.solvable = true;

                break;
            }
            // oriMoves++;
            Iterable<Board> it1 = temp1.neighbors();
            for (Board b : it1) {
                if (!b.equals(temp1.predecessor())) {
                    searchNode toInsert = new searchNode(b, temp1.getMoves() + 1, b.manhattan(), temp1.currentBoard(),
                            temp1);
                    originalPQ.insert(toInsert);
                }
            }

            searchNode temp2 = twinPQ.delMin();
            if (temp2.currentBoard().isGoal()) {
                // twinSolvable = true;
                this.solvable = false;
                this.minMoves = -1;
                this.seq = null;
                break;

            }
            // twinMoves++;
            Iterable<Board> it2 = temp2.neighbors();
            for (Board c : it2) {
                if (!c.equals(temp2.predecessor())) {
                    searchNode toInsert = new searchNode(c, temp2.getMoves() + 1, c.manhattan(), temp2.currentBoard(),
                            temp2);
                    twinPQ.insert(toInsert);
                }
            }

        }
        // System.out.println(temp1.currentBoard());
        if (this.solvable == true) {
            // Board temp=temp1.predecessor();
            while (temp1 != null) {
                this.seq.push(temp1.currentBoard());
                temp1 = temp1.preSearch();
            }

        } else {
            this.seq = null;
        }
        // this.solvable = !twinSolvable;
        // pq.insert(initial);
    }

    public boolean isSolvable() {
        return this.solvable;
    }

    public int moves() {
        return this.minMoves;
    }

    public Iterable<Board> solution() {
        return this.seq;
    }

    private class searchNode implements Comparable<searchNode> {
        private Board current = null;
        private Board predecessorBoard = null;
        private searchNode predecSearchNode = null;
        private int moves = 0;
        private int matD = 0;
        private int hatD = 0;
        private int priority = Integer.MAX_VALUE;

        public searchNode(Board curr, int moves, int matD, Board predecessor, searchNode pre) {
            if (curr == null)
                throw new IllegalArgumentException("SEARCH NODE ARGUMENT NULL");
            if (moves < 0 || matD < 0)
                throw new IllegalArgumentException("SEARCH NODE ARGUMENTS ILLEGAL");
            this.current = curr;
            this.predecessorBoard = predecessor;
            this.moves = moves;
            this.matD = matD;
            this.hatD = curr.hamming();
            this.priority = moves + matD;
            this.predecSearchNode = pre;
        }

        public Iterable<Board> neighbors() {
            return this.current.neighbors();
        }

        public int precedence() {
            return this.priority;
        }

        public Board predecessor() {
            return predecessorBoard;
        }

        public searchNode preSearch() {
            return this.predecSearchNode;

        }

        public Board currentBoard() {
            return current;
        }

        public int getMoves() {
            return this.moves;
        }

        public int manhatD() {
            return this.matD;
        }

        public int hamD() {
            return this.hatD;

        }

        @Override
        public int compareTo(searchNode node) {
            if (this.priority == node.precedence()) {
                if (this.hatD < node.hamD())
                    return -1;
                else if (this.hatD > node.hamD())
                    return 1;
                else
                    return 0;
            } else if (this.priority > node.precedence())
                return 1;
            else
                return -1;
        }
    }

    public static void main(String[] argv) {
        int[][] tiles = { { 1, 0, 2 }, { 7, 5, 4 }, { 8, 6, 3 } };
        Board toSolve = new Board(tiles);
        Solver s = new Solver(toSolve);
        System.out.println(s.isSolvable());
        Iterable<Board> it = s.solution();
        // for (Board b : it) {
        // System.out.println(b);
        // }
        System.out.println("MOVES REQD  " + s.moves());

    }
}
