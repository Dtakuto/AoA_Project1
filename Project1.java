import java.io.*;
import java.util.*;

public class Project1 {

    public static List<Point> readFile(String filePath) throws Exception {
        File file = new File(filePath);
        List<Point> points = new ArrayList<>();
        BufferedReader buff = new BufferedReader(new FileReader(file));

        String line;
        int index = 0;

        while ((line = buff.readLine()) != null) {
            String[] parts = line.split(",");
            Double x = Double.parseDouble(parts[0]);
            Double y = Double.parseDouble(parts[1]);
            points.add(new Point(x, y, index++));
        }
        buff.close();
        return points;
    }

    public static void writeFile(String filePath, List<Point> hull) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (Point p : hull) {
            writer.write(p.index + "\n");  // Write the index of the hull points
        }
        writer.close();
    }

    public static void main(String[] args) throws Exception {
        // Step 1: Read input points from the CSV file
        System.out.println("Code works");

        List<Point> points = readFile("input.csv");

        // Step 2: Compute Convex Hull using Divide and Conquer
        List<Point> hull = ConvexHullDC.convexHullDivideAndConquer(points);

        // Step 3: Write the output to output.txt
        writeFile("testOutput.txt", hull);

        System.out.println("Convex Hull computation complete! Output saved in output.txt.");
        // Step 3: Write the convex hull indices to the
    }
}