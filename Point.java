public class Point implements Comparable<Point>{
    double x;
    double y;
    public String index;

    public Point(Double x, Double y, int index2){
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point p) {
        return (int) (this.x != p.x ? this.x - p.x : this.y - p.y);
    }

    //Computes product of vectors (O -> a) and (O -> B)
    public static int crossProduct(Point O, Point A, Point B){
        double result = (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
        if (Math.abs(result) < 1e-9) return 0;  // Treat very small values as 0 (collinear case)
        return (int) result;
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
