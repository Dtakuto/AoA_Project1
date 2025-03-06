import java.util.*;

class ConvexHullDC {

    public static List<Point> convexHullDivideAndConquer(List<Point> points) {
        if (points.size() <= 1) return points;


        Collections.sort(points);

        //Divide the points into two halves
        int mid = points.size() / 2;
        List<Point> leftHull = convexHullDivideAndConquer(new ArrayList<>(points.subList(0, mid)));
        List<Point> rightHull = convexHullDivideAndConquer(new ArrayList<>(points.subList(mid, points.size())));

        //Merge the two convex hulls
        return mergeHulls(leftHull, rightHull);
    }

    private static List<Point> mergeHulls(List<Point> leftHull, List<Point> rightHull) {
        return null; 
    }

    private static int findUpperTangent(List<Point> leftHull, List<Point> rightHull) {

        //Rightmost point on left hull
        int leftRM = leftHull.get(leftHull.size() - 1);
        //Leftmost point on right hull
        int rightLM = rightHull.get(0);

        //Calls crossProduct to see if next point on right hull is above the current
        do {

            //for right hull to move point clockwise if not uppermost
            boolean moveClockwise = false;
            //for left hull to move point counter clockwise if not uppermost
            boolean moveCounterClockwise = false;

            while (crossProduct(points.get(leftRM), points.get(rightLM), points.get((rightLM + 1) % points.size())) > 0) {

                //move LEFTMOST point on right hull CLOCKWISE
                rightLM = (rightLM + 1) % points.size();
                moveClockwise = true;
            }
            while (crossProduct(points.get(rightLM), points.get(leftRM), points.get((leftRM - 1) % points.size())) < 0) {

                //move RIGHTMOST point on left hull COUNTER clockwise
                leftRM = (leftRM - 1 + points.size()) % points.size();
                moveCounterClockwise = true;
            }
        }
        while (moveClockwise || moveCounterClockwise);
        //x,y points for both points on upper tangent
        return new int[]{leftRM, rightLM};
    }

    private static int[] findLowerTangent(List<Integer> leftHull, List<Integer> rightHull, List<Point> points) {
        int leftRM = leftHull.get(leftHull.size() - 1);
        int rightLM = rightHull.get(0);

        do {
            boolean moveClockwise = false;
            boolean moveCounterClockwise = false;

            while (crossProduct(points.get(leftRM), points.get(rightLM), points.get((rightLM - 1 + points.size()) % points.size())) < 0) {
                rightLM = (rightLM - 1 + points.size()) % points.size();
                moveCounterClockwise = true;
            }

            while (crossProduct(points.get(rightLM), points.get(leftRM), points.get((leftRM + 1) % points.size())) > 0) {
                leftRM = (leftRM + 1) % points.size();
                moveClockwise = true;
            }

        } 
        while (moveClockwise || moveCounterClockwise);
        return new int[]{leftRM, rightLM};
    }


    private static int findLeftmost(List<Point> hull) {
        return 0; // Placeholder implementation
    }
}