 // Merge two hulls by finding the upper and lower tangent
 private List<Integer> mergeHulls(List<Integer> leftHull, List<Integer> rightHull) {
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