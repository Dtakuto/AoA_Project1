public class Point implements Comparable<Point>{
    int x;
    int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point p) {
        return this.x != p.x ? this.x - p.x : this.y - p.y;
    }

    //Computes product of vectors (O -> a) and (O -> B)
    public static int crossProduct(Point O, Point A, Point B){
        return (A.x - O.x) * (B.y - O.y) - (A.y - O.y) * (B.x - O.x);
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
