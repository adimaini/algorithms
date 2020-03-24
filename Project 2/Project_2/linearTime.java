import java.util.HashSet;
import java.util.Set;
import java.util.Collections;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class linearTime
{

	public static void main(String args[])
	{


		// make new objects
		linearTime linearTime_object = new linearTime();
		pointGenerator generator_object = new pointGenerator();

		// initialized and stored the sorted coordinateList for this iteration
		ArrayList<Point> coordinateList = linearTime_object.sortedPoints(generator_object.generator());

		// keep track of elapsed time of program. initialize startTime. 
		long startTime = System.nanoTime();

		//initialize pareto_results and store the results for this iteration
		Set<Point> pareto_results = new HashSet<>();
		pareto_results = linearTime_object.getPareto(coordinateList);
	
		// // printout for all points 
	 //    System.out.println("The points are:");
  //       for (Point p: coordinateList){
  //           System.out.println("X:" + p.x + " Y:" + p.y);
  //       }

  //       //printout for pareto results found
		// System.out.println("The pareto results are: ");
  //       for (Point p: pareto_results){
  //           System.out.println("X:" + p.x + " Y:" + p.y);
  //       }

        // Elapsed time calculation and print out
		long timeElapsed = System.nanoTime() - startTime;
		System.out.println("Execution time in nanoseconds: " + timeElapsed);
	
	}

	private ArrayList<Point> sortedPoints(Point[] points)
	{
		// convert points to an arraylist that can be sorted
		ArrayList<Point> coordinateList = new ArrayList<Point>();

		for (Point p: points)
		{
			coordinateList.add(p);
		}
		// sort the coordinateList 
		Collections.sort(coordinateList, new PointCompare());

		return coordinateList;
	}

	private Set<Point> getPareto(ArrayList<Point> coordinateList)
	{
		// initialize pareto_results array
		Set<Point> pareto_results = new HashSet<>();

		// converting the input result list array to a point
		Point[] points_array = coordinateList.toArray(new Point[coordinateList.size()]);

		//make the first point of the sorted list the max and add it as a result
		Point points_max = points_array[0];
		//add the top of the sorted list as a result
		for (int i = 0; i < coordinateList.size(); i++)
		{
			pareto_results.add(points_max);
			// iterate through sorted list and find any points that have a higher y value
			if (points_array[i].y > points_max.y)
			{
				// if meets condition, make it new max and add to results
				points_max = points_array[i];
				pareto_results.add(points_max);
			}
		}
		return pareto_results;
	}
}