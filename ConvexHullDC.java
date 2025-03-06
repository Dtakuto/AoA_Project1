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
        int inda = findRightmost(leftHull);
        int indb = findLeftmost(rightHull);

        int upperLeft = inda;
        int upperRight = indb;

        //Computes the upper tangent 
        while (true){
            boolean upperTangetDone = false;

            while(Point.crossProduct(rightHull.get(upperRight), leftHull.get(upperLeft), leftHull.get((upperLeft + 1) % leftHull.size())) > 0) {
                upperLeft = (upperLeft + 1) % leftHull.size();
                upperTangetDone = true;
            }

            while(Point.crossProduct(leftHull.get(upperLeft), rightHull.get(upperRight), rightHull.get((rightHull.size() + upperRight + 1 ) % rightHull.size())) < 0){
                upperRight = (rightHull.size() + upperRight - 1) % rightHull.size();
                upperTangetDone = true;
            }
            if (!upperTangetDone) break;
        }

        int lowLeft = inda;
        int lowRight = indb; 

        //Computes the lower tanget
        while (true){
            boolean lowerTangetDone = false;

            while(Point.crossProduct(rightHull.get(lowRight), leftHull.get(lowLeft), leftHull.get((lowLeft + 1) % leftHull.size())) > 0) {
                lowLeft = (lowLeft + 1) % leftHull.size();
                lowerTangetDone = true;
            }

            while(Point.crossProduct(leftHull.get(lowLeft), rightHull.get(lowRight), rightHull.get((rightHull.size() + lowRight + 1 ) % rightHull.size())) < 0){
                lowLeft = (rightHull.size() + upperRight - 1) % rightHull.size();
                lowerTangetDone = true;
            }
            if (!lowerTangetDone) break;
        }

        List <Point> mergeList = new ArrayList<>(); 

        int i = upperLeft;
        while (i != lowLeft){
            mergeList.add(leftHull.get(i));
            i = (i + 1) % leftHull.size();
        }

        mergeList.add(leftHull.get(lowLeft));

        i = lowRight;
        while (i != upperRight) {
            mergeList.add(rightHull.get(i));
            i = (i + 1) % rightHull.size();
        }

        mergeList.add(rightHull.get(upperRight));

        return mergeList;
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
