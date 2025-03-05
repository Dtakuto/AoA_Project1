import java.util.*;

class ConvexHull {
    static class Point implements Comparable<Point> {
        int x, y;
        
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public int compareTo(Point p) {
            return this.x != p.x ? this.x - p.x : this.y - p.y;
        }
    }
    
    private static int crossProduct(Point o, Point a, Point b) {
        return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
    }
    
    public static List<Point> convexHull(Point[] points) {
        if (points.length <= 1) return Arrays.asList(points);
        
        Arrays.sort(points);
        
        List<Point> lower = new ArrayList<>();
        for (Point p : points) {
            while (lower.size() >= 2 && crossProduct(lower.get(lower.size() - 2), lower.get(lower.size() - 1), p) <= 0) {
                lower.remove(lower.size() - 1);
            }
            lower.add(p);
        }
        
        List<Point> upper = new ArrayList<>();
        for (int i = points.length - 1; i >= 0; i--) {
            Point p = points[i];
            while (upper.size() >= 2 && crossProduct(upper.get(upper.size() - 2), upper.get(upper.size() - 1), p) <= 0) {
                upper.remove(upper.size() - 1);
            }
            upper.add(p);
        }
        
        lower.remove(lower.size() - 1);
        upper.remove(upper.size() - 1);
        lower.addAll(upper);
        return lower;
    }
    
    public static void main(String[] args) {
        Random rand = new Random();
        Point[] points = new Point[20];
        for (int i = 0; i < 20; i++) {
            points[i] = new Point(rand.nextInt(100), rand.nextInt(100));
        }
        
        List<Point> hull = convexHull(points);
        System.out.println("Convex Hull Points:");
        for (Point p : hull) {
            System.out.println("(" + p.x + ", " + p.y + ")");
        }
    }
}
