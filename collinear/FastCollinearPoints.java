import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

// finds all line segments containing 4 or more points
public class FastCollinearPoints {
    private Point[] points;
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
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

        this.points = points;
    }

    // The number of line segments
    public int numberOfSegments() {
        return segments().length;
    }

    /**
     * should include each maximal line segment containing 4 (or more) points exactly once.
     * For example, if 5 points appear on a line segment in the order p→q→r→s→t,
     * then do not include the subsegments p→s or q→t.
     *
     * @return The line segments
     */
    public LineSegment[] segments() {
        if (segments != null) return segments;
        ArrayList<LineSegment> segmentsAL = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            Point origin = points[i];
            // StdOut.println("origin: " + origin.toString());
            ArrayList<Point> pointsSorted = new ArrayList<Point>();
            for (int j = 0; j < points.length; j++) {
                if (i == j) continue;
                Point target = points[j];
                pointsSorted.add(target);
            }
            pointsSorted.sort(origin.slopeOrder());
            int start = 0;
            double preSlope = 0.0;
            for (int k = 0; k < pointsSorted.size(); k++) {
                double currentSlope = pointsSorted.get(k).slopeTo(origin);
                // StdOut.println("k: " + k + ", point: " + pointsSorted.get(k).toString() + ",slope: " + currentSlope);
                if (k > 0 && (currentSlope != preSlope || k == pointsSorted.size() - 1)) {
                    int end = currentSlope != preSlope ? k - 1 : k;
                    // Hit a different value or reach an end element
                    if (end - start + 1 >= 3) {
                        // create points
                        Point[] pointsInline = new Point[end - start + 2];
                        for (int m = 0; m < end - start + 1; m++) {
                            pointsInline[m] = pointsSorted.get(m + start);
                        }
                        // Add an origin point
                        pointsInline[pointsInline.length - 1] = origin;

                        // and sort
                        Arrays.sort(pointsInline);

                        // create segment and add to collection
                        LineSegment a = new LineSegment(pointsInline[0], pointsInline[pointsInline.length - 1]);

                        boolean found = false;
                        for (LineSegment s : segmentsAL) {
                            if (s.toString().equals(a.toString())) found = true;
                        }
                        if (!found) {
                            segmentsAL.add(a);
                            // StdOut.println("Added: " + a.toString());
                        }
                    }
                }

                if (currentSlope != preSlope) {
                    start = k;
                }
                preSlope = currentSlope;
            }
        }

        segments = segmentsAL.toArray(new LineSegment[segmentsAL.size()]);
        return segments;
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // print the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}
