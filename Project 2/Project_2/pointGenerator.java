import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.lang.Object;

public class pointGenerator{
	// define the 'n' value or the number of points you want to generate
	static int n = 20000000;

	public static void main(String args[]){

		Point[] pointsArray = new Point[n];

		pointGenerator generatorObject = new pointGenerator();
		pointsArray = generatorObject.generator();

		System.out.println("n is " + n);

	}

	public Point[] generator(){

		// define the boundary conditions of the coordinates
		int max = 10000;
		int min = 0;

		// initialize the array of points
		HashSet<Point> myPoints = new HashSet<Point>();

		for(int i = 0; i < n; i++){
			// nextInt is normally exclusive of the top value,
			// so add 1 to make it inclusive
			int randomNum_x = ThreadLocalRandom.current().nextInt(min, max + 1);
			int randomNum_y = ThreadLocalRandom.current().nextInt(min, max + 1);

			// add point to point array we are generating
			myPoints.add(new Point(randomNum_x, randomNum_y));
		}
		
		Point[] result = myPoints.toArray(new Point[myPoints.size()]);
		return result;
		}
		
}