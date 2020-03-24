import java.util.Random;

public class lcs{
	public static void main(String[] args){
		// new object of lcs and calling the lcs calculator with sequence
		lcs lcs_object = new lcs();
		String[] sequences = lcs_object.sequenceGenerator();
		int result = lcs_object.lcsCalculator(sequences);

		System.out.println("LCS: " + result);
		System.out.println("Sequence 1: " + sequences[0]);
		System.out.println("Sequence 2: " + sequences[1]);
	}

	// calculates the longest common subsequence
	public int lcsCalculator(String[] sequence){
		// convert ArrayList to s1 and s2
		String s1 = sequence[0];
		String s2 = sequence[1];

		// create a 2d integer array
		int[][] lcs = new int[s1.length() + 1][s2.length() + 1];



		for (int i=1; i <= s1.length(); i++){
			for (int j=1; j <= s2.length(); j++){
				// initialize LCS for 0
				if ( (i==0) || (j==0) ){
					lcs[i][j] = 0;
					continue;
				}
				else lcs[0][0] = 0;

				if ( (s1.charAt(i-1) != s2.charAt(j-1)) ) {
					lcs[i][j] = Math.max(lcs[i-1][j], lcs[i][j-1]);
				}
				else {
					lcs[i][j] = lcs[i-1][j-1] + 1;
				}
			}
		}
		return lcs[s1.length()][s2.length()];
	}

	// generates the two sequences to be used
	public String[] sequenceGenerator(){
		int n = 100;
		String s1 = "mangoes";
		String s2 = "manloves";

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