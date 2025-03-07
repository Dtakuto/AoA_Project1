import java.util.*;

public class ConvexHullDC {

    public static List<Point> convexHullDivideAndConquer(List<Point> points) {
        if (points.size() <= 2) {
            return points;  // Ensure at least 2 points are returned
        }

        // Sort points before processing
        Collections.sort(points, new Comparator<Point>() {
        @Override
        public int compare(Point p1, Point p2) {
            return Double.compare(p1.x, p2.x);
          }
        });
        System.out.println("Sorted Points: " + points);

        // Divide the points into two halves
        int mid = points.size() / 2;
        List<Point> leftHull = convexHullDivideAndConquer(points.subList(0, mid));
        List<Point> rightHull = convexHullDivideAndConquer(points.subList(mid, points.size()));

        System.out.println("All points before merging: " + leftHull + " + " + rightHull);


        List<Point> mergedHull = mergeHulls(leftHull, rightHull);

        System.out.println("Merged hull size: " + mergedHull.size() + " Points: " + mergedHull);
        if (mergedHull.isEmpty()) {
            System.out.println("Error: Merged hull is empty!");
        }   
        return mergedHull;
    }

    static List<Point> mergeHulls(List<Point> leftHull, List<Point> rightHull) {
        // Find the upper and lower tangents
        int upperLeft = leftHull.size() - 1;
        int upperRight = 0;
        int lowerLeft = leftHull.size() - 1;
        int lowerRight = 0;
    
        boolean upperDone = false;
        while (!upperDone) {
            upperDone = true;
            while (crossProduct(rightHull.get(upperRight), leftHull.get(upperLeft), leftHull.get((upperLeft - 1 + leftHull.size()) % leftHull.size())) < 0) {
                upperLeft = (upperLeft - 1 + leftHull.size()) % leftHull.size();
            }
            while (crossProduct(leftHull.get(upperLeft), rightHull.get(upperRight), rightHull.get((upperRight + 1) % rightHull.size())) > 0) {
                upperRight = (upperRight + 1) % rightHull.size();
                upperDone = false;
            }
        }
    
        boolean lowerDone = false;
        while (!lowerDone) {
            lowerDone = true;
            while (crossProduct(rightHull.get(lowerRight), leftHull.get(lowerLeft), leftHull.get((lowerLeft + 1) % leftHull.size())) > 0) {
                lowerLeft = (lowerLeft + 1) % leftHull.size();
            }
            while (crossProduct(leftHull.get(lowerLeft), rightHull.get(lowerRight), rightHull.get((rightHull.size() + lowerRight - 1) % rightHull.size())) < 0) {
                lowerRight = (rightHull.size() + lowerRight - 1) % rightHull.size();
                lowerDone = false;
            }
        }
    
        // Merging process: Walk from upperLeft to lowerLeft on leftHull, then from lowerRight to upperRight on rightHull
        List<Point> mergedHull = new ArrayList<>();
    
        // Add points from left hull
        int idx = upperLeft;
        while (idx != lowerLeft) {
            mergedHull.add(leftHull.get(idx));
            idx = (idx + 1) % leftHull.size();
        }
        mergedHull.add(leftHull.get(lowerLeft));
    
        // Add points from right hull
        idx = lowerRight;
        while (idx != upperRight) {
            mergedHull.add(rightHull.get(idx));
            idx = (idx + 1) % rightHull.size();
        }
        mergedHull.add(rightHull.get(upperRight));
    
        System.out.println("Final Merged Hull: " + mergedHull);
        return mergedHull;
    }
    
    // Find upper tangent
    static int findUpperTangent(List<Point> leftHull, List<Point> rightHull) {
        int upperLeft = leftHull.size() - 1;
        int upperRight = 0;

        boolean done = false;
        while (!done) {
            done = true;
            while (crossProduct(rightHull.get(upperRight), leftHull.get(upperLeft), leftHull.get((upperLeft - 1 + leftHull.size()) % leftHull.size())) < 0) {
                upperLeft = (upperLeft - 1 + leftHull.size()) % leftHull.size();
            }
            while (crossProduct(leftHull.get(upperLeft), rightHull.get(upperRight), rightHull.get((upperRight + 1) % rightHull.size())) > 0) {
                upperRight = (upperRight + 1) % rightHull.size();
                done = false;
            }
        }

        System.out.println("Upper tangent found: " + leftHull.get(upperLeft) + " -> " + rightHull.get(upperRight));
        return upperRight;
    }

    // Find lower tangent
    static int findLowerTangent(List<Point> leftHull, List<Point> rightHull) {
        int lowerLeft = leftHull.size() - 1;
        int lowerRight = 0;

        boolean done = false;
        while (!done) {
            done = true;
            while (crossProduct(rightHull.get(lowerRight), leftHull.get(lowerLeft), leftHull.get((lowerLeft + 1) % leftHull.size())) > 0) {
                lowerLeft = (lowerLeft + 1) % leftHull.size();
            }
            while (crossProduct(leftHull.get(lowerLeft), rightHull.get(lowerRight), rightHull.get((rightHull.size() + lowerRight - 1) % rightHull.size())) < 0) {
                lowerRight = (rightHull.size() + lowerRight - 1) % rightHull.size();
                done = false;
            }
        }

        System.out.println("Lower tangent found: " + leftHull.get(lowerLeft) + " -> " + rightHull.get(lowerRight));
        return lowerRight;
    }

    // Cross product calculation
    static double crossProduct(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    // Method to find the lower tangent
    static int findLowerTangent(List<Point> leftHull, List<Point> rightHull, int upperLeft, int upperRight) {
        int lowerLeft = upperLeft;
        int lowerRight = upperRight;

        System.out.println("Finding lower tangent...");
        boolean lowerTangentFound = false;
        while (!lowerTangentFound) {
            lowerTangentFound = true;
            while (crossProduct(leftHull.get(lowerLeft), rightHull.get(lowerRight),
                    rightHull.get((lowerRight - 1 + rightHull.size()) % rightHull.size())) > 0) {
                lowerRight = (lowerRight - 1 + rightHull.size()) % rightHull.size();
                lowerTangentFound = false;
            }
            while (crossProduct(rightHull.get(lowerRight), leftHull.get(lowerLeft),
                    leftHull.get((lowerLeft + 1) % leftHull.size())) < 0) {
                lowerLeft = (lowerLeft + 1) % leftHull.size();
                lowerTangentFound = false;
            }
        }

        System.out.println("Lower tangent found: " + leftHull.get(lowerLeft) + " -> " + rightHull.get(lowerRight));
        return lowerRight;
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