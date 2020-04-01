package Project_3;

public class LCS{
	// calculates the longest common subsequence
	public int lcsCalculator(String[] sequence){
		// convert ArrayList to s1 and s2
		String s1 = sequence[0];
		String s2 = sequence[1];

		// create a 2d integer array
		int[][] lcs = new int[s1.length() + 1][s2.length() + 1];
		lcs[0][0] = 0;

		for (int i=1; i <= s1.length(); i++){
			for (int j=1; j <= s2.length(); j++){
				
				// if the character does not match, then pick the max of either sequences' previous max lcs
				if ( (s1.charAt(i-1) != s2.charAt(j-1)) ) {
					lcs[i][j] = Math.max(lcs[i-1][j], lcs[i][j-1]);
				}
				// else (they do match). Add one. 
				else {
					lcs[i][j] = lcs[i-1][j-1] + 1;
				}
			}
		}
		// return the lcs answer (bottom right most of the lcs 2d-matrix)
		return lcs[s1.length()][s2.length()];
	}
}