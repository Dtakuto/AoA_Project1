import java.util.*;

public class ConvexHullDC {
    private static List<Point> points; // This will store all points

    public static List<Point> convexHullDivideAndConquer(List<Point> inputPoints) {
        points = new ArrayList<>(inputPoints); // Store all points
        
        if (points.size() <= 2) {
            return points;  // Ensure at least 2 points are returned
        }

        // Sort points by x-coordinate
        Collections.sort(points);
        System.out.println("Sorted Points: " + points);

        // Create list of indices instead of actual points
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            indices.add(i);
        }

        // Call the divide and conquer algorithm on indices
        List<Integer> hullIndices = divideAndConquerHull(indices);
        
        // Convert indices back to points
        List<Point> hull = new ArrayList<>();
        for (Integer idx : hullIndices) {
            hull.add(points.get(idx));
        }

        System.out.println("Merged hull size: " + hull.size() + " Points: " + hull);
        if (hull.isEmpty()) {
            System.out.println("Error: Merged hull is empty!");
        }
        return hull;
    }

    // Recursive method to compute convex hull with indices
    private static List<Integer> divideAndConquerHull(List<Integer> indices) {
        if (indices.size() <= 2) {
            return new ArrayList<>(indices);
        }

        // Divide the indices into two halves
        int mid = indices.size() / 2;
        List<Integer> leftIndices = new ArrayList<>(indices.subList(0, mid));
        List<Integer> rightIndices = new ArrayList<>(indices.subList(mid, indices.size()));

        // Recursively compute hulls for each half
        List<Integer> leftHull = divideAndConquerHull(leftIndices);
        List<Integer> rightHull = divideAndConquerHull(rightIndices);

        // Merge the two hulls
        return mergeHulls(leftHull, rightHull);
    }

    // Implement the mergeHulls method from the text file
    private static List<Integer> mergeHulls(List<Integer> leftHull, List<Integer> rightHull) {
        // Find the rightmost point of the left hull
        int leftRightmost = 0;
        for (int i = 1; i < leftHull.size(); i++) {
            if (points.get(leftHull.get(i)).x > points.get(leftHull.get(leftRightmost)).x) {
                leftRightmost = i;
            }
        }
        
        // Find the leftmost point of the right hull
        int rightLeftmost = 0;
        for (int i = 1; i < rightHull.size(); i++) {
            if (points.get(rightHull.get(i)).x < points.get(rightHull.get(rightLeftmost)).x) {
                rightLeftmost = i;
            }
        }
        
        // Find the upper tangent
        int upperLeft = leftRightmost;
        int upperRight = rightLeftmost;
        
        boolean upperDone = false;
        while (!upperDone) {
            upperDone = true;
            
            // Move counterclockwise on left hull
            while (true) {
                int next = (upperLeft + 1) % leftHull.size();
                Point a = points.get(leftHull.get(upperLeft));
                Point b = points.get(rightHull.get(upperRight));
                Point c = points.get(leftHull.get(next));
                
                if (crossProduct(c, a, b) > 0) {
                    upperLeft = next;
                    upperDone = false;
                } else {
                    break;
                }
            }
            
            // Move clockwise on right hull
            while (true) {
                int next = (upperRight - 1 + rightHull.size()) % rightHull.size();
                Point a = points.get(leftHull.get(upperLeft));
                Point b = points.get(rightHull.get(upperRight));
                Point c = points.get(rightHull.get(next));
                
                if (crossProduct(a, c, b) < 0) {
                    upperRight = next;
                    upperDone = false;
                } else {
                    break;
                }
            }
        }
        
        // Find the lower tangent
        int lowerLeft = leftRightmost;
        int lowerRight = rightLeftmost;
        
        boolean lowerDone = false;
        while (!lowerDone) {
            lowerDone = true;
            
            // Move clockwise on left hull
            while (true) {
                int next = (lowerLeft - 1 + leftHull.size()) % leftHull.size();
                Point a = points.get(leftHull.get(lowerLeft));
                Point b = points.get(rightHull.get(lowerRight));
                Point c = points.get(leftHull.get(next));
                
                if (crossProduct(c, a, b) < 0) {
                    lowerLeft = next;
                    lowerDone = false;
                } else {
                    break;
                }
            }
            
            // Move counterclockwise on right hull
            while (true) {
                int next = (lowerRight + 1) % rightHull.size();
                Point a = points.get(leftHull.get(lowerLeft));
                Point b = points.get(rightHull.get(lowerRight));
                Point c = points.get(rightHull.get(next));
                
                if (crossProduct(a, c, b) > 0) {
                    lowerRight = next;
                    lowerDone = false;
                } else {
                    break;
                }
            }
        }
        
        // Merge hulls by walking along the border
        List<Integer> mergedHull = new ArrayList<>();
        
        // Start at the upper left tangent point
        int idx = upperLeft;
        
        // Follow the left hull from upper tangent to lower tangent
        while (idx != lowerLeft) {
            mergedHull.add(leftHull.get(idx));
            idx = (idx + 1) % leftHull.size();
        }
        mergedHull.add(leftHull.get(lowerLeft));
        
        // Follow the right hull from lower tangent to upper tangent
        idx = lowerRight;
        if (upperRight != lowerRight) {
            while (idx != upperRight) {
                mergedHull.add(rightHull.get(idx));
                idx = (idx + 1) % rightHull.size();
            }
        }
        
        // Only add the upper right point if it's not already in the hull
        if (!mergedHull.contains(rightHull.get(upperRight))) {
            mergedHull.add(rightHull.get(upperRight));
        }
        
        return mergedHull;
    }
    
    // Helper method for cross product
    private static double crossProduct(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }
}