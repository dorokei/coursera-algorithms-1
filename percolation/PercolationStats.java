/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] results;
    private final double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        results = new double[trials];
        double total = n * n;

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            }

            results[i] = p.numberOfOpenSites() / total;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(results.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[0]);
        PercolationStats p = new PercolationStats(n, t);
        System.out.println("mean                    = " + p.mean());
        System.out.println("stddev                  = " + p.stddev());
        System.out.println(
                "95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
    }
}
