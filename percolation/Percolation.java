/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Percolation {
    private final int maxLength;
    private final Cell[][] grids;
    private int openNum;
    private final int topRootIndex;
    private final int bottomRootIndex;

    private class Cell {
        private int parentRow;
        private int parentCol;
        private boolean isOpen;
        private int sz;

        public Cell(int x, int y) {
            parentRow = x;
            parentCol = y;
            isOpen = false;
            sz = 1;
        }
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        topRootIndex = n;
        bottomRootIndex = n + 1;
        grids = new Cell[n + 2][n + 2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    grids[i][j] = new Cell(topRootIndex, topRootIndex);
                }
                else if (i == n - 1) {
                    grids[i][j] = new Cell(bottomRootIndex, bottomRootIndex);
                }
                else {
                    grids[i][j] = new Cell(i, j);
                }
            }
        }
        // Top root cell
        Cell top = new Cell(topRootIndex, topRootIndex);
        top.sz = n;
        grids[topRootIndex][topRootIndex] = top;

        // Bottom root cell
        Cell bottom = new Cell(bottomRootIndex, bottomRootIndex);
        bottom.sz = n;
        grids[bottomRootIndex][bottomRootIndex] = bottom;

        openNum = 0;
        maxLength = n;
    }

    private Cell root(Cell c) {
        int a = c.parentRow;
        int b = c.parentCol;
        while (!(a == grids[a][b].parentRow &&
                b == grids[a][b].parentCol)) {
            Cell cell = grids[a][b];
            a = cell.parentRow;
            b = cell.parentCol;
        }
        return grids[a][b];
    }

    private void checkRoot(int row, int col) {
        Cell c = grids[row][col];
        Cell root = root(c);
        System.out.println(row + ", " + col + "'s parents: " + c.parentRow + ", " + c.parentCol);
        System.out.println(row + ", " + col + "'s root: " + root.parentRow + ", " + root.parentCol);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row >= maxLength || col >= maxLength) {
            throw new IllegalArgumentException(
                    "row:" + row + ", col:" + col + ", maxLength:" + maxLength);
        }

        Cell c = grids[row][col];
        if (c.isOpen) return;

        c.isOpen = true;
        openNum++;

        // top
        if (row - 1 >= 0 && grids[row - 1][col].isOpen) {
            connect(c, grids[row - 1][col]);
        }

        // right
        if (col + 1 <= maxLength - 1 && grids[row][col + 1].isOpen) {
            connect(c, grids[row][col + 1]);
        }

        // bottom
        if (row + 1 <= maxLength - 1 && grids[row + 1][col].isOpen) {
            connect(c, grids[row + 1][col]);
        }

        // left
        if (col - 1 >= 0 && grids[row][col - 1].isOpen) {
            connect(c, grids[row][col - 1]);
        }
    }

    private void connect(Cell a, Cell b) {
        Cell rootA = root(a);
        Cell rootB = root(b);
        if (rootA.parentRow == rootB.parentRow && rootA.parentCol == rootB.parentCol) return;

        if (rootA.sz > rootB.sz) {
            // Add B to A
            rootB.parentRow = rootA.parentRow;
            rootB.parentCol = rootA.parentCol;
            rootA.sz += rootB.sz;
        }
        else {
            // Add A to B
            rootA.parentRow = rootB.parentRow;
            rootA.parentCol = rootB.parentCol;
            rootB.sz += rootA.sz;
        }
    }

    private boolean connected(Cell a, Cell b) {
        Cell rootA = root(a);
        Cell rootB = root(b);
        return rootA.parentRow == rootB.parentRow && rootA.parentCol == rootB.parentCol;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row >= maxLength || col >= maxLength) {
            throw new IllegalArgumentException(
                    "row:" + row + ", col:" + col + ", maxLength:" + maxLength);
        }
        return grids[row][col].isOpen;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row >= maxLength || col >= maxLength) {
            throw new IllegalArgumentException(
                    "row:" + row + ", col:" + col + ", maxLength:" + maxLength);
        }

        Cell c = grids[row][col];
        Cell topRoot = grids[topRootIndex][topRootIndex];
        return connected(c, topRoot);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }

    // does the system percolate?
    public boolean percolates() {
        Cell topRoot = grids[topRootIndex][topRootIndex];
        Cell bottomRoot = grids[bottomRootIndex][bottomRootIndex];
        return connected(bottomRoot, topRoot);
    }

    // test client (optional)
    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        Percolation p = new Percolation(3);

        p.checkRoot(0, 1);
        System.out.println("Open 0,1");
        p.open(0, 1);
        p.checkRoot(0, 1);

        p.checkRoot(1, 1);
        System.out.println("Open 1,1");
        p.open(1, 1);
        p.checkRoot(1, 1);

        p.checkRoot(2, 1);
        System.out.println("Open 2,1");
        p.open(2, 1);
        p.checkRoot(2, 1);

        // if (p.isFull(1, 1)) System.out.println("1,1 is full.");
        if (p.percolates()) System.out.println("percolates!");
        // p.checkRoot(4, 4);
        // p.checkRoot(3, 3);
    }
}
