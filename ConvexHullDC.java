import java.util.*;

class ConvexHullDC {

    public static List<Point> convexHullDivideAndConquer(List<Point> points) {
        if (points.size() <= 1) return points;


        Collections.sort(points);

        //Divide the points into two halves
        int mid = points.size() / 2;
        List<Point> leftHull = convexHullDivideAndConquer(points.subList(0, mid));
        List<Point> rightHull = convexHullDivideAndConquer(points.subList(mid, points.size()));

        //Merge the two convex hulls
        return mergeHulls(leftHull, rightHull);
    }

    private static List<Point> mergeHulls(List<Point> leftHull, List<Point> rightHull) {
        return null; 
    }

    private static int findRightmost(List<Point> hull) {
        int index = 0; 
        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(i).x > hull.get(index).x) {
                index = i;
            }
        }
        return index;
    }

    private static int findLeftmost(List<Point> hull) {
        int index = 0;
        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(i).x < hull.get(index).x) {
                index = i;
            }
        }
        return index;
    }
}
