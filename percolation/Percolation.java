/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf;
    private boolean[] openArr;
    private final int length;
    private int openSum;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        length = n;
        openSum = 0;
        uf = new WeightedQuickUnionUF(n * n + 2);
        openArr = new boolean[n * n + 2];

        // Connect top
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
        }

        // Connect bottom
        for (int i = getPositonFromIndex(n, 1); i <= getPositonFromIndex(n, n); i++) {
            uf.union(i, n * n + 1);
        }
    }

    private void checkCanonical(int row, int col) {
        checkRowCol(row, col);
        int index = getPositonFromIndex(row, col);
        System.out.println(row + "," + col + " : " + uf.find(index));
    }

    private void checkRowCol(int row, int col) {
        if (row <= 0 || col <= 0 || row > length || col > length) {
            throw new IllegalArgumentException(row + "," + col + " length:" + length);
        }
    }

    private int getPositonFromIndex(int row, int col) {
        checkRowCol(row, col);
        return (row - 1) * length + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkRowCol(row, col);
        int index = getPositonFromIndex(row, col);

        // Open
        if (openArr[index]) return;

        openArr[index] = true;
        openSum++;

        // Connect
        // Top
        if (1 < row) {
            int i = getPositonFromIndex(row - 1, col);
            if (openArr[i]) uf.union(index, i);
        }

        // Right
        if (col < length) {
            int i = getPositonFromIndex(row, col + 1);
            if (openArr[i]) uf.union(index, i);
        }

        // Bottom
        if (row < length) {
            int i = getPositonFromIndex(row + 1, col);
            if (openArr[i]) uf.union(index, i);
        }

        // Left
        if (1 < col) {
            int i = getPositonFromIndex(row, col - 1);
            if (openArr[i]) uf.union(index, i);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkRowCol(row, col);
        int index = getPositonFromIndex(row, col);
        return openArr[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkRowCol(row, col);
        int index = getPositonFromIndex(row, col);
        return uf.find(0) == uf.find(index) && openArr[index];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSum;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(length * length + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(3);

        p.checkCanonical(1, 2);
        System.out.println("Open 1,2");
        p.open(1, 2);
        p.checkCanonical(1, 2);

        p.checkCanonical(2, 2);
        System.out.println("Open 2,2");
        p.open(2, 2);
        p.checkCanonical(2, 2);

        System.out.println("Open 3,2");
        p.open(3, 2);
        p.checkCanonical(3, 2);

        if (p.isFull(2, 2)) System.out.println("2,2 is full.");
        if (p.percolates()) System.out.println("percolates!");

    }
}
