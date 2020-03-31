package Project_3_demo2;

public class LCS{
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
}