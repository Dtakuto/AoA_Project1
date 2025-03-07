import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class ConvexHullDC {
    public static List<Point> convexHullDivideAndConquer(List<Point> points) {
        points.sort(Comparator.naturalOrder()); // Sort by x-coordinates
        return computeHull(points);
    }

    private static List<Point> computeHull(List<Point> points) {
        if (points.size() <= 2) return new ArrayList<>(points);

        int mid = points.size() / 2;
        List<Point> leftHull = computeHull(points.subList(0, mid));
        List<Point> rightHull = computeHull(points.subList(mid, points.size()));

        return mergeHulls(leftHull, rightHull);
    }

    private static List<Point> mergeHulls(List<Point> leftHull, List<Point> rightHull) {
        int leftRightmost = 0, rightLeftmost = 0;

        for (int i = 1; i < leftHull.size(); i++) {
            if (leftHull.get(i).x > leftHull.get(leftRightmost).x) leftRightmost = i;
        }

        for (int i = 1; i < rightHull.size(); i++) {
            if (rightHull.get(i).x < rightHull.get(rightLeftmost).x) rightLeftmost = i;
        }

        int upperLeft = leftRightmost, upperRight = rightLeftmost;
        boolean upperDone = false;
        while (!upperDone) {
            upperDone = true;
            while (true) {
                int next = (upperLeft + 1) % leftHull.size();
                if (Point.crossProduct(leftHull.get(next), leftHull.get(upperLeft), rightHull.get(upperRight)) > 0) {
                    upperLeft = next;
                    upperDone = false;
                } else break;
            }
            while (true) {
                int next = (upperRight - 1 + rightHull.size()) % rightHull.size();
                if (Point.crossProduct(leftHull.get(upperLeft), rightHull.get(next), rightHull.get(upperRight)) < 0) {
                    upperRight = next;
                    upperDone = false;
                } else break;
            }
        }

        int lowerLeft = leftRightmost, lowerRight = rightLeftmost;
        boolean lowerDone = false;
        while (!lowerDone) {
            lowerDone = true;
            while (true) {
                int next = (lowerLeft - 1 + leftHull.size()) % leftHull.size();
                if (Point.crossProduct(leftHull.get(next), leftHull.get(lowerLeft), rightHull.get(lowerRight)) < 0) {
                    lowerLeft = next;
                    lowerDone = false;
                } else break;
            }
            while (true) {
                int next = (lowerRight + 1) % rightHull.size();
                if (Point.crossProduct(leftHull.get(lowerLeft), rightHull.get(next), rightHull.get(lowerRight)) > 0) {
                    lowerRight = next;
                    lowerDone = false;
                } else break;
            }
        }

        List<Point> mergedHull = new ArrayList<>();
        int idx = upperLeft;
        while (idx != lowerLeft) {
            mergedHull.add(leftHull.get(idx));
            idx = (idx + 1) % leftHull.size();
        }
        mergedHull.add(leftHull.get(lowerLeft));

        idx = lowerRight;
        if (upperRight != lowerRight) {
            while (idx != upperRight) {
                mergedHull.add(rightHull.get(idx));
                idx = (idx + 1) % rightHull.size();
            }
        }

        if (!mergedHull.contains(rightHull.get(upperRight))) {
            mergedHull.add(rightHull.get(upperRight));
        }

        return mergedHull;
    }
}
