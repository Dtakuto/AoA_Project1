import java.util.*;

class ConvexHullDC {

    public static List<Point> convexHullDivideAndConquer(List<Point> points) {
        if (points.size() <= 2) {
            return new ArrayList<>(points);  // Ensure at least 2 points are returned
        }

        // Sort points by x-coordinate to maintain correct order
        points.sort(Comparator.comparingDouble(p -> p.x));

        // Divide the points into two halves
        int mid = points.size() / 2;
        List<Point> leftHull = convexHullDivideAndConquer(new ArrayList<>(points.subList(0, mid)));
        List<Point> rightHull = convexHullDivideAndConquer(new ArrayList<>(points.subList(mid, points.size())));

        List<Point> mergedHull = mergeHulls(leftHull, rightHull);
        
        System.out.println("Merged hull size: " + mergedHull.size() + " Points: " + mergedHull);
        if (mergedHull.isEmpty()) {
            System.out.println("Error: Merged hull is empty!");
        }   
        return mergedHull;
    }

    private static List<Point> mergeHulls(List<Point> leftHull, List<Point> rightHull) {
        System.out.println("\nMerging hulls:");
        System.out.println("Left Hull: " + leftHull);
        System.out.println("Right Hull: " + rightHull);
    
        int upperLeft = findRightmost(leftHull);
        int upperRight = findLeftmost(rightHull);
    
        // Compute upper tangent
        boolean upperTangentFound = false;
        while (!upperTangentFound) {
            upperTangentFound = true;
            while (Point.crossProduct(leftHull.get(upperLeft), rightHull.get(upperRight),
                    rightHull.get((upperRight + 1) % rightHull.size())) < 0) {
                upperRight = (upperRight + 1) % rightHull.size();
                upperTangentFound = false;
            }
            while (Point.crossProduct(rightHull.get(upperRight), leftHull.get(upperLeft),
                    leftHull.get((upperLeft - 1 + leftHull.size()) % leftHull.size())) > 0) {
                upperLeft = (upperLeft - 1 + leftHull.size()) % leftHull.size();
                upperTangentFound = false;
            }
        }
    
        int lowerLeft = upperLeft;
        int lowerRight = upperRight;
    
        boolean lowerTangentFound = false;
        while (!lowerTangentFound) {
            lowerTangentFound = true;
            while (Point.crossProduct(leftHull.get(lowerLeft), rightHull.get(lowerRight),
                    rightHull.get((lowerRight - 1 + rightHull.size()) % rightHull.size())) > 0) {
                lowerRight = (lowerRight - 1 + rightHull.size()) % rightHull.size();
                lowerTangentFound = false;
            }
            while (Point.crossProduct(rightHull.get(lowerRight), leftHull.get(lowerLeft),
                    leftHull.get((lowerLeft + 1) % leftHull.size())) < 0) {
                lowerLeft = (lowerLeft + 1) % leftHull.size();
                lowerTangentFound = false;
            }
        }
    
        // Debugging: Print the computed tangents
        System.out.println("Upper tangent: Left[" + upperLeft + "] -> Right[" + upperRight + "]");
        System.out.println("Lower tangent: Left[" + lowerLeft + "] -> Right[" + lowerRight + "]");
    
        List<Point> mergedHull = new ArrayList<>();
    
        // Traverse left hull from upperLeft to lowerLeft
        int i = upperLeft;
        while (true) {
            mergedHull.add(leftHull.get(i));
            if (i == lowerLeft) break;
            i = (i + 1) % leftHull.size();
        }

        // Traverse right hull from lowerRight to upperRight
        i = lowerRight;
        while (true) {
            mergedHull.add(rightHull.get(i));
            if (i == upperRight) break;
            i = (i + 1) % rightHull.size();
        }

        // Debugging: Print the final merged hull
        System.out.println("Final Merged Hull:");
        for (Point p : mergedHull) {
            System.out.println("Point included: " + p);
        }
    
        return mergedHull;
    }
    
    private static int findRightmost(List<Point> hull) {
        int index = 0;
        System.out.println("Finding rightmost point in hull: " + hull);
        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(i).x > hull.get(index).x) {
                index = i;
            }
        }
        System.out.println("Rightmost point found: " + hull.get(index));
        return index;
    }
    
    private static int findLeftmost(List<Point> hull) {
        int index = 0;
        System.out.println("Finding leftmost point in hull: " + hull);
        for (int i = 1; i < hull.size(); i++) {
            if (hull.get(i).x < hull.get(index).x) {
                index = i;
            }
        }
        System.out.println("Leftmost point found: " + hull.get(index));
        return index;
    }
}