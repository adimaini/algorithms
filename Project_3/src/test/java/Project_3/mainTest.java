package Project_3;

import org.junit.Test;
import static org.junit.Assert.*;

public class mainTest {
    @Test public void simpleCase() {
		LCS lcs_object = new LCS();
		String[] sequence = {"mangoes", "mementos"}; //  problem provided case
		int result = lcs_object.lcsCalculator(sequence);
		assertEquals(4, result );
    }
    
   @Test public void edgeCases() {
		LCS lcs_object = new LCS();
		
		String[] s1 = {"om", "on"}; //  few characters
		String[] s2 = {"more", "mae"}; //  different lengths
		String[] s3 = {"n", "m"}; // no matching
		String[] s4 = {"x", "x"}; // one character
		
		int s1Result = lcs_object.lcsCalculator(s1);
		int s2Result = lcs_object.lcsCalculator(s2);
		int s3Result = lcs_object.lcsCalculator(s3);
		int s4Result = lcs_object.lcsCalculator(s4);
		
		assertEquals(1, s1Result );
		assertEquals(2, s2Result );
		assertEquals(0, s3Result );
		assertEquals(1, s4Result );
   }
   
}