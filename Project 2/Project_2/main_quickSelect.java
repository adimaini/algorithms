import java.util.ArrayList;

public class main_quickSelect{

	public static void main(String args[]){
		//get new points arrays
		pointGenerator points_obj = new pointGenerator();
		Point[] points = points_obj.generator();

		//assign the left and right most indices
		int left = 0;
		int right = points.length;
		//assign the median position to 'k'. Also check if the points arr is odd length
		int k_round = points.length%2;
		int k = points.length / 2;
		//if the array is off length, then just assign the median position as k/2 - 1 index
		if(k_round == 1){
			k = (points.length-1) / 2;
		}

		//make new objects and find the median
		findKthLargest quickSelect_obj = new findKthLargest();
		int answer = quickSelect_obj.findKthElementByRandomizedQuickSelect(points, left, right, k);
		
		System.out.println(answer);
	}
}