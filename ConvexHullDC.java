import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ConvexHullDC {

    public static List<Point> convexHullDivideAndConquer(List<Point> points) {
        // Base case: if the number of points is less than or equal to 3, return the points
        if (points.size() <= 3) {
            List<Point> hull = new ArrayList<>(points);
            // Ensure the points are in counter-clockwise order
            if (points.size() == 3 && Point.crossProduct(points.get(0), points.get(1), points.get(2)) < 0) {
                Collections.swap(hull, 1, 2);
            }
            return hull;
        }

        // Sort points before processing
        Collections.sort(points, Comparator.comparingDouble(p -> p.x));
        System.out.println("Sorted Points: " + points);

        // Divide the points into two halves
        // Prevent infinite recursion by ensuring proper division
    int mid = Math.max(1, points.size() / 2);

    // Avoid reprocessing the same points infinitely
    if (mid >= points.size()) mid = points.size() - 1;

    List<Point> leftHull = convexHullDivideAndConquer(new ArrayList<>(points.subList(0, mid)));
    List<Point> rightHull = convexHullDivideAndConquer(new ArrayList<>(points.subList(mid, points.size())));


        System.out.println("All points before merging: " + leftHull + " + " + rightHull);

        // Example usage of findRightmost method
        if (leftHull.size() < 2 || rightHull.size() < 2) {
            System.out.println("Error: One of the hulls has fewer than 2 points!");
            return new ArrayList<>(); 
        }

        int rightmostIndex = findRightmost(leftHull);
        System.out.println("Rightmost point in left hull: " + leftHull.get(rightmostIndex));
        int leftmostIndex = findLeftmost(rightHull);
        System.out.println("Leftmost point in right hull: " + rightHull.get(leftmostIndex));

        // Use rightmostIndex as the starting point for finding the lower tangent
        List<Point> mergedHull = mergeHulls(leftHull, rightHull, rightmostIndex, leftmostIndex);

        System.out.println("Merged hull size: " + mergedHull.size() + " Points: " + mergedHull);
        if (mergedHull.isEmpty()) {
            System.out.println("Error: Merged hull is empty!");
        }   
        return mergedHull;
    }

    static List<Point> mergeHulls(List<Point> leftHull, List<Point> rightHull, int rightmostIndex, int leftmostIndex) {
        int upperLeft = findUpperTangent(leftHull, rightHull);
        int lowerLeft = findLowerTangent(leftHull, rightHull, rightmostIndex, leftmostIndex);
    
        List<Point> mergedHull = new ArrayList<>();
    
        // Traverse left hull from lowerLeft to upperLeft **only once**
        int i = lowerLeft;
        do {
            mergedHull.add(leftHull.get(i));
            i = (i + 1) % leftHull.size(); 
        } while (i != (upperLeft + 1) % leftHull.size());
    
        // Traverse right hull from upperRight to lowerRight **only once**
        int j = leftmostIndex;
        do {
            mergedHull.add(rightHull.get(j));
            j = (j + 1) % rightHull.size();
        } while (j != (rightmostIndex + 1) % rightHull.size());
    
        System.out.println("Final Merged Hull: " + mergedHull);
        return mergedHull;
    }

    // Find lower tangent
    static int findLowerTangent(List<Point> leftHull, List<Point> rightHull, int rightmostIndex, int leftmostIndex) {
        int lowerLeft = rightmostIndex;
        int lowerRight = leftmostIndex;
        
        boolean done = false;
        while (!done) {
            done = true;
    
            // Move lowerLeft clockwise while it's still making a right turn
            while (Point.crossProduct(rightHull.get(lowerRight), leftHull.get(lowerLeft),
                                      leftHull.get((lowerLeft + 1) % leftHull.size())) > 0) {
                lowerLeft = (lowerLeft + 1) % leftHull.size();
                done = false;
            }
    
            // Move lowerRight counter-clockwise while it's still making a right turn
            while (Point.crossProduct(leftHull.get(lowerLeft), rightHull.get(lowerRight),
                                      rightHull.get((lowerRight - 1 + rightHull.size()) % rightHull.size())) < 0) {
                lowerRight = (lowerRight - 1 + rightHull.size()) % rightHull.size();
                done = false;
            }
        }
        
        return lowerRight;
    }

    // Find upper tangent
    static int findUpperTangent(List<Point> leftHull, List<Point> rightHull) {
        int upperLeft = leftHull.size() - 1;
        int upperRight = 0;

        System.out.println("Finding upper tangent...");
        boolean upperTangentFound = false;
        while (!upperTangentFound) {
            upperTangentFound = true;
            while (Point.crossProduct(rightHull.get(upperRight), leftHull.get(upperLeft), 
                    leftHull.get((upperLeft - 1 + leftHull.size()) % leftHull.size())) < 0) {
                upperLeft = (upperLeft - 1 + leftHull.size()) % leftHull.size();
                upperTangentFound = false;
            }
            while (Point.crossProduct(leftHull.get(upperLeft), rightHull.get(upperRight), 
                    rightHull.get((upperRight + 1) % rightHull.size())) > 0) {
                upperRight = (upperRight + 1) % rightHull.size();
                upperTangentFound = false;
            }
        }

        System.out.println("Upper tangent found: " + leftHull.get(upperLeft) + " -> " + rightHull.get(upperRight));
        return upperRight;
    }

    // Find rightmost point in hull
    private static int findRightmost(List<Point> hull) {
        if (hull.size() == 1) return 0;
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

    // Find leftmost point in hull
    private static int findLeftmost(List<Point> hull) {
        if (hull.size() == 1) return 0;
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

