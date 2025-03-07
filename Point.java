public class Point implements Comparable<Point> {
    double x;
    double y;
    public String index;

    public Point(Double x, Double y, int index2) {
        this.x = x;
        this.y = y;
        this.index = String.valueOf(index2);  // Store index properly
    }
    
    @Override
    public int compareTo(Point p) {
        return Double.compare(this.x, p.x) != 0 ? Double.compare(this.x, p.x) : Double.compare(this.y, p.y);
    }

    public static int crossProduct(Point O, Point A, Point B) {
        double result = (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
        if (Math.abs(result) < 1e-9) return 0;  // Treat very small values as 0 (collinear case)
        return Double.compare(result, 0);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
