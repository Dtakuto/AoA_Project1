public class Point implements Comparable<Point>{
    double x;
    double y;
    public String index;

    public Point(Double x, Double y, int index2){
        this.x = x;
        this.y = y;
        this.index = String.valueOf(index2);  // Store index properly
    }
    

    @Override
    public int compareTo(Point p) {
        return Double.compare(this.x, p.x) != 0 ? Double.compare(this.x, p.x) : Double.compare(this.y, p.y);
    }

    //Computes product of vectors (O -> a) and (O -> B)
    public static double crossProduct(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }


}