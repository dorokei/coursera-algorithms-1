/* *****************************************************************************
 *  Name: Keita Ono
 *  Date: 2021/02/18
 *  Description: Coursera homework
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

// Finds all line segments containing 4 points
public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        // Corner cases
        if (points == null) throw new IllegalArgumentException();
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Point p = points[i];
                Point q = points[j];
                if (p.compareTo(q) == 0) throw new IllegalArgumentException();
            }
        }

        ArrayList<LineSegment> segmentsAL = new ArrayList<LineSegment>();
        // 総当りを行う
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point pointI = points[i];
                        Point pointJ = points[j];
                        Point pointK = points[k];
                        Point pointL = points[l];
                        // System.out.print(pointI.slopeTo(pointJ) + ", " + pointI.slopeTo(pointK) + ", " + pointI.slopeTo(pointL) + "\n");
                        if (pointI.slopeTo(pointJ) == pointI.slopeTo(pointK)
                                && pointI.slopeTo(pointK) == pointI.slopeTo(pointL)
                        ) {
                            Point[] four_points = new Point[4];
                            four_points[0] = pointI;
                            four_points[1] = pointJ;
                            four_points[2] = pointK;
                            four_points[3] = pointL;
                            Arrays.sort(four_points);
                            LineSegment a = new LineSegment(four_points[0], four_points[3]);
                            segmentsAL.add(a);
                        }
                    }
                }
            }
        }
        segments = segmentsAL.toArray(new LineSegment[segmentsAL.size()]);
    }

    // The number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    /**
     * should include each line segment containing 4 points exactly once.
     * If 4 points appear on a line segment in the order p→q→r→s,
     * then you should include either the line segment p→s or s→p (but not both)
     * and you should not include subsegments such as p→r or q→r. For simplicity,
     * we will not supply any input to BruteCollinearPoints that has 5 or more collinear points.
     *
     * @return The line segments
     */
    public LineSegment[] segments() {
        return segments.clone();
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        // read the n points from a file
        StdOut.println("Start");
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            System.out.print(x + ", " + y + "\n");
            points[i] = new Point(x, y);
        }

        // print and draw the line segments
        StdOut.println("Result");
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
        StdOut.println("End");
    }
}
