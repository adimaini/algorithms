import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.Math;

public class JarvisMarchConvexHull {
    public static void main(String args[]) {
        System.out.println("Inputting data points...");

        List<Point> results = new ArrayList<>();
        List<Point> results_pareto = new ArrayList<>();

        // make new objects
        pointGenerator generator_object = new pointGenerator();
        JarvisMarchConvexHull results_object = new JarvisMarchConvexHull();

        results = results_object.findConvexHull(generator_object.generator());
        results_pareto = results_object.findParetoPoints(results, generator_object.generator());
    }

    public List<Point> findConvexHull(Point[] points) {
        //first find the leftmost value
        Point start = points[0];
        for (int i = 1; i < points.length; i++) {
            if (points[i].x < start.x) {
                start = points[i];
            }
        }
        Point current = start;
        //use set because this algorithm might try to insert duplicate point.
        List<Point> result = new ArrayList<>();
        result.add(start);
        List<Point> collinearPoints = new ArrayList<>();
        while (true) {
            Point nextTarget = points[0];
            for (int i = 1; i < points.length; i++) {
                if (points[i] == current) {
                    continue;
                }
                int val = crossProduct(current, nextTarget, points[i]);
                //if val > 0 it means points[i] is on left of current -> nextTarget. Make it the nextTarget.
                if (val > 0) {
                    nextTarget = points[i];
                    //reset collinear points because we now have a new nextTarget.
                    collinearPoints = new ArrayList<>();
                } 
                else if (val == 0) { //if val is 0 then collinear current, nextTarget and points[i] are collinear.
                    //if its collinear point then pick the further one but add closer one to list of collinear points.
                    if (distance(current, nextTarget, points[i]) < 0) {
                        collinearPoints.add(nextTarget);
                        nextTarget = points[i];
                    } 
                    else { //just add points[i] to collinearPoints list. If nextTarget indeed is the next point on
                        //convex then all points in collinear points will be also on boundary.
                        collinearPoints.add(points[i]);
                    }
                }
                //else if val < 0 then nothing to do since points[i] is on right side of current -> nextTarget.
            }

            //add all points in collinearPoints to result.
            for (Point p : collinearPoints) {
                result.add(p);
            }
            //if nextTarget is same as start it means we have formed an envelope and its done.
            if (nextTarget == start) {
                break;
            }
            //add nextTarget to result and set current to nextTarget.
            result.add(nextTarget);
            current = nextTarget;
        }
        return new ArrayList<>(result);
    }

    /**
     * Returns < 0 if 'b' is closer to 'a' compared to 'c', == 0 if 'b' and 'c' are same distance from 'a'
     * or > 0 if 'c' is closer to 'a' compared to 'b'.
     */
    private int distance(Point a, Point b, Point c) {
        int y1 = a.y - b.y;
        int y2 = a.y - c.y;
        int x1 = a.x - b.x;
        int x2 = a.x - c.x;
        return Integer.compare(y1 * y1 + x1 * x1, y2 * y2 + x2 * x2);
    }

    /**
     * Cross product to find where c belongs in reference to vector ab.
     * If result > 0 it means 'c' is on left of ab
     *    result == 0 it means 'a','b' and 'c' are collinear
     *    result < 0  it means 'c' is on right of ab
     */
    private int crossProduct(Point a, Point b, Point c) {
        int y1 = a.y - b.y;
        int y2 = a.y - c.y;
        int x1 = a.x - b.x;
        int x2 = a.x - c.x;
        return y2 * x1 - y1 * x2;
    }

    // Calculates the angle between a and b to determine if it is a Pareto point or not
    // if angle is within 270 to 360 degrees, then b is a pareto point
    private float getAngle(Point a, Point b) {
        float angle = (float) Math.toDegrees(Math.atan2(b.y - a.y, b.x - a.x));

        if(angle < 0){
            angle += 360;
        }
        return angle;
    }

    // findParetoPoints takes as inputs 'result' from findConvexHull and the array 'points' to calculate
    // and find the pareto points based on the polar degree between each vertices from the convex hull. 
    public List<Point> findParetoPoints(List<Point> result, Point[] points) {
        Set<Point> result_pareto = new HashSet<>();

        // converting the input result list array to a point.
        Point[] result_array = result.toArray(new Point[result.size()]);
        Point y_max = result_array[0];
        for (int i = 1; i < result_array.length; i++) {
            if (result_array[i].y > y_max.y) {
                y_max = result_array[i];
            }
        }
        // adding the first point that Jarvis March starts on. This is a pareto-optimal point. 
        result_pareto.add(y_max);

        // //converting the input points to array for printout
        // Point[] input_array = points.toArray(new Point[points.size()]);
        // // print statements for input results
        // System.out.println("The input points were:");
        // for (Point p: input_array){
        //     System.out.println("X:" + p.x + " Y:" + p.y);
        // }


        // print statements for convex hull results
        System.out.println("The convex hull results are:");
        for (Point p: result_array){
            System.out.println("X:" + p.x + " Y:" + p.y);
        }

        for(int i = 0; i < result_array.length-1; i++){
            if ( (getAngle(result_array[i], result_array[i+1])) < 360 && (getAngle(result_array[i], result_array[i+1]) > 270) ) {
                result_pareto.add(result_array[i+1]);
            }
        }

        // print statements for pareto point results
        System.out.println("The pareto points are:");
        for (Point p: result_pareto){
            System.out.println("X:" + p.x + " Y:" + p.y);
        }

        return new ArrayList<>(result_pareto);

    }

}