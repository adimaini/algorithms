package Project_3_demo2;

import java.util.Random;

public class main{
	public static void main(String[] args) {
		// keep track of elapsed time of program. initialize startTime. 
		long startTime = System.nanoTime();

		// new object of lcs and calling the lcs calculator with sequence
		LCS lcs_object = new LCS();
		main main_object = new main();
		String[] sequences = main_object.sequenceGenerator();
		int result = lcs_object.lcsCalculator(sequences);

		// calculate elapsed time and print
		long timeElapsed = System.nanoTime() - startTime;
		System.out.println("Execution time in nanoseconds: " + timeElapsed);

		// print LCS number 
		System.out.println("LCS: " + result);
	}

	// generates the two sequences to be used
	public String[] sequenceGenerator(){
		int n = 5000;

		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = n;
	    Random random = new Random();
	 
	    String generatedString1 = random.ints(leftLimit, rightLimit + 1)
	    	.limit(targetStringLength)
	    	.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	    	.toString();

    	String generatedString2 = random.ints(leftLimit, rightLimit + 1)
	    	.limit(targetStringLength)
	    	.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	    	.toString();

		return new String[] {generatedString1, generatedString2};
	}
}